ALTER TABLE invoice ALTER COLUMN invoice_number DROP NOT null;
ALTER TABLE invoice ADD COLUMN client_order_id VARCHAR(200) NOT null;
ALTER TABLE invoice ADD CONSTRAINT uq_client_order_id UNIQUE (client_order_id);
ALTER TABLE invoice ALTER COLUMN date TYPE TIMESTAMP;
ALTER TABLE invoice RENAME COLUMN date TO order_date;
ALTER TABLE invoice ADD COLUMN status VARCHAR(20) NOT null;
ALTER TABLE invoice ADD COLUMN error VARCHAR(200);
ALTER TABLE invoice ADD COLUMN issue_date TIMESTAMP;
