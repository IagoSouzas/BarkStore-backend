# Plano de Ação: Desenvolvimento Backend - Bark Store API (Java/Spring Boot)

Este documento detalha as etapas de desenvolvimento do back-end para o aplicativo Bark Store, contemplando gerenciamento de usuários, pets, endereços e o sistema de autenticação.

## 📌 Fase 1: Configuração Inicial e Infraestrutura

* [ ] Inicializar o projeto Spring Boot (Spring Web, Spring Data JPA, MySQL Driver, Validation, Lombok).
* [ ] Configurar conexão com o banco de dados MySQL no `application.properties` ou `application.yml`.
* [ ] Configurar tratamento de exceções global (Controller Advice) para padronizar respostas de erro.

## 📦 Fase 2: Modelagem de Dados (Entidades e Repositórios)

* [ ] Criar entidade `Usuario` (id, nomeCompleto, email, senha).
* [ ] Criar entidade `Pet` (id, nome, tipo, dataNascimento, peso, fotoUrl).
* [ ] Criar entidade `Endereco` (id, nomeEndereco, cep, rua, numero, complemento, bairro, cidade, estado).
* [ ] Mapear relacionamento 1:N entre `Usuario` e `Pet` (`@OneToMany` e `@ManyToOne`).
* [ ] Mapear relacionamento 1:N entre `Usuario` e `Endereco` (`@OneToMany` e `@ManyToOne`).
* [ ] Criar interfaces `UsuarioRepository`, `PetRepository` e `EnderecoRepository` estendendo `JpaRepository`.

## 🔒 Fase 3: Segurança e Autenticação (Spring Security + JWT)

* [ ] Adicionar dependências do Spring Security e JWT (ex: `java-jwt` ou `jjwt`).
* [ ] Implementar classe de serviço para geração e validação de tokens JWT.
* [ ] Criar os filtros de segurança (`SecurityFilterChain`) para proteger os endpoints.
* [ ] Desenvolver serviço de autenticação (`AuthenticationManager`).
* [ ] Criar DTOs de Login (`LoginRequestDTO`, `TokenResponseDTO`).
* [ ] Criar endpoint público `POST /auth/login` para gerar o token.

## 👤 Fase 4: CRUD de Usuários

* [ ] Criar DTOs para Usuário (`UsuarioCreateDTO`, `UsuarioResponseDTO`).
* [ ] Implementar `UsuarioService`:
  * [ ] Cadastrar novo usuário (com hash de senha usando `BCrypt`).
  * [ ] Buscar dados do usuário autenticado.
  * [ ] Atualizar dados do usuário.
  * [ ] Excluir conta.
* [ ] Criar `UsuarioController` mapeando os endpoints correspondentes.
* [ ] Configurar `POST /usuarios` como rota pública (cadastro).

## 🐾 Fase 5: Gerenciamento de Pets (1:N)

* [ ] Criar DTOs para Pet (`PetRequestDTO`, `PetResponseDTO`).
* [ ] Implementar `PetService`:
  * [ ] Cadastrar Pet vinculado ao ID do usuário autenticado (extraído do token JWT).
  * [ ] Listar Pets do usuário logado.
  * [ ] Atualizar e Excluir Pet.
* [ ] Criar `PetController` mapeando os endpoints (todas as rotas protegidas).

## 📍 Fase 6: Gerenciamento de Endereços (1:N)

* [ ] Criar DTOs para Endereço (`EnderecoRequestDTO`, `EnderecoResponseDTO`).
* [ ] Implementar `EnderecoService`:
  * [ ] Adicionar endereço vinculado ao usuário logado.
  * [ ] Listar endereços salvos do usuário.
  * [ ] Atualizar e remover endereço.
* [ ] Criar `EnderecoController` mapeando os endpoints (todas as rotas protegidas).

## 🧪 Fase 7: Documentação e Testes

* [ ] Adicionar dependência do Swagger/OpenAPI (`springdoc-openapi`).
* [ ] Documentar os endpoints, parâmetros e esquemas de autenticação na interface do Swagger.
* [ ] Realizar testes de integração das principais rotas usando Postman ou Insomnia.
* [ ] (Opcional) Escrever testes unitários com JUnit e Mockito para os Services.