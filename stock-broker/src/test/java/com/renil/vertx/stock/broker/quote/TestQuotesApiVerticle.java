package com.renil.vertx.stock.broker.quote;

import com.renil.vertx.stock.broker.AbstractTestVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(VertxExtension.class)
public class TestQuotesApiVerticle extends AbstractTestVerticle {

  @Test
  void get_quote_for_asset(Vertx vertx, VertxTestContext testContext) throws Throwable {
    var client = WebClient.create(vertx, new WebClientOptions().setDefaultPort(SERVER_PORT));
    client.get("/quotes/AMZN")
      .send()
      .onComplete(testContext.succeeding(resp -> {
        var json = resp.bodyAsJsonObject();
        log.info("Response : {}", json);
        assertEquals("{\"name\":\"AMZN\"}", json.getJsonObject("asset").encode());
        assertEquals(200, resp.statusCode());
        testContext.completeNow();
      }));
  }

  @Test
  void get_quote_for_asset_unknown(Vertx vertx, VertxTestContext testContext) throws Throwable {
    var client = WebClient.create(vertx, new WebClientOptions().setDefaultPort(SERVER_PORT));
    client.get("/quotes/UNKNOWN")
      .send()
      .onComplete(testContext.succeeding(resp -> {
        var json = resp.bodyAsJsonObject();
        log.info("Response : {}", json);
        assertEquals(404, resp.statusCode());
        assertEquals("{\"message\":\"Asset UNKNOWN is not found.\",\"path\":\"/quotes/UNKNOWN\"}", json.encode());
        testContext.completeNow();
      }));
  }

}
