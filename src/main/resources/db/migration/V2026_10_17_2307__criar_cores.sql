CREATE TABLE cores (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       nome VARCHAR(100) NOT NULL,
                       codigo_hex VARCHAR(7) NOT NULL,
                       disponivel BOOLEAN NOT NULL DEFAULT TRUE,
                       material_id INT NOT NULL,

                       CONSTRAINT fk_cores_materiais
                           FOREIGN KEY (material_id)
                               REFERENCES materiais(id)
);