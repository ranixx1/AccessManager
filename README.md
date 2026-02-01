# 📘 Sistema de Controle de Acesso

Projeto em **Java** para controle de usuários, permississões e registro de acessos a setores, utilizando **CSV como persistência** e um **script em JavaScript** para geração de dados mock.

O sistema simula um controle de **entrada e saída de usuários** em setores, respeitando **status do usuário**, **permissões**, **janelas de horário** e mantendo um **histórico completo de acessos**.

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
│   ├── SetorService.java
│   ├── PermissaoService.java
│   └── AcessoService.java
│
├── mapper/                 # Leitura de CSV → Objetos
│   ├── UsuarioCSVMapper.java
│   ├── SetorCSVMapper.java
│   ├── PermissaoCSVMapper.java
│   └── AcessoCSVMapper.java
│
├── repository/             # Escrita em CSV
│   ├── UsuarioRepository.java
│   ├── SetorRepository.java
│   ├── PermissaoRepository.java
│   └── AcessoRepository.java
│
├── enums/
│   └── Role.java
│
└── Main.java               # Interface CLI
```

---

## ⚙️ Pré-requisitos

- Java **17+**
- Node.js (**apenas para gerar os CSVs de mock**)

---

## 🧪 Gerando dados de teste (mock)

```bash
node GerarCSV.js
```

Serão gerados automaticamente:

- Usuários  
- Setores  
- Permissões  
- Histórico de acessos  

Todos os arquivos serão salvos na pasta `csv/`.

---

## 🧠 Conceitos

### 🔹 Usuário
- Pode estar **ativo ou inativo**
- Possui um **Role**:
  - `ADMIN`
  - `MANAGER`
  - `EMPLOYEE`
  - `VISITOR`
- Apenas usuários **ativos** podem ter o acesso validado

### 🔹 Setor
- Representa uma área física ou lógica
- Pode possuir restrições de acesso
- É utilizado como base para validação de permissões

### 🔹 Permissão
- Define se um usuário pode acessar um setor
- Possui uma **janela de horário**:
  - `horario_inicio`
  - `horario_fim`

### 🔹 Acesso
- Representa uma tentativa de entrada
- Sempre registrada (permitida ou negada)
- Evita duplicidade de acessos abertos

---

## 🧩 Serviços

### 📌 UsuarioService
```java
criarUsuario(String nome, Role role)
buscarPorId(Long id)
listarUsuarios()
desativarUsuario(Long id)
reativarUsuario(Long id)
```

### 📌 SetorService
```java
criarSetor(String nome)
buscarPorId(Long id)
listarSetores()
```

### 📌 PermissaoService
```java
criarPermissao(Long usuarioId, Long setorId, LocalTime inicio, LocalTime fim)
listarPermissoes()
buscarPermissao(Long usuarioId, Long setorId)
```

### 📌 AcessoService
```java
boolean validarEntrada(Long usuarioId, Long setorId)
void registrarSaida(Long usuarioId, Long setorId)
List<Acesso> getHistorico()
List<Acesso> getHistoricoPorUsuario(Long usuarioId)
List<Acesso> getHistoricoPorSetor(Long setorId)
```

---

##  Fluxo de Validação de Acesso

1. Usuário solicita entrada em um setor  
2. Sistema valida usuário, setor, permissão e horário  
3. Entrada registrada ou negada  
4. Saída atualiza o registro correto