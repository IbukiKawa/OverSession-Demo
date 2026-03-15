あなたは Claude Code です（リポジトリを読み書き・コマンド実行できる前提）。

目的：ユーザマッチングシステムのフロントエンドを Next.js / React(TypeScript) で実装する。

制約：Claude Code は Excel を読めないため、以下の SPEC\_JSON（Excelから抽出済み）を唯一の仕様根拠として実装すること。



\# 絶対条件

\- 既に存在する空フォルダ（または既存Next.jsプロジェクト）内で、`api/`, `app/`, `component/` 配下に適切に分割してファイルを配置すること。

\- 最終的には実APIを叩く想定だが、まずはフロントだけで画面を見たい。

&nbsp; - `NEXT\_PUBLIC\_DATA\_SOURCE=mock|api` の環境変数で \*\*モック⇔実API切替\*\*できる構造にする。

&nbsp; - 画面側は切替を意識せず、`api/index.ts` 経由でデータ取得する。

\- 必要に応じて package.json を編集して依存追加してよい。

\- `npm install \&\& npm run dev` が通る状態にする。

\- ドキュメントにない情報は「後で差し替えやすい」前提で合理的に類推して補完し、`docs/ASSUMPTIONS.md` に明記する。



\# 実装する画面（最低限）

A) チャット一覧：`/chats`

\- 会話相手一覧、最終メッセージ、最終更新日時、未読数（妥当なUXで）を表示

\- クリックで個別チャットへ遷移



B) 個別チャット：`/chats/\[chatId]`

\- 仕様は SPEC\_JSON.screen.individualChat に従う（初期表示・アクション・入力制約・リアクション）

\- 自分=右寄せ、相手=左寄せ

\- 長押し/右クリックでリアクション選択UIを出す（PCは右クリックでOK）

\- スクロール最上部で過去ログ取得 → 上部結合

\- 相手アイコンクリックでプロフィール表示（モーダル or 別ページどちらでも可）

\- 通信失敗時は画面下部にエラー表示（項目定義の「エラーメッセージ」）



C) ユーザマスタ：`/users`

\- 検索（GET /api/get/user）

\- 新規登録（POST /api/post/user）

\- 更新（PUT /api/put/user）

\- フォームバリデーション（必須、最大長、型）

\- テーブル定義（SPEC\_JSON.db）とAPIの差分は ASSUMPTIONS に記録しつつ、APIスキーマを優先



\# ディレクトリ配置ルール

\- `app/`: ルーティング（App Router想定。既存が Pages Router なら合わせる）

&nbsp; - /users, /chats, /chats/\[chatId]

\- `component/`: 再利用コンポーネント

&nbsp; - chat: ChatHeader, MessageList, MessageBubble, ReactionPicker, MessageComposer, LoadingOverlay, ErrorBanner, Avatar など

&nbsp; - user: UserForm, UserTable, UserSearchBar など

\- `api/`: API層（実API + モック）

&nbsp; - api/client/\* : fetcher（baseURL, Authorization 等もここ）

&nbsp; - api/mock/\* : テストデータ + localStorageによる簡易永続（任意だが推奨）

&nbsp; - api/index.ts : モック/実API切替を吸収し、画面からはここだけ呼ぶ



\# チャット系APIについて（重要）

Excel側の「チャット一覧API設計書」はテンプレ状態で、確定しているのは Authorization ヘッダと 400エラー形式のみ。

よって、個別チャット画面要件を満たすため、以下の “仮API” を定義して実装する（後で差し替えやすく）。

\- GET  /api/get/chats?userId=... => { chats: ChatSummary\[] }

&nbsp; - ChatSummary: { chatId, partnerUserId, partnerUserName, partnerPictureName?, lastMessage?, lastMessageAt?, unreadCount? }

\- GET  /api/get/chat/messages?chatId=...\&cursor?=...\&limit?=... => { messages: Message\[], nextCursor?: string|null }

&nbsp; - Message: { messageId, chatId, senderUserId, text, sentAt, reactions?: Reaction\[] }

&nbsp; - Reaction: { type: 1|2|3|4, count: number, reactedByMe?: boolean }

\- POST /api/post/chat/message => request { chatId, senderUserId, text } => response { messageId, sentAt }

\- POST /api/post/chat/reaction => request { chatId, messageId, type, reactorUserId } => response 204

※ この仮API定義は docs/ASSUMPTIONS.md に明記する。



\# APIエラー形式（共通）

400 の場合は、SPEC\_JSON.api.userMaster.\*.responses.400 に合わせて

{ statusCode:int, reason:string, errors:\[{code:string,msg:string}] }

として UI で表示する。



\# 実装ステップ

1\) 既存構成を確認（package.json / Next.jsのルータ方式 / src有無）

2\) 型定義を作る（api/types.ts など）

&nbsp;  - User / Office / Matching / ChatSummary / Message / Reaction / ApiError

3\) API層を作る

&nbsp;  - api/index.ts は data source 切替

&nbsp;  - mock 実装：ユーザ検索/登録/更新 + チャット一覧/履歴/送信/リアクション/過去ログ（cursor paging）

4\) 画面実装

&nbsp;  - /users : 一覧+検索+登録+更新

&nbsp;  - /chats : 一覧

&nbsp;  - /chats/\[chatId] : 個別チャット（初期表示/アクション/スクロール/リアクション）

5\) README.md を整備（起動、モック⇔API切替、主要URL）

6\) 仕様補完を docs/ASSUMPTIONS.md にまとめる（DB定義の不整合など）



