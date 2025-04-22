
# 🎓 Controle de Autorizações - IFSP

Sistema Web para controle de uso de laboratórios e salas do IFSP, com autenticação, permissões por perfil e funcionalidades automatizadas.

---

## 🚀 Funcionalidades

- ✅ Cadastro de Alunos e Servidores com conta de usuário
- ✅ Controle de autorizações de uso (datas, ambiente, tipo de atividade)
- ✅ Solicitação, entrega e devolução de chaves (CAE)
- ✅ Login com Spring Security e autenticação por perfil (`ALUNO`, `SERVIDOR`, `CAE`)
- ✅ Upload de documentos (PDFs, TCC, autorização)
- ✅ Logs de auditoria de ações do sistema
- ✅ Dashboard resumido (autorizados, pendentes, ambientes disponíveis)
- ✅ Agendamento automático de verificação de autorizações vencidas
- ✅ Envio de e-mails para notificações
- ✅ Documentação Swagger disponível em `/swagger-ui.html`

---

## 👤 Perfis de Acesso

| Perfil     | Permissões principais |
|------------|------------------------|
| `ALUNO`    | Solicita chave, envia documentos |
| `SERVIDOR` | Cria autorizações para alunos |
| `CAE`      | Entrega/devolve chaves, gerencia dados |

---

## 🔐 Autenticação

- Login via Basic Auth (em `/perfil`, `/solicitacoes`, etc.)
- Proteção por `@PreAuthorize`
- Criação de `Usuario` com senha criptografada (BCrypt)

---

## 📦 Endpoints principais

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/usuarios` | Cadastra login |
| `POST` | `/alunos` | Cria aluno com conta |
| `POST` | `/servidores` | Cria servidor com conta |
| `POST` | `/autorizacoes` | Cadastra autorização |
| `POST` | `/solicitacoes` | Aluno solicita uso |
| `PUT`  | `/solicitacoes/{id}/entregar` | CAE entrega chave |
| `PUT`  | `/solicitacoes/{id}/devolver` | Aluno devolve chave |
| `GET`  | `/dashboard` | Resumo geral |
| `GET`  | `/perfil` | Dados do usuário logado |

---

## 🧪 Testes Automatizados

- ✅ `AlunoServiceTest` (JUnit + Mockito)
- ✅ `AlunoControllerTest` (MockMvc)
- ✅ Estrutura pronta para cobertura com Jacoco e GitHub Actions
- 🔜 Testes para `PerfilController` e `ServidorService`

---

## ⚙️ Requisitos

- Java 17
- Maven 3.8+
- MySQL 8+
- IDE com suporte a Spring Boot

---

## 🛠️ Como executar o projeto localmente

```bash
# Clone o repositório
git clone https://github.com/bielrodrigues2406/controleautorizacoes.git
cd controleautorizacoes

# Crie o banco de dados MySQL
CREATE DATABASE controleautorizacoes;

# Configure as credenciais no application.properties
# src/main/resources/application.properties

# Execute a aplicação
./mvnw spring-boot:run
```

Acesse o sistema em:  
📚 http://localhost:8080/swagger-ui.html

---

## 🧪 Testes com VS Code ou Postman

- ✅ Arquivo `testes.http` disponível para testar endpoints com VS Code
- ✅ Coleção Postman disponível em `/postman_collection.json`

---

## 📤 Deploy e CI/CD

- 🔧 Pronto para deploy em:
  - Render, Railway, Heroku ou VPS com Docker
- ⚙️ Configuração para GitHub Actions (build + testes)
- ✅ `Dockerfile` e `docker-compose.yml` incluídos (opcional)

---

## 📁 Organização do Projeto

```
src/
├── main/
│   ├── java/br/edu/ifsp/
│   │   ├── controller/
│   │   ├── domain/
│   │   ├── dto/
│   │   ├── enums/
│   │   ├── mapper/
│   │   ├── repository/
│   │   ├── service/
│   │   └── service/agendamento/
│   └── resources/
│       ├── application.properties
│       └── data.sql
└── test/
    └── java/br/edu/ifsp/
        ├── service/
        └── controller/
```

---

## 👨‍💻 Autor

**Gabriel Rodrigues**  
Estudante de Ciência da Computação — IFSP Presidente Epitácio  
Projeto desenvolvido como prática de Engenharia de Software, Segurança, Arquitetura e Testes.
