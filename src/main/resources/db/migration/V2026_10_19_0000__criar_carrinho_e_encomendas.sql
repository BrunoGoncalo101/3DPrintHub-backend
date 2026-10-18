CREATE TABLE carrinho_itens (
                                id            INT AUTO_INCREMENT PRIMARY KEY,
                                utilizador_id INT NOT NULL,
                                produto_id    INT NOT NULL,
                                material_id   INT NOT NULL,
                                cor_id        INT NOT NULL,
                                quantidade    INT NOT NULL,
                                CONSTRAINT fk_carrinho_produtos  FOREIGN KEY (produto_id)  REFERENCES produtos(id),
                                CONSTRAINT fk_carrinho_materiais FOREIGN KEY (material_id) REFERENCES materiais(id),
                                CONSTRAINT fk_carrinho_cores     FOREIGN KEY (cor_id)      REFERENCES cores(id),
                                CONSTRAINT uq_carrinho_item UNIQUE (utilizador_id, produto_id, material_id, cor_id),
                                CONSTRAINT ck_carrinho_quantidade CHECK (quantidade > 0)
);

CREATE TABLE encomendas (
                            id             INT AUTO_INCREMENT PRIMARY KEY,
                            utilizador_id  INT NOT NULL,
                            estado         VARCHAR(30) NOT NULL,
                            total          DECIMAL(10,2) NOT NULL,
                            morada_entrega VARCHAR(255) NOT NULL,
                            criada_em      DATETIME(6) NOT NULL
);

CREATE TABLE linhas_encomenda (
                                  id             INT AUTO_INCREMENT PRIMARY KEY,
                                  encomenda_id   INT NOT NULL,
                                  produto_id     INT NULL,
                                  pedido_id      INT NULL,
                                  material_id    INT NOT NULL,
                                  cor_id         INT NOT NULL,
                                  quantidade     INT NOT NULL,
                                  preco_unitario DECIMAL(10,2) NOT NULL,
                                  CONSTRAINT fk_linhas_encomendas FOREIGN KEY (encomenda_id) REFERENCES encomendas(id),
                                  CONSTRAINT fk_linhas_produtos   FOREIGN KEY (produto_id)   REFERENCES produtos(id),
                                  CONSTRAINT fk_linhas_materiais  FOREIGN KEY (material_id)  REFERENCES materiais(id),
                                  CONSTRAINT fk_linhas_cores      FOREIGN KEY (cor_id)       REFERENCES cores(id),
                                  CONSTRAINT ck_linhas_quantidade CHECK (quantidade > 0)
);