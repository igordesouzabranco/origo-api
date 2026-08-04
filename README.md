# Origo API

API REST de gerenciamento de produtos, construida com Spring Boot 4.

## Sobre o projeto

A Origo API e o meu primeiro projeto em Java, criado como parte do meu portfolio pessoal com foco em fintech e logistica. O objetivo e construir uma API REST completa com persistencia em PostgreSQL, tratamento de erros limpo e boas praticas de desenvolvimento.

## Stack tecnica

- **Linguagem:** Java 21
- **Framework:** Spring Boot 4.0.7 (Spring Framework 7)
- **Persistencia:** Spring Data JPA + PostgreSQL (Neon)
- **Validacao:** Bean Validation (Jakarta Validation)
- **Documentacao:** springdoc-openapi (Swagger UI)
- **Build:** Maven (wrapper incluso)

## Como rodar localmente

### Pre-requisitos

- Java 21 (JDK)
- Maven (wrapper incluso no projeto)

### Variaveis de ambiente

Configure as seguintes variaveis de ambiente antes de rodar a aplicacao:

```bash
export DATABASE_URL="jdbc:postgresql://<host>/<database>?sslmode=require"
export DATABASE_USERNAME="<seu-usuario>"
export DATABASE_PASSWORD="<sua-senha>"
```

> **Importante:** Nunca hardcode credenciais no `application.yaml` ou committe-as no Git.

### Executar

```bash
./mvnw spring-boot:run
```

> **Nota:** Se o build travar em maquinas com pouca RAM, defina `MAVEN_OPTS=-Xmx512m`.

A aplicacao estara disponivel em `http://localhost:8082`.

## Endpoints da API

| Metodo | Rota                 | Descricao                     | Status      |
|--------|----------------------|-------------------------------|-------------|
| GET    | /v1/produtos         | Lista todos os produtos       | 200         |
| GET    | /v1/produtos/{id}    | Busca um produto por id       | 200 / 404   |
| POST   | /v1/produtos         | Cria um produto               | 201         |
| PUT    | /v1/produtos/{id}    | Atualiza um produto           | 200 / 404   |
| DELETE | /v1/produtos/{id}    | Remove um produto             | 204 / 404   |

## Documentacao interativa

A documentacao OpenAPI (Swagger UI) esta disponivel em:

```
http://localhost:8082/swagger-ui.html
```

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

- **Banco de dados:** Neon (PostgreSQL free tier) - auto-suspende e retoma sozinho apos inatividade
- **Aplicacao:** Render (free tier) - nota sobre cold start de ~30-50s apos inatividade

> **Proximo passo:** Em producao real, o ideal seria usar Flyway ou Liquibase para migracoes de banco de dados. Atualmente utiliza-se `ddl-auto: update` para fins de desenvolvimento.

## Roadmap

- Suporte ao metodo HTTP QUERY (RFC 10008) - ainda sem suporte nativo no Spring, implementacao planejada via Servlet Filter manual
