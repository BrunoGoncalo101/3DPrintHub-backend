
INSERT INTO categorias (nome, ativa) VALUES
                                         ('Decoração', TRUE),
                                         ('Miniaturas', TRUE),
                                         ('Organização', TRUE),
                                         ('Acessórios', TRUE),
                                         ('Casa e Escritório', TRUE);

INSERT INTO materiais (nome, preco_por_grama, densidade, ativo) VALUES
                                                                    ('PLA', 0.0250, 1.240, TRUE),
                                                                    ('PETG', 0.0350, 1.270, TRUE),
                                                                    ('ABS', 0.0400, 1.040, TRUE),
                                                                    ('TPU', 0.0550, 1.210, TRUE);

INSERT INTO cores (nome, codigo_hex, disponivel, material_id) VALUES
    ('Branco', '#FFFFFF', TRUE, (SELECT id FROM materiais WHERE nome = 'PLA' ORDER BY id LIMIT 1)),
('Preto', '#000000', TRUE, (SELECT id FROM materiais WHERE nome = 'PLA' ORDER BY id LIMIT 1)),
('Vermelho', '#FF0000', TRUE, (SELECT id FROM materiais WHERE nome = 'PLA' ORDER BY id LIMIT 1)),
('Azul', '#0000FF', TRUE, (SELECT id FROM materiais WHERE nome = 'PETG' ORDER BY id LIMIT 1)),
('Cinza', '#808080', TRUE, (SELECT id FROM materiais WHERE nome = 'PETG' ORDER BY id LIMIT 1)),
('Preto', '#000000', TRUE, (SELECT id FROM materiais WHERE nome = 'ABS' ORDER BY id LIMIT 1)),
('Preto', '#000000', TRUE, (SELECT id FROM materiais WHERE nome = 'TPU' ORDER BY id LIMIT 1));

INSERT INTO produtos
(nome, descricao, preco_base, imagem_url, dimensoes, ativo, categoria_id)
VALUES
    ('Vaso Geométrico', 'Vaso decorativo com padrão geométrico.', 12.99, NULL, '12x12x18 cm', TRUE, (SELECT id FROM categorias WHERE nome = 'Decoração' ORDER BY id LIMIT 1)),
('Suporte para Telemóvel', 'Suporte ajustável para telemóvel.', 7.50, NULL, '10x8x12 cm', TRUE, (SELECT id FROM categorias WHERE nome = 'Acessórios' ORDER BY id LIMIT 1)),
('Dragão Articulado', 'Miniatura de dragão com articulações.', 19.90, NULL, '25x8x6 cm', TRUE, (SELECT id FROM categorias WHERE nome = 'Miniaturas' ORDER BY id LIMIT 1)),
('Organizador de Secretária', 'Organizador para canetas e acessórios.', 14.50, NULL, '20x12x10 cm', TRUE, (SELECT id FROM categorias WHERE nome = 'Organização' ORDER BY id LIMIT 1)),
('Porta-Chaves Personalizável', 'Porta-chaves com espaço para personalização.', 3.99, NULL, '6x3x0.5 cm', TRUE, (SELECT id FROM categorias WHERE nome = 'Acessórios' ORDER BY id LIMIT 1)),
('Candeeiro Decorativo', 'Estrutura decorativa para iluminação LED.', 24.90, NULL, '15x15x22 cm', TRUE, (SELECT id FROM categorias WHERE nome = 'Casa e Escritório' ORDER BY id LIMIT 1)),
('Miniatura de Castelo', 'Modelo decorativo de um castelo medieval.', 17.50, NULL, '15x15x14 cm', TRUE, (SELECT id FROM categorias WHERE nome = 'Miniaturas' ORDER BY id LIMIT 1)),
('Suporte para Auscultadores', 'Suporte de secretária para auscultadores.', 11.99, NULL, '12x12x25 cm', TRUE, (SELECT id FROM categorias WHERE nome = 'Casa e Escritório' ORDER BY id LIMIT 1)),
('Caixa Organizadora', 'Caixa para guardar pequenos objetos.', 9.90, NULL, '15x10x8 cm', TRUE, (SELECT id FROM categorias WHERE nome = 'Organização' ORDER BY id LIMIT 1)),
('Suporte para Comando', 'Suporte para comando de videojogos.', 8.50, NULL, '12x10x10 cm', TRUE, (SELECT id FROM categorias WHERE nome = 'Acessórios' ORDER BY id LIMIT 1));
