package com.renil.vertx.starter.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VerticleA extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    log.info("Started : {}", this.getClass().getSimpleName());
    vertx.deployVerticle(new VerticleAA(), stringAsyncResult -> {
      log.info("Deployed : {}", VerticleAA.class.getSimpleName());
      vertx.undeploy(stringAsyncResult.result());
    });
    vertx.deployVerticle(new VerticleAB(), stringAsyncResult -> {
      log.info("Deployed : {}", VerticleAB.class.getSimpleName());
    });
    startPromise.complete();
  }
}
