package com.renil.vertx.starter.eventbus.pointtopoint;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainVerticle extends AbstractVerticle {

  public static final String MESSAGE_CHANNEL = "message.channel";

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new MainVerticle());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.deployVerticle(new SenderVerticle());
    vertx.deployVerticle(ReceiverVerticle.class.getName(), new DeploymentOptions().setInstances(3));
    startPromise.complete();
  }
}
