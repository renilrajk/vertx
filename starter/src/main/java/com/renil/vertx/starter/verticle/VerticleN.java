package com.renil.vertx.starter.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VerticleN extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    log.info("Started : {}", this.getClass().getSimpleName());
    log.info("Configs : {}", config().toString());
    startPromise.complete();
  }
}