\# SPEC\_JSON（Excelから抽出済み。これを根拠に実装）

=== SPEC\_JSON\_BEGIN ===

{

&nbsp; "generatedAt": "2026-02-24",

&nbsp; "sourceFiles": \[

&nbsp;   {

&nbsp;     "file": "API設計書\_登録マスタ\_v2.xlsx",

&nbsp;     "sheets": \[

&nbsp;       "概要(例)",

&nbsp;       "更新・削除",

&nbsp;       "登録",

&nbsp;       "検索",

&nbsp;       "サービスクラス概要（共通）"

&nbsp;     ]

&nbsp;   },

&nbsp;   {

&nbsp;     "file": "API設計書\_チャット一覧.xlsx",

&nbsp;     "sheets": \[

&nbsp;       "更新",

&nbsp;       "登録",

&nbsp;       "検索",

&nbsp;       "削除"

&nbsp;     ]

&nbsp;   },

&nbsp;   {

&nbsp;     "file": "ユーザ情報\_テーブル定義書.xlsx",

&nbsp;     "sheets": \[

&nbsp;       "ユーザ",

&nbsp;       "マッチング",

&nbsp;       "オフィス"

&nbsp;     ]

&nbsp;   },

&nbsp;   {

&nbsp;     "file": "画面設計書\_個別チャット.xls",

&nbsp;     "convertedTo": "画面設計書\_個別チャット.xlsx",

&nbsp;     "sheets": \[

&nbsp;       "画面設計 (画面モードなし） - 表1"

&nbsp;     ]

&nbsp;   }

&nbsp; ],

&nbsp; "api": {

&nbsp;   "userMaster": {

&nbsp;     "register": {

&nbsp;       "method": "POST",

&nbsp;       "url": "/api/post/user",

&nbsp;       "headers": \[

&nbsp;         {

&nbsp;           "name": "Content-Type",

&nbsp;           "required": true,

&nbsp;           "type": "string",

&nbsp;           "description": "application/json"

&nbsp;         }

&nbsp;       ],

&nbsp;       "query": \[],

&nbsp;       "requestBody": \[

&nbsp;         {

&nbsp;           "name": "userName",

&nbsp;           "required": true,

&nbsp;           "type": "string",

&nbsp;           "description": null

&nbsp;         },

&nbsp;         {

&nbsp;           "name": "primaryHeadOfficeName",

&nbsp;           "required": false,

&nbsp;           "type": "string",

&nbsp;           "description": null

&nbsp;         },

&nbsp;         {

&nbsp;           "name": "secondaryHeadOfficeName",

&nbsp;           "required": false,

&nbsp;           "type": "string",

&nbsp;           "description": null

&nbsp;         },

&nbsp;         {

&nbsp;           "name": "departmentName",

&nbsp;           "required": false,

&nbsp;           "type": "string",

&nbsp;           "description": null

&nbsp;         },

&nbsp;         {

&nbsp;           "name": "officeId",

&nbsp;           "required": false,

&nbsp;           "type": "int",

&nbsp;           "description": null

&nbsp;         },

&nbsp;         {

&nbsp;           "name": "floor",

&nbsp;           "required": false,

&nbsp;           "type": "int",

&nbsp;           "description": null

&nbsp;         },

&nbsp;         {

&nbsp;           "name": "gender",

&nbsp;           "required": false,

&nbsp;           "type": "string",

&nbsp;           "description": null

&nbsp;         },

&nbsp;         {

&nbsp;           "name": "affiliationYear",

&nbsp;           "required": false,

&nbsp;           "type": "int",

&nbsp;           "description": null

&nbsp;         },

&nbsp;         {

&nbsp;           "name": "workingStatus",

&nbsp;           "required": true,

&nbsp;           "type": "string",

&nbsp;           "description": null

&nbsp;         },

&nbsp;         {

&nbsp;           "name": "matchingUserId",

&nbsp;           "required": false,

&nbsp;           "type": "string",

&nbsp;           "description": null

&nbsp;         },

&nbsp;         {

&nbsp;           "name": "userImageUrl",

&nbsp;           "required": false,

&nbsp;           "type": "string",

&nbsp;           "description": null

&nbsp;         }

&nbsp;       ],

&nbsp;       "responses": {

&nbsp;         "201": {

&nbsp;           "fields": \[

&nbsp;             {

&nbsp;               "name": "userId",

&nbsp;               "required": true,

&nbsp;               "type": "string",

&nbsp;               "description": null

&nbsp;             }

&nbsp;           ],

&nbsp;           "nested": \[

&nbsp;             {

&nbsp;               "name": "code",

&nbsp;               "required": true,

&nbsp;               "type": "string",

&nbsp;               "description": "エラーコード"

&nbsp;             },

&nbsp;             {

&nbsp;               "name": "msg",

&nbsp;               "required": true,

&nbsp;               "type": "string",

&nbsp;               "description": "エラーメッセージ"

&nbsp;             }

&nbsp;           ]

&nbsp;         },

&nbsp;         "400": {

&nbsp;           "fields": \[

&nbsp;             {

&nbsp;               "name": "statusCode",

&nbsp;               "required": true,

&nbsp;               "type": "integer",

&nbsp;               "description": "ステータスコード"

&nbsp;             },

&nbsp;             {

&nbsp;               "name": "reason",

&nbsp;               "required": true,

&nbsp;               "type": "string",

&nbsp;               "description": "理由"

&nbsp;             },

&nbsp;             {

&nbsp;               "name": "errors",

&nbsp;               "required": true,

&nbsp;               "type": "Array",

&nbsp;               "description": null

&nbsp;             }

&nbsp;           ],

&nbsp;           "nested": \[

&nbsp;             {

&nbsp;               "name": "code",

&nbsp;               "required": true,

&nbsp;               "type": "string",

&nbsp;               "description": "エラーコード"

&nbsp;             },

&nbsp;             {

&nbsp;               "name": "msg",

&nbsp;               "required": true,

&nbsp;               "type": "string",

&nbsp;               "description": "エラーメッセージ"

&nbsp;             }

&nbsp;           ]

&nbsp;         }

&nbsp;       }

&nbsp;     },

&nbsp;     "update": {

&nbsp;       "method": "PUT",

&nbsp;       "url": "/api/put/user",

&nbsp;       "headers": \[

&nbsp;         {

&nbsp;           "name": "Content-Type",

&nbsp;           "required": true,

&nbsp;           "type": "string",

&nbsp;           "description": "application/json"

&nbsp;         }

&nbsp;       ],

&nbsp;       "query": \[],

&nbsp;       "requestBody": \[

&nbsp;         {

&nbsp;           "name": "userId",

&nbsp;           "required": true,

&nbsp;           "type": "string",

&nbsp;           "description": null

&nbsp;         },

&nbsp;         {

&nbsp;           "name": "userName",

&nbsp;           "required": true,

&nbsp;           "type": "string",

&nbsp;           "description": null

&nbsp;         },

&nbsp;         {

&nbsp;           "name": "primaryHeadOfficeName",

&nbsp;           "required": false,

&nbsp;           "type": "string",

&nbsp;           "description": null

&nbsp;         },

&nbsp;         {

&nbsp;           "name": "secondaryHeadOfficeName",

&nbsp;           "required": false,

&nbsp;           "type": "string",

&nbsp;           "description": null

&nbsp;         },

&nbsp;         {

&nbsp;           "name": "departmentName",

&nbsp;           "required": false,

&nbsp;           "type": "string",

&nbsp;           "description": null

&nbsp;         },

&nbsp;         {

&nbsp;           "name": "officeId",

&nbsp;           "required": false,

&nbsp;           "type": "int",

&nbsp;           "description": null

&nbsp;         },

&nbsp;         {

&nbsp;           "name": "floor",

&nbsp;           "required": false,

&nbsp;           "type": "int",

&nbsp;           "description": null

&nbsp;         },

&nbsp;         {

&nbsp;           "name": "gender",

&nbsp;           "required": false,

&nbsp;           "type": "string",

&nbsp;           "description": null

&nbsp;         },

&nbsp;         {

&nbsp;           "name": "affiliationYear",

&nbsp;           "required": false,

&nbsp;           "type": "int",

&nbsp;           "description": null

&nbsp;         },

&nbsp;         {

&nbsp;           "name": "workingStatus",

&nbsp;           "required": true,

&nbsp;           "type": "string",

&nbsp;           "description": null

&nbsp;         },

&nbsp;         {

&nbsp;           "name": "matchingUserId",

&nbsp;           "required": false,

&nbsp;           "type": "string",

&nbsp;           "description": null

&nbsp;         },

&nbsp;         {

&nbsp;           "name": "userImageUrl",

&nbsp;           "required": false,

&nbsp;           "type": "string",

&nbsp;           "description": null

&nbsp;         },

&nbsp;         {

&nbsp;           "name": "deleted",

&nbsp;           "required": true,

&nbsp;           "type": "boolean",

&nbsp;           "description": null

&nbsp;         }

&nbsp;       ],

&nbsp;       "responses": {

&nbsp;         "204": {

&nbsp;           "fields": \[],

&nbsp;           "nested": \[]

&nbsp;         },

&nbsp;         "400": {

&nbsp;           "fields": \[

&nbsp;             {

&nbsp;               "name": "statusCode",

&nbsp;               "required": true,

&nbsp;               "type": "integer",

&nbsp;               "description": "ステータスコード"

&nbsp;             },

&nbsp;             {

&nbsp;               "name": "reason",

&nbsp;               "required": true,

&nbsp;               "type": "string",

&nbsp;               "description": "理由"

&nbsp;             },

&nbsp;             {

&nbsp;               "name": "errors",

&nbsp;               "required": true,

&nbsp;               "type": "Array",

&nbsp;               "description": null

&nbsp;             }

&nbsp;           ],

&nbsp;           "nested": \[

&nbsp;             {

&nbsp;               "name": "code",

&nbsp;               "required": true,

&nbsp;               "type": "string",

&nbsp;               "description": "エラーコード"

&nbsp;             },

&nbsp;             {

&nbsp;               "name": "msg",

&nbsp;               "required": true,

&nbsp;               "type": "string",

&nbsp;               "description": "エラーメッセージ"

&nbsp;             }

&nbsp;           ]

&nbsp;         }

&nbsp;       }

&nbsp;     },

&nbsp;     "search": {

&nbsp;       "method": "GET",

&nbsp;       "url": "/api/get/user",

&nbsp;       "headers": \[

&nbsp;         {

&nbsp;           "name": "Content-Type",

&nbsp;           "required": true,

&nbsp;           "type": "string",

&nbsp;           "description": "application/json"

&nbsp;         }

&nbsp;       ],

&nbsp;       "query": \[

&nbsp;         {

&nbsp;           "name": "userId",

&nbsp;           "required": false,

&nbsp;           "type": "string",

&nbsp;           "description": "nullの場合は全件取得"

&nbsp;         }

&nbsp;       ],

&nbsp;       "requestBody": \[],

&nbsp;       "responses": {

&nbsp;         "200": {

&nbsp;           "fields": \[

&nbsp;             {

&nbsp;               "name": "userId",

&nbsp;               "required": true,

&nbsp;               "type": "string",

&nbsp;               "description": null

&nbsp;             },

&nbsp;             {

&nbsp;               "name": "userName",

&nbsp;               "required": true,

&nbsp;               "type": "string",

&nbsp;               "description": null

&nbsp;             },

&nbsp;             {

&nbsp;               "name": "primaryHeadOfficeName",

&nbsp;               "required": false,

&nbsp;               "type": "string",

&nbsp;               "description": null

&nbsp;             },

&nbsp;             {

&nbsp;               "name": "secondaryHeadOfficeName",

&nbsp;               "required": false,

&nbsp;               "type": "string",

&nbsp;               "description": null

&nbsp;             },

&nbsp;             {

&nbsp;               "name": "departmentName",

&nbsp;               "required": false,

&nbsp;               "type": "string",

&nbsp;               "description": null

&nbsp;             },

&nbsp;             {

&nbsp;               "name": "officeId",

&nbsp;               "required": false,

&nbsp;               "type": "int",

&nbsp;               "description": null

&nbsp;             },

&nbsp;             {

&nbsp;               "name": "floor",

&nbsp;               "required": false,

&nbsp;               "type": "int",

&nbsp;               "description": null

&nbsp;             },

&nbsp;             {

&nbsp;               "name": "gender",

&nbsp;               "required": false,

&nbsp;               "type": "string",

&nbsp;               "description": null

&nbsp;             },

&nbsp;             {

&nbsp;               "name": "affiliationYear",

&nbsp;               "required": false,

&nbsp;               "type": "int",

&nbsp;               "description": null

&nbsp;             },

&nbsp;             {

&nbsp;               "name": "workingStatus",

&nbsp;               "required": true,

&nbsp;               "type": "string",

&nbsp;               "description": null

&nbsp;             },

&nbsp;             {

&nbsp;               "name": "matchingUserId",

&nbsp;               "required": false,

&nbsp;               "type": "string",

&nbsp;               "description": null

&nbsp;             },

&nbsp;             {

&nbsp;               "name": "pictureName",

&nbsp;               "required": false,

&nbsp;               "type": "string",

&nbsp;               "description": null

&nbsp;             },

&nbsp;             {

&nbsp;               "name": "deleted",

&nbsp;               "required": true,

&nbsp;               "type": "boolean",

&nbsp;               "description": null

&nbsp;             }

&nbsp;           ],

&nbsp;           "nested": \[

&nbsp;             {

&nbsp;               "name": "code",

&nbsp;               "required": true,

&nbsp;               "type": "string",

&nbsp;               "description": "エラーコード"

&nbsp;             },

&nbsp;             {

&nbsp;               "name": "msg",

&nbsp;               "required": true,

&nbsp;               "type": "string",

&nbsp;               "description": "エラーメッセージ"

&nbsp;             }

&nbsp;           ]

&nbsp;         },

&nbsp;         "400": {

&nbsp;           "fields": \[

&nbsp;             {

&nbsp;               "name": "statusCode",

&nbsp;               "required": true,

&nbsp;               "type": "integer",

&nbsp;               "description": "ステータスコード"

&nbsp;             },

&nbsp;             {

&nbsp;               "name": "reason",

&nbsp;               "required": true,

&nbsp;               "type": "string",

&nbsp;               "description": "理由"

&nbsp;             },

&nbsp;             {

&nbsp;               "name": "errors",

&nbsp;               "required": true,

&nbsp;               "type": "Array",

&nbsp;               "description": null

&nbsp;             }

&nbsp;           ],

&nbsp;           "nested": \[

&nbsp;             {

&nbsp;               "name": "code",

&nbsp;               "required": true,

&nbsp;               "type": "string",

&nbsp;               "description": "エラーコード"

&nbsp;             },

&nbsp;             {

&nbsp;               "name": "msg",

&nbsp;               "required": true,

&nbsp;               "type": "string",

&nbsp;               "description": "エラーメッセージ"

&nbsp;             }

&nbsp;           ]

&nbsp;         }

&nbsp;       }

&nbsp;     }

&nbsp;   },

&nbsp;   "chatList\_template": {

&nbsp;     "method": "PUT",

&nbsp;     "url": "/api/put/aaaaa",

&nbsp;     "headers": \[

&nbsp;       {

&nbsp;         "name": "Content-Type",

&nbsp;         "required": true,

&nbsp;         "type": "string",

&nbsp;         "description": "application/json"

&nbsp;       },

&nbsp;       {

&nbsp;         "name": "Authorization",

&nbsp;         "required": true,

&nbsp;         "type": "string",

&nbsp;         "description": "ログイン時のJWT"

&nbsp;       }

&nbsp;     ],

&nbsp;     "query": \[],

&nbsp;     "requestBody": \[],

&nbsp;     "responses": {

&nbsp;       "204": {

&nbsp;         "fields": \[],

&nbsp;         "nested": \[]

&nbsp;       },

&nbsp;       "400": {

&nbsp;         "fields": \[

&nbsp;           {

&nbsp;             "name": "statusCode",

&nbsp;             "required": true,

&nbsp;             "type": "integer",

&nbsp;             "description": "ステータスコード"

&nbsp;           },

&nbsp;           {

&nbsp;             "name": "reason",

&nbsp;             "required": true,

&nbsp;             "type": "string",

&nbsp;             "description": "理由"

&nbsp;           },

&nbsp;           {

&nbsp;             "name": "errors",

&nbsp;             "required": true,

&nbsp;             "type": "Array",

&nbsp;             "description": null

&nbsp;           }

&nbsp;         ],

&nbsp;         "nested": \[

&nbsp;           {

&nbsp;             "name": "code",

&nbsp;             "required": true,

&nbsp;             "type": "string",

&nbsp;             "description": "エラーコード"

&nbsp;           },

&nbsp;           {

&nbsp;             "name": "msg",

&nbsp;             "required": true,

&nbsp;             "type": "string",

&nbsp;             "description": "エラーメッセージ"

&nbsp;           }

&nbsp;         ]

&nbsp;       }

&nbsp;     }

&nbsp;   },

&nbsp;   "notes": \[

&nbsp;     "チャット一覧API設計書はテンプレート状態（'○○をするAPI', URL '/api/put/aaaaa' 等）で、リクエストボディ/クエリパラメータも未記載。個別チャット画面設計書の要求（履歴取得/送信/リアクション/過去ログ）を満たすため、実装側でAPI形を仮決めし、後で差し替え可能な構造にする必要がある。",

&nbsp;     "ユーザマスタAPIはURLの命名から method を推定: /api/post/user=POST, /api/put/user=PUT, /api/get/user=GET。"

&nbsp;   ]

&nbsp; },

&nbsp; "db": {

&nbsp;   "ユーザ": {

&nbsp;     "logicalName": "ユーザ",

&nbsp;     "physicalName": "user",

&nbsp;     "columns": \[

&nbsp;       {

&nbsp;         "No.": 1,

&nbsp;         "論理名": "ユーザID",

&nbsp;         "物理名": "user\_id",

&nbsp;         "データ型": "character varying(6)",

&nbsp;         "Not Null": "Yes (PK)"

&nbsp;       },

&nbsp;       {

&nbsp;         "No.": 2,

&nbsp;         "論理名": "ユーザ名",

&nbsp;         "物理名": "user\_name",

&nbsp;         "データ型": "character varying(100)",

&nbsp;         "Not Null": "Yes "

&nbsp;       },

&nbsp;       {

&nbsp;         "No.": 3,

&nbsp;         "論理名": "本部名1",

&nbsp;         "物理名": "primary\_head\_office\_name",

&nbsp;         "データ型": "character varying(100)"

&nbsp;       },

&nbsp;       {

&nbsp;         "No.": 4,

&nbsp;         "論理名": "本部名2",

&nbsp;         "物理名": "secondary\_head\_office\_name",

&nbsp;         "データ型": "character varying(100)"

&nbsp;       },

&nbsp;       {

&nbsp;         "No.": 5,

&nbsp;         "論理名": "部署名",

&nbsp;         "物理名": "department\_name",

&nbsp;         "データ型": "character varying(100)"

&nbsp;       },

&nbsp;       {

&nbsp;         "No.": 6,

&nbsp;         "論理名": "オフィスID",

&nbsp;         "物理名": "office\_id",

&nbsp;         "データ型": "int"

&nbsp;       },

&nbsp;       {

&nbsp;         "No.": 7,

&nbsp;         "論理名": "所属階",

&nbsp;         "物理名": "floor",

&nbsp;         "データ型": "int"

&nbsp;       },

&nbsp;       {

&nbsp;         "No.": 8,

&nbsp;         "論理名": "性別",

&nbsp;         "物理名": "gender",

&nbsp;         "データ型": "character varying(20)"

&nbsp;       },

&nbsp;       {

&nbsp;         "No.": 9,

&nbsp;         "論理名": "在籍年数",

&nbsp;         "物理名": "affiliation\_year",

&nbsp;         "データ型": "int"

&nbsp;       },

&nbsp;       {

&nbsp;         "No.": 10,

&nbsp;         "論理名": "出社ステータス",

&nbsp;         "物理名": "working\_status",

&nbsp;         "データ型": "character varying(30)",

&nbsp;         "Not Null": "Yes",

&nbsp;         "備考": "不在/出社"

&nbsp;       },

&nbsp;       {

&nbsp;         "No.": 11,

&nbsp;         "論理名": "マッチングユーザID",

&nbsp;         "物理名": "matching\_user\_id",

&nbsp;         "データ型": "character varying(6)"

&nbsp;       },

&nbsp;       {

&nbsp;         "No.": 12,

&nbsp;         "論理名": "ユーザ画像名",

&nbsp;         "物理名": "picture\_name",

&nbsp;         "データ型": "character varying(100)"

&nbsp;       }

&nbsp;     ],

&nbsp;     "notes": \[]

&nbsp;   },

&nbsp;   "マッチング": {

&nbsp;     "logicalName": "マッチング",

&nbsp;     "physicalName": "matching",

&nbsp;     "columns": \[

&nbsp;       {

&nbsp;         "No.": 1,

&nbsp;         "論理名": "日付",

&nbsp;         "物理名": "date",

&nbsp;         "データ型": "date",

&nbsp;         "Not Null": "Yes (PK)"

&nbsp;       },

&nbsp;       {

&nbsp;         "No.": 2,

&nbsp;         "論理名": "ユーザID",

&nbsp;         "物理名": "user\_id",

&nbsp;         "データ型": "int",

&nbsp;         "Not Null": "Yes (PK)"

&nbsp;       },

&nbsp;       {

&nbsp;         "No.": 3,

&nbsp;         "論理名": "マッチングユーザID",

&nbsp;         "物理名": "matching\_user\_id",

&nbsp;         "データ型": "int",

&nbsp;         "Not Null": "Yes"

&nbsp;       }

&nbsp;     ],

&nbsp;     "notes": \[]

&nbsp;   },

&nbsp;   "オフィス": {

&nbsp;     "logicalName": "オフィス",

&nbsp;     "physicalName": "office",

&nbsp;     "columns": \[

&nbsp;       {

&nbsp;         "No.": 1,

&nbsp;         "論理名": "オフィスID",

&nbsp;         "物理名": "office\_id",

&nbsp;         "データ型": "int",

&nbsp;         "Not Null": "Yes (PK)"

&nbsp;       },

&nbsp;       {

&nbsp;         "No.": 2,

&nbsp;         "論理名": "オフィス名",

&nbsp;         "物理名": "office\_name",

&nbsp;         "データ型": "character varying(30)",

&nbsp;         "Not Null": "Yes"

&nbsp;       }

&nbsp;     ],

&nbsp;     "notes": \[

&nbsp;       "自販機がある拠点のみ"

&nbsp;     ]

&nbsp;   }

&nbsp; },

&nbsp; "screen": {

&nbsp;   "individualChat": {

&nbsp;     "items": \[

&nbsp;       {

&nbsp;         "No": 1,

&nbsp;         "項目名": "日付ヘッダー",

&nbsp;         "項目種別": "ラベル",

&nbsp;         "表示書式": "M/d(aaa)",

&nbsp;         "更新可否": "不可",

&nbsp;         "初期値": "システム",

&nbsp;         "必須": "-",

&nbsp;         "型": "date",

&nbsp;         "最小値\\n最小文字": "-",

&nbsp;         "最大値\\n最大文字": "-",

&nbsp;         "(列挙名/コード)": "-",

&nbsp;         "繰返し": "あり"

&nbsp;       },

&nbsp;       {

&nbsp;         "No": 2,

&nbsp;         "項目名": "相手名",

&nbsp;         "項目種別": "ラベル",

&nbsp;         "型": "string",

&nbsp;         "最大値\\n最大文字": 50

&nbsp;       },

&nbsp;       {

&nbsp;         "No": 3,

&nbsp;         "項目名": "自分が送信したメッセージ",

&nbsp;         "項目種別": "ラベル(可変)",

&nbsp;         "型": "string",

&nbsp;         "最小値\\n最小文字": 1,

&nbsp;         "最大値\\n最大文字": 2000

&nbsp;       },

&nbsp;       {

&nbsp;         "No": 4,

&nbsp;         "項目名": "相手が送信したメッセージ",

&nbsp;         "項目種別": "ラベル(可変)",

&nbsp;         "型": "string",

&nbsp;         "最小値\\n最小文字": 1,

&nbsp;         "最大値\\n最大文字": 2000

&nbsp;       },

&nbsp;       {

&nbsp;         "No": 5,

&nbsp;         "項目名": "送信時刻",

&nbsp;         "項目種別": "ラベル",

&nbsp;         "表示書式": "HH:mm",

&nbsp;         "型": "dateTIme"

&nbsp;       },

&nbsp;       {

&nbsp;         "No": 6,

&nbsp;         "項目名": "相手アイコン",

&nbsp;         "項目種別": "画像",

&nbsp;         "初期値": "デフォルト",

&nbsp;         "型": "Image"

&nbsp;       },

&nbsp;       {

&nbsp;         "No": 7,

&nbsp;         "項目名": "エラーメッセージ",

&nbsp;         "項目種別": "ラベル",

&nbsp;         "初期値": "非表示",

&nbsp;         "型": "string"

&nbsp;       },

&nbsp;       {

&nbsp;         "No": 8,

&nbsp;         "項目名": "メッセージ入力欄",

&nbsp;         "項目種別": "テキストエリア",

&nbsp;         "初期値": "(メッセージを入力してください)",

&nbsp;         "必須": "必須",

&nbsp;         "型": "String",

&nbsp;         "最小値\\n最小文字": 1,

&nbsp;         "最大値\\n最大文字": 1000

&nbsp;       },

&nbsp;       {

&nbsp;         "No": 11,

&nbsp;         "項目名": "リアクションボタン",

&nbsp;         "(列挙名/コード)": "1:Smile, 2:Good, 3:Like, 4: Sad ",

&nbsp;         "型": "number"

&nbsp;       }

&nbsp;     ],

&nbsp;     "actions": \[

&nbsp;       {

&nbsp;         "項目No": 1,

&nbsp;         "項目名": "送信ボタン",

&nbsp;         "アクション定義": "クリック時：入力値のバリデーション、メッセージ送信APIを呼び出す。成功時に個別メッセージ画面の最下部にメッセージを追加する。失敗時に画面下部にエラーメッセージを表示する"

&nbsp;       },

&nbsp;       {

&nbsp;         "項目No": 2,

&nbsp;         "項目名": "戻るボタン",

&nbsp;         "アクション定義": "クリック時：遷移元の画面へ戻る"

&nbsp;       },

&nbsp;       {

&nbsp;         "項目No": 3,

&nbsp;         "項目名": "相手が送信したメッセージ",

&nbsp;         "アクション定義": "長押し時：リアクションボタンのタブを表示する"

&nbsp;       },

&nbsp;       {

&nbsp;         "項目No": 4,

&nbsp;         "項目名": "リアクションボタン",

&nbsp;         "アクション定義": "クリック時：リアクション送信APIを呼び出す。成功時にメッセージ下部にリアクションマークを追加する。失敗時に画面下部にエラーメッセージを表示する"

&nbsp;       },

&nbsp;       {

&nbsp;         "項目No": 5,

&nbsp;         "項目名": "メッセージ入力欄",

&nbsp;         "アクション定義": "入力変更時：入力文字数をカウントする。最大文字数超過の場合、送信ボタンを非活性にする。"

&nbsp;       },

&nbsp;       {

&nbsp;         "項目No": 6,

&nbsp;         "項目名": "相手アイコン",

&nbsp;         "アクション定義": "クリック時：相手のプロフィールを表示する"

&nbsp;       },

&nbsp;       {

&nbsp;         "項目No": 7,

&nbsp;         "項目名": "メッセージリスト",

&nbsp;         "アクション定義": "スクロール時：スクロール位置が最上部に達したら、過去ログ取得用のAPIを追加で呼び出す。取得した過去ログをリスト上部に結合して表示する"

&nbsp;       },

&nbsp;       {

&nbsp;         "項目No": 8,

&nbsp;         "項目名": "画面全体",

&nbsp;         "アクション定義": "メッセージ履歴取得APIを呼び出す。取得データを時系列順に表示し、最下部へスクロールする。通信失敗時はポップアップ表示等で通知する。"

&nbsp;       }

&nbsp;     ],

&nbsp;     "initialDisplay": \[

&nbsp;       {

&nbsp;         "No": 1,

&nbsp;         "概要": "画面描画準備",

&nbsp;         "詳細": "ローディングインジケータを表示し、ユーザー操作を一時的にブロック"

&nbsp;       },

&nbsp;       {

&nbsp;         "No": 2,

&nbsp;         "概要": "チャット履歴取得",

&nbsp;         "詳細": "非同期でサーバーからチャットメッセージ履歴を取得する。"

&nbsp;       },

&nbsp;       {

&nbsp;         "No": 3,

&nbsp;         "概要": "メッセージリストの作成",

&nbsp;         "詳細": "取得した履歴データを時系列順に展開して表示する。送信者IDが自分なら右寄せ、相手なら左寄せにする。"

&nbsp;       },

&nbsp;       {

&nbsp;         "No": 4,

&nbsp;         "概要": "スクロール",

&nbsp;         "詳細": "メッセージ表示後、自動的に画面最下部までスクロールする"

&nbsp;       },

&nbsp;       {

&nbsp;         "No": 5,

&nbsp;         "概要": "入力欄の初期化",

&nbsp;         "詳細": "メッセージ入力欄を空にする。保存された下書き等があればセットする。"

&nbsp;       },

&nbsp;       {

&nbsp;         "No": 6,

&nbsp;         "概要": "エラーハンドリング",

&nbsp;         "詳細": "通信失敗時はローディングを中止し、エラーメッセージを表示欄に出す。"

&nbsp;       }

&nbsp;     ]

&nbsp;   }

&nbsp; }

}

