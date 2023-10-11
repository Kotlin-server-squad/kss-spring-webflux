-- Create tables for entities


CREATE TABLE "item_category"
(
    id          SERIAL PRIMARY KEY,
    category    VARCHAR(255),
    description TEXT,
    created_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ
);

CREATE TABLE "item"
(
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(255),
    description     TEXT,
    price           NUMERIC,
    count           INTEGER,
    deleted         BOOLEAN,
    item_category_id BIGINT,
    created_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ,
    FOREIGN KEY (item_category_id) REFERENCES "item_category" (id)
);

CREATE TABLE "k-order"
(
    id         SERIAL PRIMARY KEY,
    price_sum   NUMERIC,
    status     VARCHAR(255),
    boolean    BOOLEAN,
    customer_id BIGINT,
    created_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ

);

CREATE TABLE "order_line_item"
(
    id           SERIAL PRIMARY KEY,
    count        INTEGER,
    item_price    NUMERIC,
    item_price_sum NUMERIC,
    item_id      BIGINT,
    order_id     BIGINT,
    FOREIGN KEY (item_id) REFERENCES "item" (id),
    FOREIGN KEY (order_id) REFERENCES "k-order" (id)
);

CREATE TABLE "order-status"
(
    id     SERIAL PRIMARY KEY,
    status VARCHAR(255)
);



-- Create indexes for improved query performance

CREATE INDEX idx_order_customer_id ON "k-order" (customer_id);
CREATE INDEX idx_order_line_item_item_id ON "order_line_item" (item_id);
CREATE INDEX idx_order_line_item_order_id ON "order_line_item" (order_id);
CREATE INDEX idx_item_item_category_id ON "item" (item_category_id);