ALTER TABLE carrinho_itens
    ADD CONSTRAINT fk_carrinho_utilizadores
        FOREIGN KEY (utilizador_id) REFERENCES utilizadores(id);

ALTER TABLE encomendas
    ADD CONSTRAINT fk_encomendas_utilizadores
        FOREIGN KEY (utilizador_id) REFERENCES utilizadores(id);