package com.renil.vertx.starter.custommessage;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    var eventBus = vertx.eventBus();
    Ping ping = new Ping("test", true);
    eventBus.registerDefaultCodec(Ping.class, new PingMessageCodec());
    eventBus.<Pong>request(MainVerticle.MESSAGE_CHANNEL, ping, resp -> {
      if(resp.failed()) {
        log.error("failed...", resp.cause());
        return;
      }
      log.info("response received : {}", resp.result().body());
    });
    startPromise.complete();
  }
}
