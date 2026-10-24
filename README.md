# 3D Print Hub — API (Back-end)

API REST da loja online de impressão 3D **3D Print Hub**. Permite aos clientes comprar modelos do catálogo ou enviar o seu próprio ficheiro 3D (`.stl`, `.obj`, `.3mf`) para orçamento e impressão, e ao administrador gerir o catálogo, os pedidos e as encomendas.

Projeto da unidade curricular de Laboratório de Projeto — CTeSP em Desenvolvimento para a Web e Dispositivos Móveis, ESTG — Politécnico do Porto.

## Tecnologias

| Camada | Tecnologia |
| --- | --- |
| Linguagem | Java 21 |
| Framework | Spring Boot 4 (Spring Web MVC, Spring Data JPA, Bean Validation) |
| Segurança | Spring Security, JWT (OAuth2 Resource Server), BCrypt |
| Base de dados | MySQL 8.4 no Azure Database for MySQL – Flexible Server |
| Migrações | Flyway |
| Documentação | springdoc-openapi (Swagger UI) |
| Build | Maven (Maven Wrapper incluído) |
| CI | GitHub Actions |

## Estrutura do projeto

```
src/main/java/pt/utp/estg/pp/printhub/
├── config/       Configuração (segurança, Swagger)
├── controller/   Endpoints REST
├── service/      Lógica de negócio
├── repository/   Acesso à base de dados (Spring Data JPA)
├── entity/       Entidades JPA
├── dto/          Objetos de entrada e saída da API
├── model/        Enums e conversores
└── exception/    Exceções e tratamento global de erros

src/main/resources/
├── application.properties
└── db/migration/ Migrações Flyway
```

## Pré-requisitos

- **JDK 21**
- **Git**
- **Acesso à base de dados:** o servidor MySQL no Azure só aceita ligações de IPs autorizados. Pede ao responsável pela base de dados para acrescentar o teu IP público à firewall.

Não é preciso instalar o Maven: o projeto inclui o Maven Wrapper (`mvnw` / `mvnw.cmd`).

## Configuração

As credenciais **nunca** ficam no código nem no repositório. A aplicação lê-as de variáveis de ambiente:

| Variável | Descrição |
| --- | --- |
| `DB_HOST` | Endereço do servidor MySQL |
| `DB_USER` | Utilizador da base de dados |
| `DB_PASSWORD` | Palavra-passe da base de dados |
| `JWT_SECRET` | Chave de assinatura dos tokens JWT (mínimo 32 caracteres) |

Os valores de `DB_*` são partilhados em privado pelo responsável pela base de dados.

Para gerar um `JWT_SECRET` para desenvolvimento (PowerShell):

```powershell
$b = New-Object byte[] 48; [Security.Cryptography.RandomNumberGenerator]::Create().GetBytes($b); [Convert]::ToBase64String($b)
```

### No IntelliJ IDEA

1. Abrir o projeto e, se não for reconhecido como Maven: botão direito no `pom.xml` → **Add as Maven Project**.
2. Em **Run → Edit Configurations**, criar uma configuração **Spring Boot** (ou **Application**) com a main class `pt.utp.estg.pp.printhub.PrinthubApplication`.
3. Em **Environment variables**, acrescentar as quatro variáveis acima.
4. Manter **Store as project file** desligado, para as credenciais não irem para o repositório.

## Executar

Pelo IntelliJ, com a configuração acima, ou no terminal (PowerShell):

```powershell
$env:DB_HOST="..."; $env:DB_USER="..."; $env:DB_PASSWORD="..."; $env:JWT_SECRET="..."
.\mvnw.cmd spring-boot:run
```

A API fica disponível em `http://localhost:8080`. Ao arrancar, o Flyway aplica as migrações em falta na base de dados `loja_dev`.

## Documentação da API

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Especificação OpenAPI: `http://localhost:8080/v3/api-docs`

Para testar endpoints que precisam de sessão: fazer login em `POST /auth/login`, copiar o `token` da resposta, carregar em **Authorize** no Swagger e colar o token.

### Endpoints disponíveis

| Método | Endpoint | Descrição | Autenticação |
| --- | --- | --- | --- |
| POST | `/auth/registo` | Criar conta de cliente | Não |
| POST | `/auth/login` | Iniciar sessão (devolve o token JWT) | Não |
| GET | `/utilizadores/me` | Dados do utilizador com sessão | Sim |
| GET | `/produtos`, `/produtos/{id}` | Catálogo e detalhe de produto | Não |
| GET | `/categorias` | Categorias | Não |
| GET | `/materiais` | Materiais | Não |
| POST | `/ficheiros` | Enviar um ficheiro 3D (máx. 50 MB) | Sim |
| GET | `/carrinho` | Ver o carrinho | Sim |
| POST | `/carrinho/itens` | Adicionar ao carrinho | Sim |
| DELETE | `/carrinho/itens/{id}` | Remover do carrinho | Sim |

A lista sempre atualizada está no Swagger.

### Formato dos erros

Todos os erros seguem o mesmo formato JSON:

```json
{ "error": "O email teste@exemplo.pt já está registado.", "code": 409 }
```

## Base de dados e migrações

- A estrutura da base de dados só muda através de migrações Flyway em `src/main/resources/db/migration`.
- Nome das migrações: `V<AAAA>_<MM>_<DD>_<HHMM>__<descricao>.sql` (por exemplo, `V2026_10_19_1130__criar_utilizadores.sql`). A versão por data e hora evita conflitos entre a equipa.
- **Uma migração já aplicada nunca se edita**: corrige-se com uma migração nova.
- Ninguém altera tabelas à mão. O Hibernate está em `ddl-auto=validate`: confirma que as entidades batem certo com as tabelas, mas nunca as altera.

## Testes

```powershell
.\mvnw.cmd -B verify
```

- O GitHub Actions corre este comando em cada pull request para a `main` (`.github/workflows/backend-ci.yml`).
- A coleção do Postman com todos os endpoints está em `docs/`.

## Fluxo de trabalho

- Ninguém trabalha diretamente na `main`.
- Uma branch por tarefa: `tipo/<n.º do item>-descricao`, por exemplo `feat/15-user-registration`.
- Commits com prefixo e o item do Azure Boards: `feat: add user registration with BCrypt AB#15`.
- Prefixos: `feat` (funcionalidade), `fix` (correção), `docs` (documentação), `test` (testes), `ci` (integração contínua), `refactor` (reorganização sem mudar o comportamento), `chore` (manutenção).
- Integração na `main` por pull request, revisto por outro elemento e com o CI a verde.

## Equipa (Grupo 2 — Back-end)

| Elemento | Área | Papel |
| --- | --- | --- |
| Miguel Pinto | Utilizadores, segurança e notificações | Líder e Scrum Master do back-end; DevOps |
| Renato Moreira | Catálogo, materiais, stock e estatísticas | Administração da base de dados |
| Bruno Gonçalo | Pedidos de impressão personalizada | Qualidade (Postman, relatório de bugs) |
| Sandro Rocha | Carrinho, encomendas, pagamentos e fila de produção | Documentação da API (Swagger) |
