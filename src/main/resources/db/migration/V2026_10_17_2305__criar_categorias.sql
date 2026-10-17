CREATE TABLE categorias (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            nome VARCHAR(100) NOT NULL,
                            ativa BOOLEAN NOT NULL DEFAULT TRUE
);