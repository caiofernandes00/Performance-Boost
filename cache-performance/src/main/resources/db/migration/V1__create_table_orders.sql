CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE orders (
    uuid UUID NOT NULL DEFAULT uuid_generate_v4(),
    name VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NULL,
    PRIMARY KEY (uuid)
);

CREATE INDEX idx_orders_name ON orders (name);

CREATE OR REPLACE FUNCTION update_orders_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
