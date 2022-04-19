package com.renil.vertx.stock.broker.handler;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FailureHandler implements Handler<RoutingContext> {
  @Override
  public void handle(RoutingContext context) {
    if (context.response().ended()) {
      return;
    }
    log.error("Routing error : ", context.failure());
    context.response()
      .setStatusCode(500)
      .end(new JsonObject().put("message", "Something went wrong...").toBuffer());
  }
}
