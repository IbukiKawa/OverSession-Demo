# OverSession-Demo

OverSession のデモアプリケーションです。  
Spring Boot (Java) バックエンド + Next.js フロントエンド + PostgreSQL で構成されています。

---

## リポジトリ構成

```text
.
├── backend/                    # Spring Boot バックエンド
│   └── src/main/
│       ├── java/               # Java ソースコード
│       └── resources/
│           ├── schema.sql      # DDL (テーブル定義)
│           ├── data.sql        # 初期ダミーデータ
│           └── META-INF/       # Doma SQL ファイル
├── frontend/
│   └── src/                    # Next.js アプリ (package.json もここ)
├── docker/                     # Dockerfile 群
├── docker-compose.yml          # Docker Compose 設定
├── pom.xml                     # Maven ビルド設定
└── docs/                       # 設計書・ドキュメント
```

---

## 動作確認済み環境

| ツール | バージョン |
|---|---|
| Java | 25.0.2 |
| Maven | 3.x |
| Node.js | 18 以上 |
| Docker Desktop | 最新推奨 |
| PostgreSQL | 16 (Docker) |

---

## 起動方法

この README は **Docker で起動する前提** で記載しています。

> ⚠️ `docker compose up` で backend を起動している間は、`mvn spring-boot:run` を同時に実行しないでください。ポート 8080 が競合します。

---

### 1. フロントエンド環境変数を設定

`frontend/src/.env.local` を作成し、以下を設定します。

```env
NEXT_PUBLIC_DATA_SOURCE=api
NEXT_PUBLIC_API_BASE_URL=http://localhost:8080
NEXT_PUBLIC_CURRENT_USER_ID=user01
```

> `.env.local` がない、または `NEXT_PUBLIC_DATA_SOURCE=mock` になっている場合はモックデータが使われます。

---

### 2. 全サービスを Docker で起動

```bash
docker compose up -d --build
```

- バックエンド: http://localhost:8080
- フロントエンド: http://localhost:3000
- DB: localhost:5432

初回起動時は backend イメージのビルドに数分かかる場合があります。

---

### 3. 起動状態を確認

```bash
docker compose ps
```

- `backend` が `Up`
- `frontend` が `Up`
- `db` が `healthy`

になっていれば起動完了です。

---

### 4. ブラウザでアクセス

http://localhost:3000 を開くと、`user01`（田中 太郎）としてログインした状態でチャット一覧が表示されます。

---

### 5. 停止方法

```bash
docker compose down
```

DB データも含めて完全に消したい場合は以下を実行します。

```bash
docker compose down -v
```

---

## 環境変数

### フロントエンド (`frontend/src/.env.local`)

```env
# データソース: "mock" (デフォルト) or "api" (バックエンドAPI接続)
NEXT_PUBLIC_DATA_SOURCE=api

# バックエンドAPIのベースURL
NEXT_PUBLIC_API_BASE_URL=http://localhost:8080

# ログインユーザID
NEXT_PUBLIC_CURRENT_USER_ID=user01
```

> `.env.local` が存在しない場合、または `NEXT_PUBLIC_DATA_SOURCE=mock` の場合はモックデータが使用されます。

### バックエンド (`backend/src/main/resources/application.properties`)

| プロパティ | デフォルト値 | 説明 |
|---|---|---|
| `spring.datasource.url` | `jdbc:postgresql://localhost:5432/mydb` | PostgreSQL 接続先 |
| `spring.datasource.username` | `postgres` | DB ユーザー |
| `spring.datasource.password` | `password` | DB パスワード |
| `doma.dialect` | `POSTGRES` | Doma 方言 |

---

## 主要 API エンドポイント

| メソッド | パス | 説明 |
|---|---|---|
| GET | `/api/users` | ユーザー一覧 |
| GET | `/api/users?userId={id}` | ユーザー 1 件取得 |
| POST | `/api/users` | ユーザー登録 |
| PUT | `/api/users/{userId}` | ユーザー更新 |
| DELETE | `/api/users/{userId}` | ユーザー削除（論理削除） |
| GET | `/api/chats?userId={id}` | チャット一覧 |
| GET | `/api/chats/{chatId}` | チャット 1 件取得 |
| POST | `/api/chats` | チャット作成 |
| GET | `/api/chats/{chatId}/messages` | メッセージ一覧 |
| POST | `/api/chats/{chatId}/messages` | メッセージ送信 |
| PUT | `/api/chats/{chatId}/messages/{messageId}/read` | 既読マーク |
| POST | `/api/chats/{chatId}/messages/{messageId}/reactions` | リアクション追加 |

