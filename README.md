
# 🎓 Sistema de Controle de Autorizações - IFSP

Projeto acadêmico para gerenciar autorizações de uso de ambientes, controle de chaves e autenticação de perfis no IFSP. Foco em segurança, extensibilidade e base sólida para aplicações escaláveis.

---

## ✅ Funcionalidades Principais

### 👤 Perfis de Usuário (RBAC)
- **ALUNO**: Solicita uso, devolve chave, envia documentos
- **SERVIDOR**: Autoriza uso de ambientes
- **CAE**: Gera chave, confirma devolução, gerencia tudo

### 🔐 Autenticação e Segurança
- Autenticação com Spring Security (`UserDetailsService`)
- Criptografia de senhas com BCrypt
- Controle de tentativas de login com bloqueio automático
- Recuperação de senha via e-mail com token único
- Proteção por roles com `@PreAuthorize`

### 🗃️ Gerenciamento
- CRUD completo de Alunos, Servidores, Ambientes, Autorizações
- Solicitação, entrega e devolução de chaves (com status)
- Log de auditoria detalhado (quem fez, quando e o quê)

### 📄 Relatórios e Consultas
- Exportação em **PDF** e **Excel** das autorizações
- Filtros por Aluno, Ambiente e Status
- Paginação e ordenação com `Pageable`

### 📊 Dashboard Analítico
- Total de autorizações
- Chaves solicitadas, entregues, pendentes
- Ambientes disponíveis

---

## 🔧 Endpoints de Destaque

| Método | URI                         | Descrição                                      |
|--------|-----------------------------|------------------------------------------------|
| POST   | `/auth/recuperar`           | Gera token de recuperação de senha             |
| POST   | `/auth/redefinir`           | Redefine senha com token                       |
| GET    | `/autorizacoes`             | Lista com filtros, paginação                   |
| GET    | `/autorizacoes/exportar`    | Exporta autorizações para PDF                  |
| GET    | `/relatorios/exportar/excel`| Exporta autorizações para Excel                |
| GET    | `/dashboard`                | Dados analíticos em tempo real                 |

---

## 🧪 Testes Automatizados

- Integração completa com `MockMvc` para Auth e Recuperação
- Testes de serviços com JUnit + Mockito
- Estrutura pronta para cobertura com Jacoco
- Planejamento para testes de carga com JMeter e autenticação mockada

---

## 📂 Estrutura do Projeto

```
src/
├── controller/         # Camada REST
├── domain/             # Entidades JPA
├── dto/                # Data Transfer Objects
├── enums/              # Enumerações (Status, Role)
├── mapper/             # MapStruct (Entity <-> DTO)
├── repository/         # Interfaces JPA
├── service/            # Regras de negócio
├── config/             # Segurança, beans, handlers
└── resources/
    ├── application.properties
    └── data.sql
```

---

## 🐳 Infraestrutura (planejado)

- `Dockerfile` e `docker-compose.yml` com MySQL + App
- Deploy possível em Render, Railway, VPS ou AWS EC2
- CI/CD com GitHub Actions (build, teste, cobertura)

---

## 🧠 Futuro / Extensões

- Integração com JWT para APIs externas
- Serviço de email real com SendGrid/Mailgun
- Balanceamento com NGINX + Spring Gateway
- Cache de consultas com Redis
- Exportação para CSV / gráficos dinâmicos

---

## 👨‍💻 Autores

**Gabriel Rodrigues**  
**Jorge Luis**  
Alunos de Ciência da Computação - IFSP Presidente Epitácio  
Projeto voltado à prática de Engenharia de Software, Arquitetura e Segurança.
