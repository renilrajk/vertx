package com.renil.vertx.starter.custommessage;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

public class PingMessageCodec implements MessageCodec<Ping, Ping> {
  @Override
  public void encodeToWire(Buffer buffer, Ping ping) {
    throw new UnsupportedOperationException("Only local encode is supported");
  }

  @Override
  public Ping decodeFromWire(int i, Buffer buffer) {
    throw new UnsupportedOperationException("Only local decode is supported");
  }

  @Override
  public Ping transform(Ping ping) {
    return ping;
  }

  @Override
  public String name() {
    return "PingMessageCodec";
  }

  @Override
  public byte systemCodecID() {
    return -1;
  }
}
