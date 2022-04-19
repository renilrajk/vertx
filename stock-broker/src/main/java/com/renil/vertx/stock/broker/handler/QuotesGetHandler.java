package com.renil.vertx.stock.broker.handler;

import com.renil.vertx.stock.broker.model.Quote;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class QuotesGetHandler implements Handler<RoutingContext> {

  private Map<String, Quote> cachedQuotes;

  @Override
  public void handle(RoutingContext context) {
    var asset = context.pathParam("asset");
    log.info("asset param : {}", asset);
    var quote = Optional.ofNullable(cachedQuotes.get(asset));
    if(quote.isEmpty()) {
      context.response()
        .setStatusCode(HttpResponseStatus.NOT_FOUND.code())
        .end(new JsonObject()
          .put("message", "Asset " + asset + " is not found.")
          .put("path", context.normalizedPath())
          .toBuffer());
      return;
    }
    var json = quote.get().toJsonObject();
    log.info("Path {} responds with {}", context.normalizedPath(), json.encode());
    context.response()
      .setStatusCode(200)
      .end(json.toBuffer());
  }
}
