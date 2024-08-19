CREATE TABLE transaction (
    id NUMERIC,
    account_uid UUID,
    transaction_uid UUID,
    transaction_type varchar(255),
    amount NUMERIC,
    transaction_time TIMESTAMP WITHOUT TIME ZONE
)