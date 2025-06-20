﻿# <a name="librarymanagerapi"></a><a name="contribuições"></a><a name="autor"></a><a name="licença"></a>📚 LibraryManagerAPI
Uma API RESTful para gerenciamento de biblioteca com controle de empréstimos e histórico, desenvolvida com **Spring Boot**, **Spring Security (JWT + OAuth2 Google)** e **JPA/Hibernate**.


## 🚀 Funcionalidades Principais
- ✅ Autenticação e autorização com **JWT** e login via **Google OAuth2**
- 📖 Gestão de livros com controle de disponibilidade
- 👤 Cadastro e autenticação de leitores e administradores
- 🔐 Controle de acesso baseado em roles (ADMIN, LEITOR)
- 🔄 Histórico completo de empréstimos com controle de devoluções
- 📊 Paginação, filtros e busca textual


## <a name="funcionalidades-principais"></a>🛠️ Tecnologias Utilizadas
- Java 17
- Spring Boot 3
- Spring Security + JWT + OAuth2
- JPA + Hibernate
- PostgreSQL
- Docker + Docker Compose


## <a name="tecnologias-utilizadas"></a>📦 Como Rodar o Projeto
### <a name="clone-o-repositório"></a>1. Clone o repositório
git clone https://github.com/LeonMesquita/Library-Manager-API.git \
cd LibraryManagerAPI
### 2\. Crie um arquivo .env na raiz com as variáveis de ambiente:
\# PostgreSQL\
DB\_HOST=library\_manager\_db\
DB\_PORT=5432\
DB\_NAME=postgres\
DB\_USERNAME=postgres\
DB\_PASSWORD=postgres\
\
\# JWT\
JWT\_SECRET=uma\_chave\_segura\
JWT\_EXPIRATION=60\
JWT\_REFRESH\_EXPIRATION=1440\
\
\# Google OAuth2\
GOOGLE\_CLIENT\_ID=seu\_client\_id\
GOOGLE\_CLIENT\_SECRET=sua\_client\_secret

💡 Você pode usar um PostgreSQL local ou o que será iniciado via Docker.


### <a name="x4424f9743b32b3b7df52630275c18440715a35a"></a>3. Rodando com Docker
docker-compose up --build

A aplicação estará disponível em: http://localhost:8080


## <a name="como-rodar-o-projeto"></a><a name="rodando-com-docker"></a>🔐 Usuário ADMIN padrão
Ao rodar a aplicação pela primeira vez, um usuário administrador é criado automaticamente:

{\
"email": "admin@gmail.com",\
"password": "new@admin"\
}


## 🚀  Documentação Swagger

Para testar os endpoints da aplicação, entre na página do Swagger: \
http://localhost:8080/api/swagger-ui/index.html \
Para utilizar as rotas autenticadas, execute o endpoint de login, clique no botão "Authorize" e copie o token gerado.



## <a name="usuário-admin-padrão"></a>📬 Endpoints
### 🧑 Usuários

|Método|Endpoint|Descrição|
| :- | :- | :- |
|POST|/api/users|Cadastra novo usuário|
|PUT|/api/users/{id}|Edita usuário (ADMIN ou o próprio dono)|
|GET|/api/users/{id}|Consulta usuário por ID|
|DELETE|/api/users/{id}|Deleta usuário|

Necessário enviar **Bearer Token** nas rotas protegidas.


### <a name="usuários"></a>🔑 Autenticação

|Método|Endpoint|Descrição|
| :- | :- | :- |
|POST|/api/auth/login|Login com email e senha|
|POST|/api/auth/refresh-token|Gera novo token via refreshToken|
|GET|/api/oauth2/authorization/google|Login via Google (tokens retornam nos headers)|


### <a name="autenticação"></a>📖 Livros

|Método|Endpoint|Descrição|
| :- | :- | :- |
|POST|/api/books|Cria novo livro (ADMIN)|
|GET|/api/books|Lista livros com paginação|
|GET|/api/books/{id}|Consulta livro por ID|
|PUT|/api/books/{id}|Edita livro (ADMIN)|
|DELETE|/api/books/{id}|Deleta livro (ADMIN)|

Filtros por título, autor, gênero e disponibilidade podem ser adicionados via query params.

![ref1]
### <a name="livros"></a>📦 Empréstimos

|Método|Endpoint|Descrição|
| :- | :- | :- |
|POST|/api/rentals|Solicita empréstimo|
|GET|/api/rentals/mine|Consulta meus empréstimos|
|GET|/api/rentals|Lista todos os empréstimos (ADMIN)|
|PUT|/api/rentals/{id}/approve|Aprova empréstimo (ADMIN)|
|PUT|/api/rentals/{id}/reject|Rejeita empréstimo (ADMIN)|
|PUT|/api/rentals/{id}/return|Marca devolução|


## <a name="endpoints"></a><a name="empréstimos"></a>✅ Regras de Negócio
- Usuário com role **ADMIN** tem permissões totais.
- Usuário com role **LEITOR** pode gerenciar apenas sua conta e seus empréstimos.
- Cada livro possui controle de quantidade disponível.
- Um leitor pode ter **no máximo 3 empréstimos ativos**.
- Ao devolver um livro, sua disponibilidade é atualizada.
- Apenas livros disponíveis podem ser emprestados.


## <a name="regras-de-negócio"></a>📂 Estrutura do Projeto
├── src\
│   ├── configs           # Configurações gerais
│   ├── controllers       # Camada de controle (endpoints)\
│   ├── dtos              # DTOs de entrada e saída\
│   ├── models            # Entidades JPA\
│   ├── repositories      # Interfaces de acesso a dados\
│   ├── services          # Regras de negócio\
│   ├── security          # Filtros de segurança e JWT\
 |   └── exceptions        # Tratamento global de erros\
├── resources\ 
│   └── application.properties   # Configurações gerais\
├── Dockerfile\
├── docker-compose.yml\
└── .env


## <a name="estrutura-do-projeto"></a><a name="testes"></a>📌 Possíveis Melhorias Futuras
- Integração com e-mail para notificação de devolução
- Suporte a múltiplas bibliotecas
- Sistema de reservas
- Frontend com React ou Angular


[ref1]: Aspose.Words.cc2608f4-7dd2-4eb3-a2e8-d630add526c3.001.png
[ref2]: Aspose.Words.cc2608f4-7dd2-4eb3-a2e8-d630add526c3.002.png
