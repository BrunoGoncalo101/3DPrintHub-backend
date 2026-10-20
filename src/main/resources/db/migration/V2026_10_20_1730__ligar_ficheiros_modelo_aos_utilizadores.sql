ALTER TABLE ficheiros_modelo
    ADD CONSTRAINT fk_ficheiros_modelo_utilizadores
        FOREIGN KEY (utilizador_id)
            REFERENCES utilizadores(id);