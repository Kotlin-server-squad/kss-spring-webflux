-- Create sequences for ID columns
CREATE SEQUENCE customer_id_seq;
CREATE SEQUENCE address_id_seq;
CREATE SEQUENCE additional_information_id_seq;

-- Create tables for entities


CREATE TABLE "address"
(
    id         BIGINT      DEFAULT nextval('address_id_seq') PRIMARY KEY,
    zip_code   INTEGER,
    street     VARCHAR(255),
    town       VARCHAR(255),
    country    VARCHAR(255),
    region     VARCHAR(255),
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE TABLE "additional_information"
(
    id          BIGINT      DEFAULT nextval('additional_information_id_seq') PRIMARY KEY,
    birth_day   INTEGER,
    birth_month INTEGER,
    birth_year  INTEGER,
    gender      VARCHAR(255),
    created_at  TIMESTAMPTZ DEFAULT NOW(),
    updated_at  TIMESTAMPTZ DEFAULT NOW()
);

CREATE TABLE "customer"
(
    id                        BIGINT      DEFAULT nextval('customer_id_seq') PRIMARY KEY,
    user_name                 VARCHAR(255) NOT NULL,
    first_name                VARCHAR(255),
    last_name                 VARCHAR(255),
    address_id                BIGINT REFERENCES "address" (id),
    email                     VARCHAR(255) UNIQUE,
    additional_information_id BIGINT REFERENCES "additional_information" (id),
    created_at                TIMESTAMPTZ DEFAULT NOW(),
    updated_at                TIMESTAMPTZ DEFAULT NOW()
);


-- Create indexes for improved query performance

CREATE INDEX idx_customer_address_id ON "customer" (address_id);
CREATE INDEX idx_customer_email ON "customer" (email);
