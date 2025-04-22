# 🔐 Sistema de Controle de Autorizações - IFSP

Sistema web desenvolvido com Java e Spring Boot para controle de autorizações de uso de salas e laboratórios no IFSP. Permite a concessão de permissões por servidores, solicitação de uso por alunos e gerenciamento de chaves pela CAE.

---

## 📚 Sumário

- [Tecnologias](#tecnologias)
- [Objetivo](#objetivo)
- [Perfis de Acesso](#perfis-de-acesso)
- [Entidades](#entidades)
- [Endpoints da API](#endpoints-da-api)
- [Swagger](#swagger)
- [Execução com Docker](#execução-com-docker)
- [Consultas por Período](#consultas-por-período)
- [Segurança com Spring Security](#segurança-com-spring-security)

---

## 🚀 Tecnologias

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- Lombok
- MySQL
- Docker + Docker Compose
- Spring Security (em desenvolvimento)
- Swagger (SpringDoc OpenAPI)

---

## 🎯 Objetivo

Facilitar e controlar o uso de salas e laboratórios do campus pelos alunos, com base em autorizações concedidas por servidores, e gerenciar o empréstimo de chaves através da CAE (Coordenadoria de Apoio ao Ensino).

---

## 👥 Perfis de Acesso

| Perfil     | Permissões principais                                                                 |
|------------|----------------------------------------------------------------------------------------|
| **CAE**    | Cadastrar/editar ambientes, servidores, registrar entrega e devolução de chaves       |
| **Servidor** | Cadastrar autorizações para alunos                                                  |
| **Aluno**    | Solicitar uso de ambientes, visualizar solicitações                                 |

---

## 🧱 Entidades

- `Aluno`: nome, prontuário, curso
- `Servidor`: nome, prontuário, e-mail
- `Ambiente`: nome, localização, disponível
- `Autorizacao`: aluno, servidor, ambiente, atividade, dias e horários, período de uso
- `SolicitacaoChave`: aluno, ambiente, status, datas de solicitação, entrega e devolução

---

## 🌐 Endpoints da API

### 🔸 Aluno
- `GET /alunos`
- `POST /alunos`
- `PUT /alunos/{id}`
- `DELETE /alunos/{id}`

### 🔸 Servidor
- `GET /servidores`
- `POST /servidores`

### 🔸 Ambiente
- `GET /ambientes`
- `GET /ambientes/disponiveis`
- `POST /ambientes`

### 🔸 Autorização
- `GET /autorizacoes`
- `POST /autorizacoes`
- `GET /autorizacoes/aluno/{id}`
- `GET /autorizacoes/ambiente/{id}`
- `DELETE /autorizacoes/{id}`

### 🔸 Solicitação de Chave
- `GET /solicitacoes`
- `POST /solicitacoes`
- `PUT /solicitacoes/{id}/entregar`
- `PUT /solicitacoes/{id}/devolver`

---

## 📑 Swagger

Documentação da API disponível em:


> Inclui teste de endpoints, schemas de requisição e resposta.

---

## 🐳 Execução com Docker

### 🔸 Subir o sistema completo:
```bash
docker-compose up --build
