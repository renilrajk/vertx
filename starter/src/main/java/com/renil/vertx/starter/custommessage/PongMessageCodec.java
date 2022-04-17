package com.renil.vertx.starter.custommessage;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

public class PongMessageCodec implements MessageCodec<Pong, Pong> {
  @Override
  public void encodeToWire(Buffer buffer, Pong pong) {
    throw new UnsupportedOperationException("Only local encode is supported");
  }

  @Override
  public Pong decodeFromWire(int i, Buffer buffer) {
    throw new UnsupportedOperationException("Only local decode is supported");
  }

  @Override
  public Pong transform(Pong pong) {
    return pong;
  }

  @Override
  public String name() {
    return "PongMessageCodec";
  }

  @Override
  public byte systemCodecID() {
    return -1;
  }
}
