package com.renil.vertx.stock.broker.api;

import com.renil.vertx.stock.broker.handler.WatchListDeleteHandler;
import com.renil.vertx.stock.broker.handler.WatchListGetHandler;
import com.renil.vertx.stock.broker.handler.WatchListPutHandler;
import com.renil.vertx.stock.broker.model.WatchList;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class WatchListApi {

  public static void attach(Router router) {
    final Map<UUID, WatchList> watchlistMap = new HashMap<>();
    String path = "/accounts/:accountId/watchlist";

    router.get(path).handler(new WatchListGetHandler(watchlistMap));
    router.put(path).handler(new WatchListPutHandler(watchlistMap));
    router.delete(path).handler(new WatchListDeleteHandler(watchlistMap));
  }

  public static String getAccountId(RoutingContext context) {
    return context.pathParam("accountId");
  }
}
