# 🎧 SpotiPobre

Sistema web de catálogo musical e playlists, desenvolvido para a disciplina de Implementação e Operação de Banco de Dados do curso de Análise e Desenvolvimento de Sistemas (IFRS - Campus Rio Grande).

O Javalin processa as requisições, conversa com o PostgreSQL via JDBC puro (sem ORM) e renderiza as telas com templates Mustache.

## 🛠️ Stack

- **Java 21**
- **Javalin 6** — framework web
- **Mustache** — templates das telas
- **PostgreSQL 16** — banco de dados
- **JDBC** — acesso ao banco, sem ORM
- **HTML5 / CSS3** — front-end, com um design system próprio (`app.css`) em tema escuro

## ✨ Funcionalidades

| Funcionalidade | Rota | Descrição |
|---|---|---|
| Criar playlist | `GET` / `POST` `/playlists/nova` | Cadastra uma playlist (nome, pública/privada) e vincula um usuário como dono |
| Listar / alterar / excluir playlist | `/playlists/tela_listagem`, `/playlists/tela_alterar/{id}`, `/playlists/alterar`, `/playlists/excluir` | CRUD completo de playlists |
| Catálogo musical | `GET` `/dashboard` | Lista álbuns e suas músicas, via `JOIN` entre `album`, `musica` e `album_musica` |
| Registrar reprodução | `POST` `/reproduzir` | Grava no histórico qual usuário ouviu qual música, com data e hora |
| CRUD de usuários | `/usuarios/*` | Cadastro, listagem, alteração e exclusão de usuários |

O sistema não possui autenticação/login: quem está "usando" o sistema é escolhido manualmente em cada ação (dono da playlist, ouvinte de uma música).

## 📁 Estrutura do projeto

```
spoti-pobre/
├── src/main/java/apresentacao/
│   └── Main.java                  # ponto de entrada, registra rotas e static files
├── src/main/java/controller/      # rotas Javalin (Dashboard, Playlist, Usuario)
├── src/main/java/negocio/         # classes de domínio (Album, Musica, Playlist, Usuario...)
├── src/main/java/persistencia/    # DAOs (JDBC puro) e conexão com o banco
├── src/main/resources/
│   ├── public/app.css             # design system único, usado por todas as telas
│   └── templates/                 # telas Mustache (home, dashboard, playlists/, usuarios/)
└── pom.xml
```

## ▶️ Como rodar

### Pré-requisitos

- Java 21
- Maven
- PostgreSQL rodando localmente

### 1. Criar o banco

```bash
psql -U postgres -f schema_e_dados.sql
```

O script cria o banco `spoti_pobre`, as tabelas e alguns dados de exemplo (álbuns, artistas, usuários e playlists).

### 2. Configurar a conexão

Em `src/main/java/persistencia/ConexaoPostgreSQL.java`, confira host, porta, nome do banco e credenciais. Por padrão:

```java
host = "localhost"
port = "5432"
dbname = "spoti_pobre"
username = "postgres"
password = "postgres"
```

### 3. Rodar a aplicação

```bash
mvn compile exec:java
```

Ou rode a classe `Main.java` diretamente pela IDE.

A aplicação sobe em **http://localhost:7070**.

## 👤 Autor

**Lucas Mello**
Estudante de Análise e Desenvolvimento de Sistemas — IFRS

- GitHub: [github.com/lucasmmello03](https://github.com/lucasmmello03)
- LinkedIn: [linkedin.com/in/mellolucas03](https://linkedin.com/in/mellolucas03)
