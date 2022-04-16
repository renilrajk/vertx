package com.renil.vertx.starter.eventbus.requestresponse;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainVerticle extends AbstractVerticle {

  public static final String REQUEST_RESPONSE = "request-response";

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new MainVerticle());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.deployVerticle(ResponseVerticle.class.getName(), new DeploymentOptions().setInstances(2));
    vertx.deployVerticle(new RequestVerticle());
    startPromise.complete();
  }
}
