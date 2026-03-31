# 🚀 StealthForge API

Uma API REST construída do zero utilizando apenas **Java 21 puro**, sem frameworks, com foco em aprendizado profundo de como APIs funcionam internamente.

---

## 🎯 Objetivo

Este projeto foi criado com o propósito de:

* Entender como funciona uma API HTTP por baixo dos panos
* Implementar manualmente conceitos que frameworks abstraem
* Aplicar boas práticas de arquitetura (Controller, Service, Repository)
* Trabalhar com JSON sem bibliotecas externas
* Implementar CORS manualmente
* Construir um CRUD completo com evolução incremental

---

## 🧱 Tecnologias utilizadas

* Java 21 (puro)
* `com.sun.net.httpserver.HttpServer`
* Programação Orientada a Objetos (POO)

---

## 📦 Arquitetura

```
com.stealthforge/
 ├── controller/     # Entrada HTTP (rotas)
 ├── service/        # Regras de negócio
 ├── repository/     # Persistência em memória
 ├── model/          # Entidades e DTOs
 └── util/           # Utilitários (JSON, CORS, etc.)
```

---

## 🔥 Funcionalidades atuais

* [x] Servidor HTTP nativo
* [x] Roteamento manual
* [x] CRUD completo de produtos
* [x] JSON (serialização/deserialização manual)
* [x] CORS (preflight + headers)
* [x] Soft delete
* [x] PUT (update completo)
* [x] PATCH (update parcial)
* [x] Tratamento básico de erros

---

## 📌 Endpoints

### 🔹 Listar produtos

```
GET /products
```

### 🔹 Buscar por ID

```
GET /products/{id}
```

### 🔹 Criar produto

```
POST /products
```

Body:

```json
{
  "name": "Produto",
  "price": 100.0
}
```

---

### 🔹 Atualizar completamente

```
PUT /products/{id}
```

---

### 🔹 Atualizar parcialmente

```
PATCH /products/{id}
```

---

### 🔹 Deletar (soft delete)

```
DELETE /products/{id}
```

---

## ⚠️ Observações importantes

* O JSON é manipulado manualmente (sem Jackson/Gson)
* Os dados são armazenados em memória (sem banco)
* O projeto é educacional, focado em aprendizado

---

## ▶️ Como executar

Compile:

```
javac -d out src/main/java/com/stealthforge/Main.java
```

Execute:

```
java -cp out com.stealthforge.Main
```

---

## 🌐 Testando

A API sobe por padrão em:

```
http://localhost:8080
```

---

## 🚧 Próximos passos

* Validação de dados
* Padronização de resposta
* Melhor parser JSON
* Logging estruturado
* Middleware (interceptadores)
* Persistência real (arquivo ou banco)

---

## 🧠 Aprendizados

Este projeto cobre conceitos fundamentais como:

* HTTP na prática
* CORS
* Idempotência
* Arquitetura em camadas
* Manipulação de dados
* Design de API REST

---

## 👨‍💻 Autor

Projeto desenvolvido para estudo e aprofundamento em backend com Java por Eduardo B. Rauta.

---

## ⭐ Motivação

"Entender primeiro, abstrair depois."
