package com.renil.vertx.stock.broker.handler;

import com.renil.vertx.stock.broker.api.AssetsApi;
import com.renil.vertx.stock.broker.model.Asset;
import com.renil.vertx.stock.broker.util.Constants;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class AssetsGetHandler implements Handler<RoutingContext> {
  @Override
  public void handle(RoutingContext context) {
    final var assets = new JsonArray();
    Constants.ASSETS.stream().map(Asset::new).forEach(assets::add);
    log.info("Path {} responds with {}", context.normalizedPath(), assets.encode());
    // only for testing purpose
//    inducedDelay(context);
    context.response()
      .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
      .putHeader("my-header", "my-value")
      .end(assets.toBuffer());
  }

  private void inducedDelay(RoutingContext context) {
    try {
      int millis = ThreadLocalRandom.current().nextInt(100, 300);
      if(millis % 2 == 0) {
        Thread.sleep(millis);
        context.response().setStatusCode(500).end("Sleeping...");
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