---

## トラブルシューティング

### CORS エラーが出る場合

ブラウザで以下のようなエラーが出る場合があります。

```text
Access to fetch at 'http://localhost:8080/...' from origin 'http://localhost:3000' has been blocked by CORS policy
```

主な原因は **backend コンテナが古いイメージのまま起動していて、最新の CORS 設定が反映されていない** ことです。

#### 対処方法

1. backend を再ビルドして再起動

```bash
docker compose up -d --build backend
```

2. 全サービスごと更新したい場合

```bash
docker compose up -d --build
```

3. 反映確認

```bash
curl -i -X OPTIONS 'http://localhost:8080/api/chats?userId=user01' \
    -H 'Origin: http://localhost:3000' \
    -H 'Access-Control-Request-Method: GET'
```

以下のように `Access-Control-Allow-Origin: http://localhost:3000` が返れば正常です。

```text
Access-Control-Allow-Origin: http://localhost:3000
```

#### あわせて確認すること

- `frontend/src/.env.local` の `NEXT_PUBLIC_DATA_SOURCE=api` になっているか
- `NEXT_PUBLIC_API_BASE_URL=http://localhost:8080` になっているか
- `docker compose ps` で `backend` が `Up` になっているか

---

## Spring Boot の導入について

このプロジェクトは **Spring Boot** を使っており、新たに Spring Boot を導入する場合の手順を以下に記載します。  
（すでに `pom.xml` に設定済みのため、clone 後はこの手順は不要です。）

---

### 1. Java・Maven のインストール

#### macOS

Homebrew でインストールできます。

```bash
brew install openjdk@21
brew install maven
```

#### Windows

**Winget（推奨）**を使う場合：

```powershell
winget install Microsoft.OpenJDK.21
winget install Apache.Maven
```

**手動でインストールする場合：**

1. [Eclipse Temurin（OpenJDK）](https://adoptium.net/) から Java 21 のインストーラーをダウンロードして実行
2. [Maven 公式](https://maven.apache.org/download.cgi) から `apache-maven-x.x.x-bin.zip` をダウンロード
3. 任意のフォルダ（例: `C:\tools\maven`）に展開
4. 環境変数 `PATH` に `C:\tools\maven\bin` を追加

#### インストール確認（共通）

```bash
java -version   # 21 以上であればOK
mvn -version
```

---

### 2. Spring Boot プロジェクトの作成

[Spring Initializr](https://start.spring.io/) でプロジェクトのひな形を生成します。

| 項目 | 設定値 |
|---|---|
| Project | Maven |
| Language | Java |
| Spring Boot | 2.7.x または 3.x |
| Packaging | Jar |
| Java | 21 |

**Dependencies（依存関係）の追加：**

| 依存関係 | 用途 |
|---|---|
| Spring Web | REST API の作成 |
| PostgreSQL Driver | PostgreSQL 接続 |
| JDBC API | DB アクセス基盤 |

生成した zip を展開してプロジェクトルートに配置します。

---

### 3. このプロジェクト固有の追加依存関係

通常の Spring Boot に加え、以下を `pom.xml` に追加しています。

```xml
<!-- Doma（SQLファイル分離管理） -->
<dependency>
    <groupId>org.seasar.doma</groupId>
    <artifactId>doma-core</artifactId>
    <version>2.55.0</version>
</dependency>
<dependency>
    <groupId>org.seasar.doma.boot</groupId>
    <artifactId>doma-spring-boot-starter</artifactId>
    <version>1.7.0</version>
</dependency>
```

`maven-compiler-plugin` にアノテーションプロセッサも設定が必要です（`pom.xml` 参照）。

---

### 4. application.properties の設定

`backend/src/main/resources/application.properties` に DB 接続情報などを記述します。

```properties
# サーバーポート
server.port=8080

# PostgreSQL 接続先
spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
spring.datasource.username=postgres
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver

# DDL・初期データの自動実行
spring.sql.init.mode=always
spring.sql.init.schema-locations=classpath:schema.sql
spring.sql.init.data-locations=classpath:data.sql

# Doma 方言
doma.dialect=POSTGRES
```

> 設定値はすべてデフォルトのままで動作します。変更が必要な場合のみ書き換えてください。

---

### 5. 起動確認

```bash
mvn spring-boot:run
```

以下の URL で動作確認できます。

```bash
curl http://localhost:8080/api/users
```

`[]` または JSON 配列が返れば正常に起動しています。
