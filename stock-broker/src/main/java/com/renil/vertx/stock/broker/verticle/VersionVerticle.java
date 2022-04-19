package com.renil.vertx.stock.broker.verticle;

import com.renil.vertx.stock.broker.config.ConfigLoader;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VersionVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    ConfigLoader.load(vertx)
      .onFailure(startPromise::fail)
      .onSuccess(config -> {
        log.info("Current application version is {}", config.getVersion());
        startPromise.complete();
      });
  }
}
