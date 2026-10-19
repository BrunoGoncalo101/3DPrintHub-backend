CREATE TABLE utilizadores (
                              id                INT AUTO_INCREMENT PRIMARY KEY,
                              nome              VARCHAR(100) NOT NULL,
                              email             VARCHAR(150) NOT NULL,
                              password_hash     VARCHAR(255) NOT NULL,
                              perfil            VARCHAR(20)  NOT NULL,
                              telefone          VARCHAR(20),
                              morada            VARCHAR(255),
                              ativo             BOOLEAN      NOT NULL DEFAULT TRUE,
                              termos_aceites_em DATETIME(6)  NOT NULL,
                              criado_em         DATETIME(6)  NOT NULL,
                              CONSTRAINT uq_utilizadores_email UNIQUE (email)
);