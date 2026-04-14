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
github.ebrauta/
 ├── app/               # Aplicação
 | ├── config/              # Configurações da Aplicação
 | ├── middleware/          # Middlewares (Cors, Exception e Logging)
 | └── util/                # Utilitários (Banner, Json e Logger)
 └── core/              # Núcleo
   ├── adapter/             # Adaptador do Controller 
   ├── http/                # Requests e Responses
   ├── middleware/          # Núcleo de middlewares
   └── router               # Controlador de Rotas
```

---

## 🔥 Funcionalidades atuais

* [x] Servidor HTTP nativo
* [x] Roteamento manual
* [x] JSON (serialização/deserialização manual)
* [x] CORS (preflight + headers)

---

## 📌 Endpoints

### 🔹 Teste

```
GET /test
```
---

## ⚠️ Observações importantes

* O JSON é manipulado manualmente (sem Jackson/Gson)
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

## 🧠 Aprendizados

Este projeto cobre conceitos fundamentais como:

* HTTP na prática
* CORS
* Idempotência
* Arquitetura em camadas

---

## 👨‍💻 Autor

Projeto desenvolvido para estudo e aprofundamento em backend com Java por Eduardo B. Rauta.

---

## ⭐ Motivação

"Entender primeiro, abstrair depois."
