package com.renil.vertx.stock.broker.verticle;

import com.renil.vertx.stock.broker.api.AssetsApi;
import com.renil.vertx.stock.broker.api.QuotesApi;
import com.renil.vertx.stock.broker.api.WatchListApi;
import com.renil.vertx.stock.broker.config.ConfigLoader;
import com.renil.vertx.stock.broker.handler.FailureHandler;
import com.renil.vertx.stock.broker.config.BrokerConfig;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    ConfigLoader.load(vertx)
      .onFailure(startPromise::fail)
      .onSuccess(config -> {
        log.info("Configuration fetched : {}", config);
        final Router router = initRouter();
        initServer(router, config, startPromise);
      });
  }

  private void initServer(Router router, BrokerConfig config, Promise<Void> startPromise) {
    vertx.createHttpServer()
      .requestHandler(router)
      .exceptionHandler(err -> log.error("HTTP server error : ", err))
      .listen(config.getServerPort())
      .onFailure(startPromise::fail)
      .onSuccess(server -> {
        startPromise.complete();
        log.info("HTTP server started on port {}", server.actualPort());
      });
  }

  private Router initRouter() {
    final var router = Router.router(vertx);
    router.route()
      .handler(BodyHandler.create())
      .failureHandler(new FailureHandler());

    AssetsApi.attach(router);
    QuotesApi.attach(router);
    WatchListApi.attach(router);
    return router;
  }
}
