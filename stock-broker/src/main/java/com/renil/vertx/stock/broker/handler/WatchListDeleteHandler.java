package com.renil.vertx.stock.broker.handler;

import com.renil.vertx.stock.broker.api.WatchListApi;
import com.renil.vertx.stock.broker.model.WatchList;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
public class WatchListDeleteHandler implements Handler<RoutingContext> {

  private Map<UUID, WatchList> watchlistMap;

  @Override
  public void handle(RoutingContext context) {
    var accountId = WatchListApi.getAccountId(context);
    log.info("Account Id : {}", accountId);
    var watchList = watchlistMap.remove(UUID.fromString(accountId));
    log.info("deleted : {}, remaining : {}", watchList, watchlistMap.values());
    context.response().end(watchList.toJsonObject().toBuffer());
  }
}
