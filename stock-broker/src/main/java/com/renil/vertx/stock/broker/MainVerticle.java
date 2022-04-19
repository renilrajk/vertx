package com.renil.vertx.stock.broker;

import com.renil.vertx.stock.broker.verticle.ServerVerticle;
import com.renil.vertx.stock.broker.verticle.VersionVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainVerticle extends AbstractVerticle {

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.exceptionHandler(err -> log.error("Unhandled error...", err));
    vertx.deployVerticle(new MainVerticle())
      .onFailure(err -> log.error("Failed to deploy...", err))
      .onSuccess(id -> log.info("Deployed {} with id {}", MainVerticle.class.getSimpleName(), id));
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.deployVerticle(VersionVerticle.class.getName())
      .onFailure(startPromise::fail)
        .onSuccess(versionId -> {
          vertx.deployVerticle(ServerVerticle.class.getName(),
              new DeploymentOptions().setInstances(getAvailableProcessors()))
            .onFailure(startPromise::fail)
            .onSuccess(serverId -> {
              log.info("Deployed {} verticle with id {}", ServerVerticle.class.getSimpleName(), serverId);
              startPromise.complete();
            });
        });
  }

  private int getAvailableProcessors() {
    return Math.max(1, Runtime.getRuntime().availableProcessors() / 2);
  }

}
