To-Do List API – Spring Boot
Uma API RESTful desenvolvida em Java com Spring Boot, projetada para gerenciar tarefas (To-Dos) de forma simples, eficiente e escalável. 
O projeto implementa boas práticas de arquitetura, documentação com Swagger/OpenAPI, e utiliza camadas bem definidas para facilitar manutenção e evolução contínua do sistema.

1. Tecnologias Utilizadas
Java 21
Spring Boot 3+
Spring Web
Spring Data JPA
Spring Validation
Spring Security
Hibernate
PostgreSQL / H2 
Maven
Lombok
OpenAPI/Swagger 3 (Springdoc)

2. Arquitetura

A API segue uma arquitetura organizada em camadas:

controller/   → Camada de entrada (REST endpoints)
service/      → Regras de negócio
repository/   → Persistência e ORM
entity/       → Mapeamento das entidades JPA
dto/          → Objetos de transferência para requests/responses
exceptions/   → Custom exceptions e handlers globais
config/       → Configurações (ex.: OpenAPI, Security)

3. Funcionalidades
Gerenciamento de Tarefas (To-Do)
Criar uma nova tarefa
Buscar todas as tarefas
Buscar tarefa por ID
Atualizar tarefa
Alterar status (PENDING, IN_PROGRESS, COMPLETED)
Remover uma tarefa

Gerenciamento de Usuários
Cadastro de usuários
Login
Autenticação JWT
Associação de tarefas ao usuário

4. Endpoints Principais
GET    /api/tasks - /api/users
GET    /api/tasks/{id} - /api/users{id}
POST   /api/tasks - /api/users
PUT    /api/tasks/{id} - /api/users{id}
PATCH  /api/tasks/{id}/status 
DELETE /api/tasks/{id} - /api/users
POST   /api/auth/register
POST   /api/auth/login

5. Documentação da API (Swagger/OpenAPI)
Inclui descrição dos endpoints, modelos de requisição e resposta, códigos HTTP e exemplos.

6. Melhorias Futuras

Melhorar a autenticação e autorização JWT/OAuth2
Paginação e filtros
Deploy em ambiente cloud (Railway, Render, AWS, Azure)
Testes unitários e integração com JUnit + Mockito
Notificações por e-mail
Integração com front-end React/Angular
