# account
Microsserviço de contas do Bagual Bank: cadastro de contas, consulta de saldo, débito e crédito.

## Sobre o serviço
Consumido pelo `transaction` via REST para processar depósitos, saques e transferências. 

As regras de negócio estão na entidade 'Account' afim de proteger o estado interno de manipulação indevida.

## Funcionalidades implementadas
* Criar conta (`POST /accounts`)
* Buscar conta por id (`GET /accounts/{id}`)
* Listar contas (`GET /accounts`)
* Débito de Saldo (`PATCH /accounts/{id}/debit`)
* Crédito de saldo (`PATCH /accounts/{id}/credit`)
* Bloqueio/ativação de conta
* Validação de dados de entrada
* Migração de schema com Flyway
* Testes unitários (regra de negócio e service layer)
* Containerização completa (aplicação + banco via Docker Compose)

## Tecnologias
- Java 21 + Spring Boot 3;
- Maven;
- PostgreSQL;
- Flyway;
- JUnit 5, Mockito e AssertJ;
- Docker e Docker Compose.

## Decisões técnicas
* **UUID como identificador, em vez de sequencial:** é mais adequado à arquiteturas distribuídas e evita enumeration attacks;
* **BigDecimal para valores monetários:** evita erros de arredondamento de ponto flutuante;
* **Rich Domain Model:** regras de negócio vivem na entidade, não apenas no service;
* **Unique Constraint composta (document + account_type):** permite que o mesmo CPF tenha contas de tipos diferentes.

## Como executar

Pré-requisito: Docker Desktop instalado e em execução.

```bash
docker compose up --build -d
```

A API fica disponível em: http://localhost:8081

## Rodando os testes

```bash
mvn test
```
