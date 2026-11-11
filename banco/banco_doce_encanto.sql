-- =====================================================
-- BANCO DE DADOS: LOJA VIRTUAL DOCE ENCANTO (CUPCAKES)
-- Compatível com MySQL 8
-- Como usar: abra este arquivo no MySQL Workbench e
-- clique no raio (Execute). Pronto!
-- =====================================================

-- 1) Cria o banco (a "pasta" que guarda todas as tabelas)
CREATE DATABASE IF NOT EXISTS doce_encanto
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE doce_encanto;

-- =====================================================
-- 2) TABELAS
-- =====================================================

-- CLIENTES: quem se cadastra no site.
-- O campo "tipo" diferencia o cliente comum do administrador.
CREATE TABLE clientes (
  id_cliente     INT AUTO_INCREMENT PRIMARY KEY,
  nome           VARCHAR(100) NOT NULL,
  email          VARCHAR(120) NOT NULL UNIQUE,
  senha_hash     VARCHAR(255) NOT NULL,
  telefone       VARCHAR(20),
  tipo           ENUM('CLIENTE','ADMIN') NOT NULL DEFAULT 'CLIENTE',
  data_cadastro  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- CATEGORIAS: grupos de cupcakes (Clássicos, Frutas...)
CREATE TABLE categorias (
  id_categoria  INT AUTO_INCREMENT PRIMARY KEY,
  nome          VARCHAR(60) NOT NULL UNIQUE,
  descricao     VARCHAR(200)
);

-- PRODUTOS: os cupcakes que aparecem na vitrine
CREATE TABLE produtos (
  id_produto    INT AUTO_INCREMENT PRIMARY KEY,
  id_categoria  INT NOT NULL,
  nome          VARCHAR(80) NOT NULL,
  descricao     VARCHAR(255),
  preco         DECIMAL(10,2) NOT NULL,
  imagem_url    VARCHAR(255),
  ativo         BOOLEAN NOT NULL DEFAULT TRUE,
  FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria)
);

-- ENDERECOS: onde o cliente quer receber o pedido
CREATE TABLE enderecos (
  id_endereco  INT AUTO_INCREMENT PRIMARY KEY,
  id_cliente   INT NOT NULL,
  rua          VARCHAR(120) NOT NULL,
  numero       VARCHAR(10) NOT NULL,
  complemento  VARCHAR(60),
  bairro       VARCHAR(60) NOT NULL,
  cidade       VARCHAR(60) NOT NULL,
  estado       CHAR(2) NOT NULL,
  cep          VARCHAR(9) NOT NULL,
  FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente)
);

-- PEDIDOS: cada compra feita por um cliente
CREATE TABLE pedidos (
  id_pedido    INT AUTO_INCREMENT PRIMARY KEY,
  id_cliente   INT NOT NULL,
  id_endereco  INT NOT NULL,
  data_pedido  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  status       ENUM('PENDENTE','PAGO','ENTREGUE','CANCELADO') NOT NULL DEFAULT 'PENDENTE',
  valor_total  DECIMAL(10,2) NOT NULL,
  FOREIGN KEY (id_cliente)  REFERENCES clientes(id_cliente),
  FOREIGN KEY (id_endereco) REFERENCES enderecos(id_endereco)
);

-- ITENS_PEDIDO: quais cupcakes (e quantos) estão em cada pedido
CREATE TABLE itens_pedido (
  id_item         INT AUTO_INCREMENT PRIMARY KEY,
  id_pedido       INT NOT NULL,
  id_produto      INT NOT NULL,
  quantidade      INT NOT NULL,
  preco_unitario  DECIMAL(10,2) NOT NULL,
  FOREIGN KEY (id_pedido)  REFERENCES pedidos(id_pedido) ON DELETE CASCADE,
  FOREIGN KEY (id_produto) REFERENCES produtos(id_produto)
);

-- PAGAMENTOS: registro do pagamento (fictício) de cada pedido
CREATE TABLE pagamentos (
  id_pagamento    INT AUTO_INCREMENT PRIMARY KEY,
  id_pedido       INT NOT NULL UNIQUE,
  forma           ENUM('PIX','CARTAO') NOT NULL,
  valor           DECIMAL(10,2) NOT NULL,
  status          ENUM('PENDENTE','APROVADO','RECUSADO') NOT NULL DEFAULT 'PENDENTE',
  data_pagamento  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido)
);

-- ENTREGAS: acompanhamento da entrega de cada pedido
CREATE TABLE entregas (
  id_entrega     INT AUTO_INCREMENT PRIMARY KEY,
  id_pedido      INT NOT NULL UNIQUE,
  status         ENUM('AGUARDANDO','EM_ROTA','ENTREGUE') NOT NULL DEFAULT 'AGUARDANDO',
  data_prevista  DATE,
  data_entrega   DATETIME,
  FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido)
);

-- =====================================================
-- 3) DADOS DE EXEMPLO (para a vitrine já ter cupcakes)
-- =====================================================

INSERT INTO categorias (nome, descricao) VALUES
  ('Clássicos', 'Os sabores tradicionais de sempre'),
  ('Frutas',    'Sabores leves e frescos'),
  ('Especiais', 'Receitas mais elaboradas');

INSERT INTO produtos (id_categoria, nome, descricao, preco) VALUES
  (1, 'Brigadeiro',  'Massa de chocolate com cobertura de brigadeiro', 7.50),
  (2, 'Morango',     'Massa de baunilha com creme de morango',         8.00),
  (2, 'Limão',       'Massa cítrica com glacê de limão',               7.50),
  (1, 'Baunilha',    'Clássico de baunilha com chantilly',             6.50),
  (3, 'Red velvet',  'Massa aveludada com cream cheese',               9.00),
  (3, 'Ninho',       'Creme de leite ninho com cobertura cremosa',     8.50);

-- =====================================================
-- 4) CONFERÊNCIA: rode isto para ver se deu certo
-- =====================================================
SHOW TABLES;
SELECT p.nome, c.nome AS categoria, p.preco
FROM produtos p
JOIN categorias c ON c.id_categoria = p.id_categoria;
