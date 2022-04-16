package com.renil.vertx.starter.worker;

import io.vertx.core.*;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class MainVerticle extends AbstractVerticle {

  public static void main(String[] args) {
    final Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new MainVerticle());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    log.info("Started : {}", this.getClass().getSimpleName());
    startPromise.complete();
    vertx.deployVerticle(new WorkerVerticle(), new DeploymentOptions()
      .setWorker(true)
      .setWorkerPoolName("my-worker-thread")
      .setWorkerPoolSize(1)
    );
    executeBlocking();
  }

  private void executeBlocking() {
    vertx.executeBlocking(promise -> {
      log.info("Executing blocking code in worker thread");
      try {
        Thread.sleep(5000);
        promise.complete();
      } catch (InterruptedException e) {
        log.error("Failed in blocking code : {}", e);
        promise.fail(e);
      }
    }, asyncResult -> {
      if(asyncResult.succeeded()) {
        log.info("Successfully returned to event loop from worker thread");
      } else {
        log.error("Returned to event loop. Blocking call failed in worker thread : {}", asyncResult.cause());
      }
    });
  }
}
