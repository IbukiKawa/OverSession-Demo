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

### ① PostgreSQL を Docker で起動

```bash
docker compose up -d db
```

> `docker compose ps` で `db` が `healthy` になるまで待ちます。

---

### ② バックエンド (Spring Boot) を起動

```bash
mvn spring-boot:run
```

- 起動後: http://localhost:8080
- 初回起動時に `schema.sql` → `data.sql` の順で自動実行され、テーブル作成とダミーデータ投入が行われます。

---

### ③ フロントエンド (Next.js) を起動

```bash
cd frontend/src
npm install      # 初回のみ
npm run dev
```

- 起動後: http://localhost:3000

---

### ④ ブラウザでアクセス

http://localhost:3000 を開くと、`user01`（田中 太郎）としてログインした状態でチャット一覧が表示されます。

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

> `.env.local` が存在しない場合はモックデータが使用されます。

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

## Docker で全サービスをまとめて起動する場合

```bash
docker compose up -d
```

> バックエンドコンテナのビルドには数分かかる場合があります。  
> ローカル開発時は DB のみ Docker で起動し、バックエンド・フロントエンドはローカルで起動する方法が推奨です。
