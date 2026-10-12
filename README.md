<div align="center">

# Origo API

**API REST de gestão de produtos, com servidor MCP para agentes de IA**

Java · Spring Boot · PostgreSQL · Docker · MCP

![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.7-6DB33F?logo=springboot&logoColor=white) ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Neon-4169E1?logo=postgresql&logoColor=white) ![Maven](https://img.shields.io/badge/Maven-wrapper-C71A36?logo=apachemaven&logoColor=white) ![Docker](https://img.shields.io/badge/Docker-ready-2496ED?logo=docker&logoColor=white) ![Swagger](https://img.shields.io/badge/Swagger-UI-85EA2D?logo=swagger&logoColor=black) ![MCP](https://img.shields.io/badge/MCP-Streamable%20HTTP-000000)

**[API em produção](https://origo-api.onrender.com)** · **[Documentação Swagger](https://origo-api.onrender.com/swagger-ui.html)**

</div>

---

## Sobre

A Origo API é uma API REST de gerenciamento de produtos construída com Spring Boot 4. É o meu primeiro projeto em Java e faz parte de um portfólio pessoal voltado para fintech e logística.

O objetivo é praticar o ciclo completo de uma API: endpoints REST, persistência em PostgreSQL, tratamento de erros padronizado e boas práticas de desenvolvimento. Além do REST, as operações de produto também são expostas como ferramentas MCP, para que agentes de IA consigam gerenciar o estoque por conversa.

## Recursos

- **CRUD de produtos:** listar, buscar por ID, criar, atualizar e remover.
- **Validação de entrada:** Bean Validation (Jakarta) nos dados recebidos.
- **Erros padronizados:** toda resposta de erro segue o mesmo formato JSON (`message` e `status`).
- **Documentação interativa:** Swagger UI gerado automaticamente pelo springdoc-openapi.
- **Servidor MCP:** cinco ferramentas para agentes de IA, via Streamable HTTP em `/mcp`.
- **Configuração por `.env`:** credenciais fora do código e do Git.
- **Docker:** imagem pronta para deploy.

## Stack

| Tecnologia | Versão | Papel |
|---|---|---|
| Java | 21 | Linguagem |
| Spring Boot | 4.0.7 | Framework (Spring Framework 7) |
| Spring Data JPA | — | Persistência |
| PostgreSQL | Neon | Banco de dados |
| Jakarta Bean Validation | — | Validação de entrada |
| Lombok | — | Reduz código repetitivo (getters, builders, construtores) |
| springdoc-openapi | — | Swagger UI |
| Spring AI | — | Servidor MCP |
| springboot4-dotenv | — | Carrega o arquivo `.env` automaticamente |
| Maven | Wrapper | Build |
| Docker | — | Container para deploy |

## Como rodar

**Requisitos:** JDK 21 (o Maven Wrapper já vem no projeto, não precisa instalar o Maven).

```bash
git clone https://github.com/igordesouzabranco/origo-api.git
cd origo-api
cp .env.example .env
```

Preencha o `.env` com os dados do seu PostgreSQL:

```env
DATABASE_URL=jdbc:postgresql://<host>/<banco>?sslmode=require
DATABASE_USERNAME=seu_usuario
DATABASE_PASSWORD=sua_senha
```

Depois suba a API:

```bash
./mvnw spring-boot:run
```

| Comando | O que faz |
|---|---|
| `./mvnw spring-boot:run` | Sobe a API na porta 8082 |
| `docker build -t origo-api .` | Gera a imagem Docker |
| `docker run -p 8082:8082 -e DATABASE_URL=... -e DATABASE_USERNAME=... -e DATABASE_PASSWORD=... origo-api` | Roda a API em container |
| `npx @modelcontextprotocol/inspector http://localhost:8082/mcp` | Testa o servidor MCP (transporte Streamable HTTP) |

> Se o build travar em máquinas com pouca RAM, defina `MAVEN_OPTS=-Xmx512m`. Nunca coloque credenciais no `application.yaml` nem versione o `.env` (ele já está no `.gitignore`).

## Produção

| Peça | Onde |
|---|---|
| API | Render (free tier, rodando em Docker) |
| Banco | Neon PostgreSQL (free tier) |
| Documentação | [Swagger UI](https://origo-api.onrender.com/swagger-ui.html) |

No Render, configure as mesmas três variáveis do `.env`: `DATABASE_URL` (formato JDBC), `DATABASE_USERNAME` e `DATABASE_PASSWORD`.

O Render free hiberna por inatividade, então a primeira requisição depois de um tempo parado leva cerca de 30 a 50 segundos (cold start). O banco no Neon também suspende sozinho e volta automaticamente.

## Rotas

Local: `http://localhost:8082` · Produção: `https://origo-api.onrender.com`

### REST

| Método | Rota | Descrição | Respostas |
|---|---|---|---|
| GET | `/produtos` | Lista todos os produtos | 200 |
| GET | `/produtos/{id}` | Busca um produto por ID | 200 / 404 |
| POST | `/produtos` | Cria um produto | 201 / 400 |
| PUT | `/produtos/{id}` | Atualiza um produto | 200 / 400 / 404 |
| DELETE | `/produtos/{id}` | Remove um produto | 204 / 404 |

Corpo do `POST` e do `PUT`:

```json
{
  "name": "Notebook",
  "price": 2499.90,
  "amount": 10
}
```

| Campo | Tipo | Regra |
|---|---|---|
| `name` | String | Obrigatório, não pode ficar em branco |
| `price` | Decimal | Obrigatório, maior que zero |
| `amount` | Inteiro | Obrigatório, zero ou maior |

### Formato de erro

```json
{
  "message": "Produto não encontrado",
  "status": 404
}
```

| Status | Quando acontece |
|---|---|
| 404 | Produto não encontrado (`NotFoundException`) ou rota inexistente (`NoResourceFoundException`, mensagem "Recurso não encontrado") |
| 400 | Erro de validação nos dados enviados (`MethodArgumentNotValidException`) |
| 500 | Erro interno inesperado (`Exception`, mensagem "Erro interno inesperado") |

Nos erros de validação, a mensagem lista cada campo inválido no formato `campo: mensagem`, separados por vírgula:

```json
{
  "message": "name: Nome não pode ficar em branco, price: Preço deve ser maior que zero",
  "status": 400
}
```

### MCP

Endpoint: `http://localhost:8082/mcp` (Streamable HTTP) · Servidor: `origo-api-mcp` v`0.0.1`

Os parâmetros das ferramentas MCP estão em português (`nome`, `preco`, `quantidade`), diferente dos campos do REST (`name`, `price`, `amount`).

| Ferramenta | Descrição | Parâmetros |
|---|---|---|
| `listarProdutos` | Lista todos os produtos | — |
| `buscarProduto` | Busca um produto por ID | `id` (Long) |
| `criarProduto` | Cria um produto | `nome` (String), `preco` (BigDecimal), `quantidade` (Integer) |
| `atualizarProduto` | Atualiza um produto | `id` (Long), `nome`, `preco`, `quantidade` |
| `removerProduto` | Remove um produto | `id` (Long) |

Para conectar o Claude Desktop ou o Claude Code, adicione o servidor na configuração de MCP:

```json
{
  "mcpServers": {
    "origo-api": {
      "transport": "streamable-http",
      "url": "http://localhost:8082/mcp"
    }
  }
}
```

Depois é só pedir em linguagem natural, por exemplo: *"cria um produto Notebook a 2499.90 com 10 unidades"*.

> O `GlobalExceptionHandler` só trata requisições HTTP. Nas chamadas MCP, o Spring AI converte as exceções em resultados de erro da ferramenta, usando a mensagem da exceção (por exemplo, "Produto não encontrado").

## Roadmap

- Suporte ao método HTTP `QUERY` (RFC 10008), que o Spring ainda não oferece nativamente. A ideia é implementar com um Servlet Filter manual.
- Trocar o `ddl-auto: update` por migrations com Flyway ou Liquibase. Hoje o `update` serve só para desenvolvimento e não deve ser usado em produção de verdade.

## Estrutura

```
origo-api/
├── .mvn/wrapper/                          # Maven Wrapper (não precisa instalar o Maven)
├── src/
│   ├── main/
│   │   ├── java/igor/branco/origo/
│   │   │   ├── OrigoApplication.java      # classe principal do Spring Boot
│   │   │   ├── config/
│   │   │   │   └── OpenApiConfig.java     # metadados do Swagger (título, versão, contato)
│   │   │   ├── controller/
│   │   │   │   └── ProdutoController.java # rotas REST de /produtos
│   │   │   ├── dto/
│   │   │   │   └── ProdutoDto.java        # dados de entrada com validações
│   │   │   ├── entity/
│   │   │   │   └── Produto.java           # entidade JPA (tabela produtos)
│   │   │   ├── exception/
│   │   │   │   ├── ErrorResponse.java     # formato padrão de erro
│   │   │   │   └── NotFoundException.java # produto não encontrado
│   │   │   ├── handler/
│   │   │   │   └── GlobalExceptionHandler.java  # traduz exceções em respostas JSON
│   │   │   ├── mcp/
│   │   │   │   └── ProdutoMcpTools.java   # ferramentas MCP para agentes de IA
│   │   │   ├── repository/
│   │   │   │   └── ProdutoRepository.java # acesso ao banco (Spring Data JPA)
│   │   │   └── service/
│   │   │       └── ProdutoService.java    # regras de negócio
│   │   └── resources/
│   │       ├── application.yaml           # banco, MCP e porta
│   │       └── static/index.html          # página inicial da API
│   └── test/java/igor/branco/origo/
│       └── OrigoApplicationTests.java     # teste de carregamento do contexto
├── .env.example                           # modelo das variáveis de ambiente
├── .gitignore                             # ignora o .env real
├── Dockerfile                             # imagem usada no deploy no Render
├── pom.xml                                # dependências e build (Maven)
└── README.md
```

---
