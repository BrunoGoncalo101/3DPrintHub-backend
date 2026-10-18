
CREATE TABLE ficheiros_modelo (
                                  id INT AUTO_INCREMENT PRIMARY KEY,
                                  nome_original VARCHAR(255) NOT NULL,
                                  caminho VARCHAR(500) NOT NULL,
                                  tamanho_bytes INT NOT NULL,
                                  formato VARCHAR(45) NOT NULL,
                                  volume_cm3 DECIMAL(10,2),
                                  utilizador_id INT NOT NULL,
                                  criado_em DATETIME(6) NOT NULL
);
