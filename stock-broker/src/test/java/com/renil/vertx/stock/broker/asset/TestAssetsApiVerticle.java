package com.renil.vertx.stock.broker.asset;

import com.renil.vertx.stock.broker.AbstractTestVerticle;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
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
public class TestAssetsApiVerticle extends AbstractTestVerticle {

  @Test
  void get_all_assets(Vertx vertx, VertxTestContext testContext) throws Throwable {
    var client = WebClient.create(vertx, new WebClientOptions().setDefaultPort(SERVER_PORT));
    client.get("/assets")
      .send()
      .onComplete(testContext.succeeding(resp -> {
        var json = resp.bodyAsJsonArray();
        log.info("Response : {}", json);
        assertEquals("[{\"name\":\"AAPL\"},{\"name\":\"AMZN\"},{\"name\":\"FB\"},{\"name\":\"GOOG\"},{\"name\":\"MSFT\"},{\"name\":\"NFLX\"},{\"name\":\"TSLA\"}]", json.encode());
        assertEquals(200, resp.statusCode());
        assertEquals(HttpHeaderValues.APPLICATION_JSON.toString(), resp.getHeader(HttpHeaders.CONTENT_TYPE.toString()));
        assertEquals("my-value", resp.getHeader("my-header"));
        testContext.completeNow();
      }));
  }

}
