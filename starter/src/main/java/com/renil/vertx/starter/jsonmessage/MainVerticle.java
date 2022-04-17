package com.renil.vertx.starter.jsonmessage;

import io.vertx.core.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainVerticle extends AbstractVerticle {

  public static final String MESSAGE_CHANNEL = "json.message.channel";

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new MainVerticle());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.deployVerticle(new RequestVerticle(), onError());
    vertx.deployVerticle(new ResponseVerticle(), onError());
    startPromise.complete();
  }

  private Handler<AsyncResult<String>> onError() {
    return response -> {
      if(response.failed()) {
        log.error("Deployment failed...", response.cause());
      }
    };
  }
}