=== SPEC\_JSON\_END ===



開始せよ。まず repo 状態を確認し、必要なら next.js の雛形作成→型→API層→画面→README→ASSUMPTIONS の順で進めること。



\# 追加要件（2026-02-24 追記）

以下の機能を既存実装に追加する。



\## 1\. 既読/未読表示（チャット詳細）

\- `Message` 型に `readAt?: string | null` を追加する。

&nbsp; - `readAt` = ISO8601 文字列 → 既読（受信者が読んだ日時）

&nbsp; - `readAt` = `null` → 未読

\- 自分が送信したメッセージのタイムスタンプ横に既読/未読ラベルを表示する。

&nbsp; - 既読：「既読」（青字）

&nbsp; - 未読：「未読」（薄灰字）

\- チャットを開いた時点で、相手から届いたメッセージを自動的に既読にする（`POST /api/post/chat/read`）。

\- モックでは `unreadCount` のリセットも行う。

\- テストデータ：古いメッセージは既読済み、最新の相手メッセージ2件は未読として初期データに設定する。



\## 2\. リアクション操作の改善（チャット詳細）

\- 相手メッセージにカーソルを乗せると😊ボタンを表示し、クリックでリアクションピッカーを開く（デスクトップでの発見性向上）。

\- 長押し（500ms）・右クリックによるピッカー起動も継続してサポートする。

