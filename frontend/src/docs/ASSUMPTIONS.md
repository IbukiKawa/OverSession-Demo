# 仕様補完記録 (ASSUMPTIONS.md)

実装者: Claude Code
作成日: 2026-02-24

---

## 1. チャット系API（仮定義）

`API設計書_チャット一覧.xlsx` はテンプレート状態（URL `/api/put/aaaaa`、リクエストボディ未記載）のため、
個別チャット画面の要件を満たすべく以下の仮API形式を定義した。
**実APIが確定次第、`api/client/fetcher.ts` を修正すること。**

| メソッド | URL | 概要 |
|---|---|---|
| GET | `/api/get/chats?userId=...` | チャット一覧取得 |
| GET | `/api/get/chat/messages?chatId=...&cursor=...&limit=...` | メッセージ履歴取得（カーソルページング） |
| POST | `/api/post/chat/message` | メッセージ送信 |
| POST | `/api/post/chat/reaction` | リアクション送信 |

### ChatSummary（GET /api/get/chats レスポンス）
```json
{
  "chats": [
    {
      "chatId": "string",
      "partnerUserId": "string",
      "partnerUserName": "string",
      "partnerPictureName": "string | null",
      "lastMessage": "string | null",
      "lastMessageAt": "ISO8601 | null",
      "unreadCount": "number | null"
    }
  ]
}
```

### Message（GET /api/get/chat/messages レスポンス）
```json
{
  "messages": [
    {
      "messageId": "string",
      "chatId": "string",
      "senderUserId": "string",
      "text": "string",
      "sentAt": "ISO8601",
      "reactions": [
        { "type": 1, "count": 1, "reactedByMe": true }
      ]
    }
  ],
  "nextCursor": "string | null"
}
```

---

## 2. ログインユーザIDの扱い

設計書にログイン/認証フローの定義がなかったため、以下の仮実装とした。

- **モックモード**: `MOCK_CURRENT_USER_ID = "user01"` を固定値として使用
- **実APIモード**: `CURRENT_USER_ID` は空文字列。実装時はログイン後のJWTデコード等でセットすること（`api/index.ts` 修正）

---

## 3. ユーザ画像

- DBテーブルの `picture_name` はファイル名のみ（URLではない）
- API登録/更新リクエストの `userImageUrl` はURL形式
- フロントのモックではプロフィール画像を持たず、ユーザ名の頭文字+カラーでアバターを表示
- 実APIでは画像URLの構築ロジック（BaseURL + pictureName）が別途必要

---

## 4. DBとAPI定義の差分

| 項目 | DB物理名 | APIフィールド名 | 差分 |
|---|---|---|---|
| ユーザ画像 | `picture_name` (GET) | `userImageUrl` (POST/PUT) | GET は名前のみ返却、POST/PUT はURL |
| マッチングユーザID | `matching_user_id` (varchar) | `matchingUserId` (string) | 一致 |
| matchingテーブルのユーザID型 | `user_id int` | API: `userId string` | DBはintだが、APIはstring (user_idカラムはvarchar(6)) |

---

## 5. チャット一覧に表示するチャット

設計書に「ユーザIDに紐づくチャット一覧を返す」という定義があるのみ。
モックでは `userId` を無視し全チャットを返す実装にした。
実APIでは userId に応じてフィルタリングすること。

---

## 6. リアクション仕様

- 仕様書に 1:Smile, 2:Good, 3:Like, 4:Sad とあるため、下記の絵文字を対応付け:
  - 1: 😊 Smile
  - 2: 👍 Good
  - 3: ❤️ Like
  - 4: 😢 Sad
- 同一ユーザが同じリアクションを再度押すとトグル（取り消し）される（仕様に明示なし）

---

## 7. スクロールによる過去ログ取得

- カーソルはメッセージIDを使用（タイムスタンプカーソルも可だが、メッセージIDの方が一意性が高い）
- 初期表示は最新 20 件、スクロール最上部到達で 20 件ずつ遡る

---

## 8. 相手プロフィール表示

- 仕様では「モーダルor別ページどちらでも可」とあるため、モーダルを採用
- 表示内容はユーザマスタAPI (`GET /api/get/user?userId=...`) から取得

---

## 9. workingStatus の列挙値

設計書備考に「不在/出社」とあるため、この2値を想定。
フォームのセレクトボックスに `出社` / `不在` のみ表示。

---

## 10. 画面遷移

`画面遷移図.png` を参照したが、画像ファイルのため詳細不明。以下を仮定：
- `/` → `/chats` へリダイレクト
- `/chats` ← → `/chats/[chatId]` （戻るボタン）
- `/chats` ↔ `/users` （ヘッダーリンク）
