package com.renil.vertx.starter.eventbus.requestresponse;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import static com.renil.vertx.starter.eventbus.requestresponse.MainVerticle.REQUEST_RESPONSE;

@Slf4j
public class RequestVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    var eventBus = vertx.eventBus();
    vertx.setPeriodic(5000, id -> {
    eventBus.<String>request(REQUEST_RESPONSE, "Hello", response -> {
      log.info("Response received : {}", Objects.nonNull(response.result()) ? response.result().body() : "No response");
    });
    });
    startPromise.complete();
  }
}