\- リアクション（😊👍❤️😢）クリック時はトグル動作（同じリアクションを再押しで取り消し）。

\- 楽観的UIアップデートあり。通信失敗時は画面下部にエラーバナーを表示。

\- テストデータ：`msg004` に初期リアクション（Smile×1）を設定済み。



\## 3\. ユーザ画像の取得・表示（全画面共通）

\- `User.pictureName` にプロフィール画像URLを格納する（モックではフルURLを直接格納、実APIでは構築ルールを別途定義）。

\- `Avatar` コンポーネントに `imageUrl?: string` prop を追加し、画像がある場合は `<img>` を表示、ない場合は頭文字+カラー円にフォールバック。

\- 画像を表示する画面：

&nbsp; - チャット一覧（`/chats`）：相手アイコン

&nbsp; - 個別チャット（`/chats/[chatId]`）：相手吹き出し横アイコン・プロフィールモーダル内アイコン

&nbsp; - ユーザ管理（`/users`）：テーブル左端にアイコン列を追加

\- `ChatSummary.partnerPictureName` を利用して相手の画像をチャット一覧・詳細で表示する。

\- モックのプレースホルダー画像：`https://i.pravatar.cc/150?u={userId}`（シード固定で一貫したアバターを生成）

\- `next.config.ts` の `images.remotePatterns` に `i.pravatar.cc` を追加すること。

