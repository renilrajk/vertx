package com.renil.vertx.stock.broker.api;

import com.renil.vertx.stock.broker.handler.AssetsGetHandler;
import io.vertx.ext.web.Router;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AssetsApi {

  public static void attach(Router router) {
    router.get("/assets").handler(new AssetsGetHandler()) ;
  }

}
