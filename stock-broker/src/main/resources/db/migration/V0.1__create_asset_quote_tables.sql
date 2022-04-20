CREATE TABLE asset
(
  value VARCHAR PRIMARY KEY
);

CREATE TABLE quote
(
  id          SERIAL PRIMARY KEY,
  bid         NUMERIC,
  ask         NUMERIC,
  last_price  NUMERIC,
  volume      NUMERIC,
  asset       VARCHAR,
  FOREIGN KEY (asset) REFERENCES asset(value),
  CONSTRAINT last_price_is_positive CHECK (last_price > 0),
  CONSTRAINT ask_is_positive CHECK (ask > 0),
  CONSTRAINT bid_is_positive CHECK (bid > 0),
  CONSTRAINT volume_is_positive_or_zero CHECK (volume >= 0)
);