\- ユーザ登録・更新フォームの `userImageUrl` フィールドで画像URLを入力・保存できるようにする。



\# UIリファクタリング要件（2026-02-28 追記）

あなたは Claude Code です（リポジトリを読み書き・コマンド実行できる前提）。

## 目的

既存実装は `<div className="...">` 等、素の HTML 要素と Tailwind CSS クラス文字列で構成されている。
これを **React らしいコンポーネント指向** の実装に書き換える。

## UIライブラリ方針

- **メインライブラリ: MUI (Material UI) v6** を採用し、できる限り MUI で実装する。
- MUI で実現できない機能や演出は、他のパッケージを導入して補完してよい。
- Tailwind CSS は廃止し、スタイリングは MUI の `sx` prop を使用する。

## インストール手順

`frontend/src/` ディレクトリで以下を実行する（React 19 との peer 依存解決のため `--legacy-peer-deps` を使用）：

```bash
npm install @mui/material @mui/icons-material @emotion/react @emotion/styled --legacy-peer-deps
```

## 実装ガイドライン

### レイアウト
- `<div className="flex ...">` → `<Box display="flex" ...>` または `<Stack>`
- `<div className="grid ...">` → `<Box display="grid" ...>`
- ページ全体の幅制限 → `<Container maxWidth="sm|md|lg">`

