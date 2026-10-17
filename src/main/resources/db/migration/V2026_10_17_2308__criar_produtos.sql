CREATE TABLE produtos (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(150) NOT NULL,
                          descricao VARCHAR(500),
                          preco_base DECIMAL(10,2) NOT NULL,
                          imagem_url VARCHAR(500),
                          dimensoes VARCHAR(100),
                          ativo BOOLEAN NOT NULL DEFAULT TRUE,
                          categoria_id INT NOT NULL,

                          CONSTRAINT fk_produtos_categorias
                              FOREIGN KEY (categoria_id)
                                  REFERENCES categorias(id)
);