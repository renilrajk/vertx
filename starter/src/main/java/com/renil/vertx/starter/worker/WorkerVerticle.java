package com.renil.vertx.starter.worker;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WorkerVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    log.info("Blocking code in worker verticle started");
    startPromise.complete();
    Thread.sleep(5000);
    log.info("Blocking code in worker verticle completed");
  }
}
