CREATE TABLE accounts (
	account_id			UUID PRIMARY KEY
	, client_name 		VARCHAR(255) NOT NULL
	, document			VARCHAR(255) NOT NULL
	, balance			NUMERIC(19, 2) NOT NULL
	, account_type		VARCHAR(50) NOT NULL
	, account_status	VARCHAR(50) NOT NULL
	, version			BIGINT
	, CONSTRAINT uq_document_account_type UNIQUE (document, account_type)
);