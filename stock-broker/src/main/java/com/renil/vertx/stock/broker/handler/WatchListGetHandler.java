package com.renil.vertx.stock.broker.handler;

import com.renil.vertx.stock.broker.api.WatchListApi;
import com.renil.vertx.stock.broker.model.WatchList;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
public class WatchListGetHandler implements Handler<RoutingContext> {

  private Map<UUID, WatchList> watchlistMap;

  @Override
  public void handle(RoutingContext context) {
    var accountId = WatchListApi.getAccountId(context);
    log.info("Account Id : {}", accountId);
    var watchlist = Optional.ofNullable(watchlistMap.get(UUID.fromString(accountId)));
    if(watchlist.isEmpty()) {
      context.response()
        .setStatusCode(HttpResponseStatus.NOT_FOUND.code())
        .end(new JsonObject()
          .put("message", "Watchlist for account " + accountId + " is not found.")
          .put("path", context.normalizedPath())
          .toBuffer());
      return;
    }
    context.response()
      .setStatusCode(HttpResponseStatus.OK.code())
      .end(watchlist.get().toJsonObject().toBuffer());
  }
}
