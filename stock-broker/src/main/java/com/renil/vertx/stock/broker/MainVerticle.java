package com.renil.vertx.stock.broker;

import com.renil.vertx.stock.broker.config.ConfigLoader;
import com.renil.vertx.stock.broker.db.migration.FlywayMigration;
import com.renil.vertx.stock.broker.verticle.ServerVerticle;
import com.renil.vertx.stock.broker.verticle.VersionVerticle;
import io.vertx.core.*;
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
      .onSuccess(versionId -> log.info("Deployed {} with id {}", VersionVerticle.class.getSimpleName(), versionId))
      .compose(next -> migrateDatabase())
      .onFailure(startPromise::fail)
      .onSuccess(id -> log.info("Migrated DB Schema to latest version..."))
      .compose(next -> deployServerVerticle(startPromise));
  }

  private Future<Void> migrateDatabase() {
    return ConfigLoader.load(vertx)
      .compose(config -> FlywayMigration.migrate(vertx, config.getDbConfig()));
  }

  private Future<String> deployServerVerticle(Promise<Void> startPromise) {
    return vertx.deployVerticle(ServerVerticle.class.getName(),
        new DeploymentOptions().setInstances(getAvailableProcessors()))
      .onFailure(startPromise::fail)
      .onSuccess(serverId -> {
        log.info("Deployed {} verticle with id {}", ServerVerticle.class.getSimpleName(), serverId);
        startPromise.complete();
      });
  }

  private int getAvailableProcessors() {
    return Math.max(1, Runtime.getRuntime().availableProcessors() / 2);
  }

}
