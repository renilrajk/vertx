package com.renil.vertx.stock.broker;

import com.renil.vertx.stock.broker.util.Constants;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;

public class AbstractTestVerticle {
  protected final int SERVER_PORT = 9000;

  @BeforeEach
  void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
    System.setProperty(Constants.SERVER_PORT, String.valueOf(SERVER_PORT));
    vertx.deployVerticle(new MainVerticle(), testContext.succeeding(id -> testContext.completeNow()));
  }
}
