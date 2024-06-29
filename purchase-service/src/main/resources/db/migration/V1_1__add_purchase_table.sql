CREATE TABLE purchase (
    id                  BIGSERIAL PRIMARY KEY NOT NULL,
    book_isbn           varchar(255) UNIQUE NOT NULL,
    book_price          float8 NOT NULL,
    quantity            int NOT NULL,
    status              VARCHAR(100) NOT NULL,
    version             integer NOT NULL
);