### テキスト
- `<h1>`, `<h2>`, `<p>`, `<span>` → `<Typography variant="h6|subtitle1|body2|caption" ...>`

### ボタン
- `<button className="px-4 py-2 bg-blue-500 ...">` → `<Button variant="contained">`
- アイコンボタン → `<IconButton>`

### フォーム
- `<input type="text">` → `<TextField size="small">`
- `<textarea>` → `<TextField multiline maxRows={4}>`
- `<select>` → `<Select>` + `<FormControl>` + `<InputLabel>` + `<MenuItem>`
- `<input type="checkbox">` → `<Checkbox>` + `<FormControlLabel>`

### ヘッダー
- sticky な `<div>` ヘッダー → `<AppBar position="sticky">` + `<Toolbar>`

### リスト（チャット一覧）
- `<ul>/<li>` → `<List>` + `<ListItemButton>` + `<ListItemAvatar>` + `<ListItemText>`
- 未読バッジ → `<Badge badgeContent={count} color="error">`

### テーブル（ユーザ一覧）
- `<table>/<tr>/<td>` → `<TableContainer>` + `<Table>` + `<TableHead>` + `<TableBody>` + `<TableRow>` + `<TableCell>`
- ステータス表示 → `<Chip color="success|default">`

### モーダル
- `<div className="fixed inset-0 ...">` → `<Dialog open maxWidth="sm" fullWidth>`
  + `<DialogTitle>` + `<DialogContent>` + `<DialogActions>`

