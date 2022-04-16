package com.renil.vertx.starter.eventbus.publishsubscribe;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PublisherVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    var eventBus = vertx.eventBus();
    log.info("message publishing...");
    eventBus.<String>publish(MainVerticle.PUBLISH_CHANNEL, "new message");
    startPromise.complete();
  }
}
