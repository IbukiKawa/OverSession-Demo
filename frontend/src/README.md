# OverSession Frontend

ユーザマッチングシステムのフロントエンド（Next.js / React / TypeScript）

---

## 起動方法

```bash
cd src
npm install
npm run dev
```

ブラウザで http://localhost:3000 を開く（`/chats` へリダイレクトされる）。

---

## モック ⇔ 実API 切替

`.env.local` の `NEXT_PUBLIC_DATA_SOURCE` を変更する。

```env
# モック（デフォルト）
NEXT_PUBLIC_DATA_SOURCE=mock

# 実API
NEXT_PUBLIC_DATA_SOURCE=api
NEXT_PUBLIC_API_BASE_URL=http://localhost:8080
```

- **mock**: `api/mock/` 内のインメモリ実装 + localStorage 永続化を使用
- **api**: `api/client/fetcher.ts` 内の実APIクライアントを使用

> モックデータを初期状態にリセットするには、ブラウザの DevTools → Application → Local Storage を全削除する。

---

## 主要URL

| URL | 画面 |
|---|---|
| `/chats` | チャット一覧 |
| `/chats/[chatId]` | 個別チャット |
| `/users` | ユーザ管理（検索・登録・更新） |

---

## ディレクトリ構成

```
src/
├── api/
│   ├── types.ts          # 型定義
│   ├── index.ts          # モック/API切替エントリポイント
│   ├── mock/             # モック実装
│   │   ├── storage.ts    # localStorage ヘルパー
│   │   ├── users.ts      # ユーザCRUDモック
│   │   └── chats.ts      # チャットモック
│   └── client/
│       └── fetcher.ts    # 実APIクライアント
├── app/
│   ├── layout.tsx
│   ├── page.tsx          # /chats へリダイレクト
│   ├── chats/
│   │   ├── page.tsx      # チャット一覧
│   │   └── [chatId]/
│   │       └── page.tsx  # 個別チャット
│   └── users/
│       └── page.tsx      # ユーザ管理
├── component/
│   ├── chat/
│   │   ├── Avatar.tsx
│   │   ├── ChatHeader.tsx
│   │   ├── ErrorBanner.tsx
│   │   ├── LoadingOverlay.tsx
│   │   ├── MessageBubble.tsx
│   │   ├── MessageComposer.tsx
│   │   ├── MessageList.tsx
│   │   └── ReactionPicker.tsx
│   └── user/
│       ├── UserForm.tsx
│       ├── UserSearchBar.tsx
│       └── UserTable.tsx
└── docs/
    └── ASSUMPTIONS.md    # 仕様補完記録
```

---

## 実API連携時の注意

- `api/client/fetcher.ts` の各関数がエンドポイントURLを保持しています
- チャット系APIは仮定義のため、バックエンドの実装に合わせて修正が必要です
- 詳細は `docs/ASSUMPTIONS.md` を参照
