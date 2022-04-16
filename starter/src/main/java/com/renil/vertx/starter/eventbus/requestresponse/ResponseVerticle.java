package com.renil.vertx.starter.eventbus.requestresponse;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import static com.renil.vertx.starter.eventbus.requestresponse.MainVerticle.REQUEST_RESPONSE;

@Slf4j
public class ResponseVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    log.info("deployed response");
    var eventBus = vertx.eventBus();
    eventBus.<String>consumer(REQUEST_RESPONSE, message -> {
      log.info("Request received : {}", message.body());
      message.reply("Nice to meet you!");
    });
    startPromise.complete();
  }
}
