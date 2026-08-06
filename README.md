# Origo API

API REST de gerenciamento de produtos, construida com Spring Boot 4.

## Sobre o projeto:

A Origo API é o meu primeiro projeto em Java, criado como parte do meu portfolio pessoal com foco em fintech e logistica. O objetivo é construir uma API REST completa com persistência em PostgreSQL, tratamento de erros limpo e boas práticas de desenvolvimento.

## Stack técnica:

- **Linguagem:** Java 21
- **Framework:** Spring Boot 4.0.7 (Spring Framework 7)
- **Persistencia:** Spring Data JPA + PostgreSQL (Neon)
- **Validacao:** Bean Validation (Jakarta Validation)
- **Documentacao:** springdoc-openapi (Swagger UI)
- **Build:** Maven (wrapper incluso)

## Como rodar localmente

### Pré-requisitos:

- Java 21 (JDK) br
- Maven (wrapper incluso no projeto)

### Variáveis de ambiente:

Configure as seguintes variáveis de ambiente antes de rodar a aplicação:

```bash
export DATABASE_URL="jdbc:postgresql://<host>/<database>?sslmode=require"
export DATABASE_USERNAME="<seu-usuario>"
export DATABASE_PASSWORD="<sua-senha>"
```

> **Importante:** Nunca hardcode credenciais no `application.yaml` ou committe-as no Git.

### Executar:

```bash
./mvnw spring-boot:run
```

> **Nota:** Se o build travar em máquinas com pouca RAM, defina `MAVEN_OPTS=-Xmx512m`.

A aplicação estará disponivel em `http://localhost:8082`.

## Endpoints da API:

| Metodo | Rota                 | Descricao                     | Status      |
|--------|----------------------|-------------------------------|-------------|
| GET    | /v1/produtos         | Lista todos os produtos       | 200         |
| GET    | /v1/produtos/{id}    | Busca um produto por id       | 200 / 404   |
| POST   | /v1/produtos         | Cria um produto               | 201         |
| PUT    | /v1/produtos/{id}    | Atualiza um produto           | 200 / 404   |
| DELETE | /v1/produtos/{id}    | Remove um produto             | 204 / 404   |

## Documentação interativa

A documentação OpenAPI (Swagger UI) está disponivel em:

- **Produção:** https://origo-api.onrender.com/swagger-ui.html
- **Local:** http://localhost:8082/swagger-ui.html

## Tratamento de erros

A API retorna erros no formato padronizado:

```json
{
  "message": "Descricao do erro",
  "status": 404
}
```

### Casos cobertos

- **404** - Recurso nao encontrado (`NotFoundException`)
- **400** - Erros de validacao de entrada (`MethodArgumentNotValidException`)
- **500** - Erros internos genericos (`Exception`)

## Deploy

**API em producao:** https://origo-api.onrender.com

- **Banco de dados:** Neon (PostgreSQL free tier) - auto-suspende e retoma sozinho apos inatividade
- **Aplicacao:** Render (free tier) com Docker - nota sobre cold start de ~30-50s apos inatividade

### Variáveis de ambiente (Render):

| Variável | Valor |
|---|---|
| `DATABASE_URL` | `jdbc:postgresql://<host>/<database>?sslmode=require` |
| `DATABASE_USERNAME` | `neondb_owner` |
| `DATABASE_PASSWORD` | *(senha do Neon)* |

### Build com Docker

```bash
docker build -t origo-api .
docker run -p 8082:8082 -e DATABASE_URL="..." -e DATABASE_USERNAME="..." -e DATABASE_PASSWORD="..." origo-api
```

> **Próximo passo:** Em produção real, o ideal seria usar Flyway ou Liquibase para migrações de banco de dados. Atualmente utiliza-se `ddl-auto: update` para fins de desenvolvimento.

## Roadmap

- Suporte ao metodo HTTP QUERY (RFC 10008) - ainda sem suporte nativo no Spring, implementação planejada via Servlet Filter manual
