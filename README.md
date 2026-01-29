# 📘 Sistema de Controle de Acesso

Projeto em **Java** para controle de usuários, permissões e registro de acessos a setores, utilizando **CSV como persistência** e um **script em JavaScript** para mock de dados.

---

## 📂 Estrutura do Projeto

```
project/
│
├── csv/                    # Dados persistidos (gerados pelo script JS)
│   ├── usuarios.csv
│   ├── setores.csv
│   ├── permissoes.csv
│   └── acessos.csv
│
├── model/                  # Entidades de domínio
│   ├── Usuario.java
│   ├── Setor.java
│   ├── Permissao.java
│   └── Acesso.java
│
├── service/                # Regras de negócio
│   ├── UsuarioService.java
│   └── AcessoService.java
│
├── mapper/                 # Leitura de CSV → objetos
│   ├── UsuarioCSVMapper.java
│   ├── PermissaoCSVMapper.java
│   ├── SetorCSVMapper.java
│   └── AcessoCSVMapper.java
│
├── repository/             # Escrita em CSV
│   └── UsuarioRepository.java
│
├── enums/
│   └── Role.java
│
└── Main.java                # Interface CLI
```

---

## ⚙️ Pré-requisitos

* Java 17+
* Node.js (apenas para gerar os CSVs)

---

## 🧪 Gerando dados de teste (mock)

Execute o script JavaScript:

```bash
node GerarCSV.js
```

Isso irá gerar:

* Usuários
* Setores
* Permissões
* Histórico de acessos

Todos em arquivos CSV dentro da pasta `csv/`.

---

## 🧠 Conceitos Importantes

### 🔹 Usuário

* Pode estar **ativo ou inativo**
* Possui um **Role** (`ADMIN`, `MANAGER`, `EMPLOYEE`, `VISITOR`)
* Apenas usuários ativos podem ter acesso validado

### 🔹 Permissão

* Define **se um usuário pode entrar em um setor**
* Possui **janela de horário** (`horario_inicio` → `horario_fim`)

### 🔹 Acesso

* Representa uma tentativa de entrada
* Sempre registrada (permitida ou não)
* Contém:

  * usuário
  * setor
  * horário de entrada
  * horário de saída (se permitido)
  * status (permitido / negado)

---

## 🧩 Serviços Disponíveis

### UsuarioService

```java
criarUsuario(String nome, Role role)
buscarPorId(Long id)
desativarUsuario(Long id)
reativarUsuario(Long id)
```

### AcessoService

```java
boolean validar(Long usuarioId, Long setorId)
List<Acesso> getHistorico()
```