### ローディング
- アニメーションスピナー div → `<Backdrop>` + `<CircularProgress>` + `<Typography>`

### エラー表示
- エラー赤枠 div → `<Alert severity="error" onClose={...}>`

### アバター
- 独自実装の円形アバター → `<Avatar>` (MUI) を `src` prop と fallback (bgcolor + 頭文字) で実装

## テーマ設定

`app/ThemeRegistry.tsx` を Client Component として作成し、`app/layout.tsx` でラップする：

```tsx
'use client';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';

const theme = createTheme({
  palette: { primary: { main: '#3b82f6' } },
  typography: { fontFamily: 'Arial, Helvetica, sans-serif' },
  components: { MuiButton: { defaultProps: { disableElevation: true } } },
});

export default function ThemeRegistry({ children }) {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      {children}
    </ThemeProvider>
  );
}
```

## 変更対象ファイル

| ファイル | 主な変更 |
|---------|---------|
| `app/ThemeRegistry.tsx` | 新規作成（MUI テーマ） |
| `app/layout.tsx` | ThemeRegistry でラップ |
| `app/globals.css` | Tailwind 廃止、最小化 |
| `app/chats/page.tsx` | List + AppBar + Badge |
| `app/chats/[chatId]/page.tsx` | Dialog（プロフィールモーダル） |
| `app/users/page.tsx` | Container + AppBar + Button |
| `component/chat/Avatar.tsx` | MUI Avatar |
| `component/chat/ChatHeader.tsx` | AppBar + Toolbar + IconButton |
| `component/chat/MessageBubble.tsx` | Box + Paper + Chip + Typography |
| `component/chat/MessageList.tsx` | Box + CircularProgress + Chip |
| `component/chat/MessageComposer.tsx` | TextField + Button |
| `component/chat/ReactionPicker.tsx` | Paper + IconButton + Tooltip |
| `component/chat/ErrorBanner.tsx` | Alert |
| `component/chat/LoadingOverlay.tsx` | Backdrop + CircularProgress |
| `component/user/UserTable.tsx` | TableContainer + Table + Chip |
| `component/user/UserSearchBar.tsx` | TextField + Button |
| `component/user/UserForm.tsx` | Dialog + TextField + Select + Checkbox |

## 注意事項

- API 層（`api/`）は変更しない。
- ビジネスロジック（useState/useEffect/コールバック）は変更しない。
- 既存の Props インターフェースは維持する。
- `npm run build` でビルドエラーがないことを確認する。

