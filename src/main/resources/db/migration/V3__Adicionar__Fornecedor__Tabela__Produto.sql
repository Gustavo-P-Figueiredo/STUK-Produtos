ALTER TABLE produtos
    ADD COLUMN fornecedor_id BIGINT;

ALTER TABLE produtos
    ADD CONSTRAINT fornecedor_fk
        FOREIGN KEY (fornecedor_id)
            REFERENCES fornecedor(id);