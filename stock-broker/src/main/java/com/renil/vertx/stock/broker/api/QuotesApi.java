package com.renil.vertx.stock.broker.api;

import com.renil.vertx.stock.broker.handler.QuotesGetHandler;
import com.renil.vertx.stock.broker.model.Asset;
import com.renil.vertx.stock.broker.model.Quote;
import com.renil.vertx.stock.broker.util.Constants;
import io.vertx.ext.web.Router;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class QuotesApi {

  public static void attach(Router router) {
    final Map<String, Quote> cachedQuotes = new HashMap<>();
    Constants.ASSETS.forEach(asset -> cachedQuotes.put(asset, initRandomQuote(asset)));

    router.get("/quotes/:asset").handler(new QuotesGetHandler(cachedQuotes));
  }

  private static Quote initRandomQuote(String asset) {
    return Quote.builder()
      .asset(new Asset(asset))
      .bid(randomDecimalValue())
      .volume(randomDecimalValue())
      .lastPrice(randomDecimalValue())
      .ask(randomDecimalValue())
      .build();
  }

  private static BigDecimal randomDecimalValue() {
    return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1, 100));
  }
}
