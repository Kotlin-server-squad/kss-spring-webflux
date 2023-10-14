-- Create sequences for ID columns
CREATE SEQUENCE item_id_seq;
CREATE SEQUENCE item_category_id_seq;
CREATE SEQUENCE k_order_id_seq;
CREATE SEQUENCE order_line_item_id_seq;

-- Create tables for entities
CREATE TABLE "item_category"
(
    id          BIGINT DEFAULT nextval('item_category_id_seq') PRIMARY KEY,
    category    VARCHAR(255),
    description TEXT,
    created_at  TIMESTAMP,
    updated_at  TIMESTAMP
);

CREATE TABLE "item"
(
    id               BIGINT DEFAULT nextval('item_id_seq') PRIMARY KEY,
    name             VARCHAR(255),
    description      TEXT,
    price            NUMERIC,
    count            INTEGER,
    deleted          BOOLEAN,
    item_category_id BIGINT REFERENCES "item_category" (id),
    created_at       TIMESTAMP,
    updated_at       TIMESTAMP
);

CREATE TABLE "k_order"
(
    id         BIGINT DEFAULT nextval('k_order_id_seq') PRIMARY KEY,
    priceSum   NUMERIC,
    status     VARCHAR(255),
    boolean    BOOLEAN,
    customerId BIGINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE "order_line_item"
(
    id           BIGINT DEFAULT nextval('order_line_item_id_seq') PRIMARY KEY,
    count        INTEGER,
    itemPrice    NUMERIC,
    itemPriceSum NUMERIC,
    item_id      BIGINT REFERENCES "item" (id),
    order_id     BIGINT REFERENCES "k_order" (id),
    created_at   TIMESTAMP,
    updated_at   TIMESTAMP
);

-- Create indexes for improved query performance

CREATE INDEX idx_item_item_category_id ON "item" (item_category_id);
CREATE INDEX idx_order_line_item_item_id ON "order_line_item" (item_id);
CREATE INDEX idx_order_line_item_order_id ON "order_line_item" (order_id);
CREATE INDEX idx_order_customer_id ON "k_order" (customerId);