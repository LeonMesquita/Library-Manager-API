📚 LibraryManagerAPI
Uma API RESTful para gerenciamento de biblioteca com controle de empréstimos e histórico, desenvolvida com Spring Boot, Spring Security (JWT + OAuth2 Google) e JPA/Hibernate.

🚀 Funcionalidades Principais
• ✅ Autenticação e autorização com JWT e login via Google OAuth2
• 📖 Gestão de livros com controle de disponibilidade
• 👤 Cadastro e autenticação de leitores e administradores
• 🔐 Controle de acesso baseado em roles (ADMIN, LEITOR)
• 🔄 Histórico completo de empréstimos com controle de devoluções
• 📊 Paginação, filtros e busca textual

🛠️ Tecnologias Utilizadas
• Java 17
• Spring Boot 3
• Spring Security + JWT + OAuth2
• JPA + Hibernate
• PostgreSQL
• Docker + Docker Compose

📦 Como Rodar o Projeto
1. Clone o repositório
   git clone https://github.com/LeonMesquita/Library-Manager-API.git
   cd LibraryManagerAPI
2. Crie um arquivo .env na raiz com as variáveis de ambiente:
# PostgreSQL
DB_HOST=library_manager_db
DB_PORT=5432
DB_NAME=postgres
DB_USERNAME=postgres
DB_PASSWORD=112233

# JWT
JWT_SECRET=uma_chave_segura
JWT_EXPIRATION=60
JWT_REFRESH_EXPIRATION=1440

# Google OAuth2
GOOGLE_CLIENT_ID=seu_client_id
GOOGLE_CLIENT_SECRET=sua_client_secret
💡 Você pode usar um PostgreSQL local ou o que será iniciado via Docker.

3. Rodando com Docker
   docker-compose up --build
   A aplicação estará disponível em: http://localhost:8080

🔐 Usuário ADMIN padrão
Ao rodar a aplicação pela primeira vez, um usuário administrador é criado automaticamente:
{
"email": "admin@gmail.com",
"password": "new@admin"
}

📬 Endpoints
🧑 Usuários
Método
Endpoint
Descrição
POST
/api/users
Cadastra novo usuário
PUT
/api/users/{id}
Edita usuário (ADMIN ou o próprio dono)
GET
/api/users/{id}
Consulta usuário por ID
DELETE
/api/users/{id}
Deleta usuário
Necessário enviar Bearer Token nas rotas protegidas.

🔑 Autenticação
Método
Endpoint
Descrição
POST
/api/auth/login
Login com email e senha
POST
/api/auth/refresh-token
Gera novo token via refreshToken
GET
/api/oauth2/authorization/google
Login via Google (tokens retornam nos headers)

📖 Livros
Método
Endpoint
Descrição
POST
/api/books
Cria novo livro (ADMIN)
GET
/api/books
Lista livros com paginação
GET
/api/books/{id}
Consulta livro por ID
PUT
/api/books/{id}
Edita livro (ADMIN)
DELETE
/api/books/{id}
Deleta livro (ADMIN)
Filtros por título, autor, gênero e disponibilidade podem ser adicionados via query params.

📦 Empréstimos
Método
Endpoint
Descrição
POST
/api/rentals
Solicita empréstimo
GET
/api/rentals/mine
Consulta meus empréstimos
GET
/api/rentals
Lista todos os empréstimos (ADMIN)
PUT
/api/rentals/{id}/approve
Aprova empréstimo (ADMIN)
PUT
/api/rentals/{id}/reject
Rejeita empréstimo (ADMIN)
PUT
/api/rentals/{id}/return
Marca devolução

✅ Regras de Negócio
• Usuário com role ADMIN tem permissões totais.
• Usuário com role LEITOR pode gerenciar apenas sua conta e seus empréstimos.
• Cada livro possui controle de quantidade disponível.
• Um leitor pode ter no máximo 3 empréstimos ativos.
• Ao devolver um livro, sua disponibilidade é atualizada.
• Apenas livros disponíveis podem ser emprestados.

📂 Estrutura do Projeto
├── src
│   ├── config           # Configurações de segurança e JWT
│   ├── controller       # Camada de controle (endpoints)
│   ├── dto              # DTOs de entrada e saída
│   ├── entity           # Entidades JPA
│   ├── repository       # Interfaces de acesso a dados
│   ├── service          # Regras de negócio
│   └── exception        # Tratamento global de erros
├── resources
│   └── application.yml  # Configurações gerais
├── Dockerfile
├── docker-compose.yml
└── .env


📌 Possíveis Melhorias Futuras
• Integração com e-mail para notificação de devolução
• Suporte a múltiplas bibliotecas
• Sistema de reservas
• Frontend com React ou Angular
