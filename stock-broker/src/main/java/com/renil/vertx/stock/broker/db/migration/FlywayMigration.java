package com.renil.vertx.stock.broker.db.migration;

import com.renil.vertx.stock.broker.config.DbConfig;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class FlywayMigration {

  public static Future<Void> migrate(Vertx vertx, DbConfig dbConfig) {
    return vertx.<Void>executeBlocking(promise -> {
      executeMigrations(dbConfig);
      promise.complete();
    })
    .onFailure(err -> log.error("Failed to migrate database schema with error :", err));
  }

  private static void executeMigrations(DbConfig dbConfig) {
    var jdbcUrl = String.format("jdbc:postgresql://%s:%d/%s", dbConfig.getHost(), dbConfig.getPort(), dbConfig.getDatabase());
    log.debug("Migrating db schema using jdbc url : {}", jdbcUrl);
    log.debug("DbConfig {}", dbConfig);

    var flyway = Flyway.configure()
      .dataSource(jdbcUrl, dbConfig.getUser(), dbConfig.getPassword())
      .schemas("broker")
      .defaultSchema("broker")
      .load();

    var current = Optional.ofNullable(flyway.info().current());
    current.ifPresent(info -> log.debug("Current migration version is {}", info.getVersion()));

    var pendingMigrations  = Optional.ofNullable(flyway.info().pending());
    log.debug("Pending migrations are : {}", printPendingMigrations(pendingMigrations));

    flyway.migrate();
  }

  private static String printPendingMigrations(Optional<MigrationInfo[]> pendingMigrations) {
    if(pendingMigrations.isPresent()) {
      return Arrays.stream(pendingMigrations.get())
        .map(migrationInfo -> migrationInfo.getVersion() + " - " + migrationInfo.getDescription())
        .collect(Collectors.joining(",", "[", "]"));
    } else {
      return "[]";
    }
  }


}
