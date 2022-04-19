package com.renil.vertx.stock.broker.watchlist;

import com.renil.vertx.stock.broker.AbstractTestVerticle;
import com.renil.vertx.stock.broker.model.Asset;
import com.renil.vertx.stock.broker.model.WatchList;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(VertxExtension.class)
public class TestWatchListApiVerticle extends AbstractTestVerticle {

  @Test
  void put_and_get_watchlist(Vertx vertx, VertxTestContext testContext) throws Throwable {
    var client = WebClient.create(vertx, new WebClientOptions().setDefaultPort(SERVER_PORT));
    var accountId = UUID.randomUUID();
    var assets = List.of(new Asset("AMZN"), new Asset("TSLA"));
    var body = new WatchList(assets);
    client.put("/accounts/" + accountId + "/watchlist")
      .sendJsonObject(body.toJsonObject())
      .onComplete(testContext.succeeding(resp -> {
        var json = resp.bodyAsJsonObject();
        log.info("Response Put : {}", json);
        assertEquals(200, resp.statusCode());
        assertEquals("{\"watchlist\":[{\"name\":\"AMZN\"},{\"name\":\"TSLA\"}]}", json.encode());
      }))
      .compose(next -> {
        client.get("/accounts/" + accountId + "/watchlist")
          .send()
          .onComplete(testContext.succeeding(resp -> {
            var json = resp.bodyAsJsonObject();
            log.info("Response Get : {}", json);
            assertEquals(200, resp.statusCode());
            assertEquals("{\"watchlist\":[{\"name\":\"AMZN\"},{\"name\":\"TSLA\"}]}", json.encode());
            testContext.completeNow();
          }));
        return Future.succeededFuture();
      });
  }

  @Test
  void put_delete_and_get_watchlist(Vertx vertx, VertxTestContext testContext) throws Throwable {
    var client = WebClient.create(vertx, new WebClientOptions().setDefaultPort(SERVER_PORT));
    var accountId = UUID.randomUUID();
    var assets = List.of(new Asset("AMZN"), new Asset("TSLA"));
    var body = new WatchList(assets);
    client.put("/accounts/" + accountId + "/watchlist")
      .sendJsonObject(body.toJsonObject())
      .onComplete(testContext.succeeding(resp -> {
        var json = resp.bodyAsJsonObject();
        log.info("Response Put : {}", json);
        assertEquals(200, resp.statusCode());
        assertEquals("{\"watchlist\":[{\"name\":\"AMZN\"},{\"name\":\"TSLA\"}]}", json.encode());
      }))
      .compose(next -> {
        client.delete("/accounts/" + accountId + "/watchlist")
          .send()
          .onComplete(testContext.succeeding(resp -> {
            var json = resp.bodyAsJsonObject();
            log.info("Response Delete : {}", json);
            assertEquals(200, resp.statusCode());
            assertEquals("{\"watchlist\":[{\"name\":\"AMZN\"},{\"name\":\"TSLA\"}]}", json.encode());
          }));
        return Future.succeededFuture();
      })
      .compose(next -> {
        client.get("/accounts/" + accountId + "/watchlist")
          .send()
          .onComplete(testContext.succeeding(resp -> {
            var json = resp.bodyAsJsonObject();
            log.info("Response Get : {}", json);
            assertEquals(404, resp.statusCode());
            assertEquals("{\"message\":\"Watchlist for account " + accountId + " is not found.\",\"path\":\"/accounts/" + accountId + "/watchlist\"}", json.encode());
            testContext.completeNow();
          }));
        return Future.succeededFuture();
      });
  }
}
