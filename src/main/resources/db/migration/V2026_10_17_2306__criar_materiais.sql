CREATE TABLE materiais (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           nome VARCHAR(100) NOT NULL,
                           preco_por_grama DECIMAL(10,4) NOT NULL,
                           densidade DECIMAL(10,3) NOT NULL,
                           ativo BOOLEAN NOT NULL DEFAULT TRUE
);