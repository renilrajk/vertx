package com.renil.vertx.starter.custommessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Ping {
 private String message;
 private boolean isEnabled;
}
