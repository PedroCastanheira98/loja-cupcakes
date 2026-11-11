# Doce Encanto – Loja virtual de cupcakes

Projeto Integrador Transdisciplinar em Engenharia de Software II.

Loja virtual em que o cliente se cadastra, escolhe cupcakes na vitrine, monta o carrinho, informa o endereço, paga (pagamento fictício) e consulta seus pedidos. Há uma área administrativa para consultar pedidos e gerenciar os cupcakes.

**Autor:** Pedro Castanheira· **RGM:** 38174405

## Funcionalidades

- Cadastro e login de clientes (senha criptografada com BCrypt)
- Vitrine de cupcakes com filtro por categoria
- Carrinho de compras
- Finalização do pedido com endereço de entrega
- Registro de pagamento fictício (Pix ou Cartão) e de entrega
- Consulta dos pedidos pelo cliente
- Área administrativa: consulta de pedidos e cadastro, edição, ocultação e exclusão de cupcakes

## Tecnologias

- Java 21
- Spring Boot 4.1 (Web MVC, Data JPA, Security)
- Thymeleaf, HTML e CSS
- MySQL 8
- Maven
- Arquitetura em camadas, no padrão MVC

## Estrutura do projeto

```
banco/                       Script SQL do banco de dados
docs/                        Documentação, dicionário de dados e testes
src/main/java/.../model        Entidades (tabelas)
src/main/java/.../repository   Acesso ao banco
src/main/java/.../service      Regras de negócio (carrinho, pedido, login)
src/main/java/.../controller   Controladores (páginas e ações)
src/main/java/.../config       Segurança
src/main/resources/templates   Páginas HTML (View)
src/main/resources/static/css  Estilo
```

## Como executar

Requisitos: Java 21, MySQL 8 e uma IDE como o IntelliJ IDEA.

1. No MySQL Workbench, abra e execute o arquivo `banco/banco_doce_encanto.sql`. Ele cria o banco `doce_encanto`, as 8 tabelas e alguns cupcakes de exemplo.
2. Defina a variável de ambiente `DB_SENHA` com a senha do seu MySQL.
   - No IntelliJ: **Run > Edit Configurations > Modify options > Environment variables** e escreva `DB_SENHA=sua_senha`.
   - Se o seu usuário do MySQL não for `root`, altere `spring.datasource.username` em `src/main/resources/application.properties`.
3. Execute a classe `LojaApplication`.
4. Abra http://localhost:8080 no navegador.

### Criar um administrador

1. Cadastre-se normalmente no site.
2. No MySQL, execute:

```sql
USE doce_encanto;
UPDATE clientes SET tipo = 'ADMIN' WHERE id_cliente = 1;
```

(troque o `1` pelo `id_cliente` do seu cadastro)

3. Saia do site e entre de novo. O link **Admin** aparece no menu.

## Documentação

Na pasta `docs`:

- Documentação do projeto (requisitos, arquitetura MVC, interface, modelo de dados)
- Dicionário de dados
- Documento de testes

## Observações

- O pagamento é fictício e nenhuma cobrança real é feita.
- O sistema roda localmente e não está hospedado na internet.
