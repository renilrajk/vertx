package com.renil.vertx.starter.eventbus.publishsubscribe;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainVerticle extends AbstractVerticle {

  public static final String PUBLISH_CHANNEL = "publish.channel";

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new MainVerticle());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.deployVerticle(ConsumerVerticle.class.getName(), new DeploymentOptions().setInstances(3));
    vertx.deployVerticle(new Consumer2Verticle());
    vertx.deployVerticle(new PublisherVerticle());
    startPromise.complete();
  }
}
