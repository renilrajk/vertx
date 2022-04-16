package com.renil.vertx.starter.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VerticleAB extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    log.info("Started : {}", this.getClass().getSimpleName());
    startPromise.complete();
  }

  @Override
  public void stop(Promise<Void> stopPromise) throws Exception {
    log.info("Stopped : {}", this.getClass().getSimpleName());
    stopPromise.complete();
  }
}
