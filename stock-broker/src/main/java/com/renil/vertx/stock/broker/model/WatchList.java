package com.renil.vertx.stock.broker.model;

import io.vertx.core.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WatchList {
  List<Asset> watchlist;

  public JsonObject toJsonObject() {
    return JsonObject.mapFrom(this);
  }
}
