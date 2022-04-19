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
public class WatchListPutHandler implements Handler<RoutingContext> {

  private Map<UUID, WatchList> watchlistMap;

  @Override
  public void handle(RoutingContext context) {
    var accountId = WatchListApi.getAccountId(context);
    log.info("Account Id : {}", accountId);
    var body = context.getBodyAsJson();
    var watchList = body.mapTo(WatchList.class);
    watchlistMap.put(UUID.fromString(accountId), watchList);
    context.response().end(body.toBuffer());
  }
}
