# 抽出サマリ（Excel → テキスト化）

## 1) ユーザマスタ API（API設計書_登録マスタ_v2.xlsx）

### POST ユーザ登録

- method: **POST**
- url: `/api/post/user`

**Headers**

|name|required|type|description|
|---|---|---|---|
|Content-Type|True|string|application/json|

**Request body**

|name|required|type|description|
|---|---|---|---|
|userName|True|string|None|
|primaryHeadOfficeName|False|string|None|
|secondaryHeadOfficeName|False|string|None|
|departmentName|False|string|None|
|officeId|False|int|None|
|floor|False|int|None|
|gender|False|string|None|
|affiliationYear|False|int|None|
|workingStatus|True|string|None|
|matchingUserId|False|string|None|
|userImageUrl|False|string|None|

**Responses**

- 201

|name|required|type|description|
|---|---|---|---|
|userId|True|string|None|

  - nested (errors[])

|name|required|type|description|
|---|---|---|---|
|code|True|string|エラーコード|
|msg|True|string|エラーメッセージ|

- 400

|name|required|type|description|
|---|---|---|---|
|statusCode|True|integer|ステータスコード|
|reason|True|string|理由|
|errors|True|Array|None|

  - nested (errors[])

|name|required|type|description|
|---|---|---|---|
|code|True|string|エラーコード|
|msg|True|string|エラーメッセージ|

### PUT ユーザ更新

- method: **PUT**
- url: `/api/put/user`

**Headers**

|name|required|type|description|
|---|---|---|---|
|Content-Type|True|string|application/json|

**Request body**

|name|required|type|description|
|---|---|---|---|
|userId|True|string|None|
|userName|True|string|None|
|primaryHeadOfficeName|False|string|None|
|secondaryHeadOfficeName|False|string|None|
|departmentName|False|string|None|
|officeId|False|int|None|
|floor|False|int|None|
|gender|False|string|None|
|affiliationYear|False|int|None|
|workingStatus|True|string|None|
|matchingUserId|False|string|None|
|userImageUrl|False|string|None|
|deleted|True|boolean|None|

**Responses**

- 204

|name|required|type|description|
|---|---|---|---|

- 400

|name|required|type|description|
|---|---|---|---|
|statusCode|True|integer|ステータスコード|
|reason|True|string|理由|
|errors|True|Array|None|

  - nested (errors[])

|name|required|type|description|
|---|---|---|---|
|code|True|string|エラーコード|
|msg|True|string|エラーメッセージ|

### GET ユーザ検索

- method: **GET**
- url: `/api/get/user`

**Headers**

|name|required|type|description|
|---|---|---|---|
|Content-Type|True|string|application/json|

**Query**

|name|required|type|description|
|---|---|---|---|
|userId|False|string|nullの場合は全件取得|

**Responses**

- 200

|name|required|type|description|
|---|---|---|---|
|userId|True|string|None|
|userName|True|string|None|
|primaryHeadOfficeName|False|string|None|
|secondaryHeadOfficeName|False|string|None|
|departmentName|False|string|None|
|officeId|False|int|None|
|floor|False|int|None|
|gender|False|string|None|
|affiliationYear|False|int|None|
|workingStatus|True|string|None|
|matchingUserId|False|string|None|
|pictureName|False|string|None|
|deleted|True|boolean|None|

  - nested (errors[])

|name|required|type|description|
|---|---|---|---|
|code|True|string|エラーコード|
|msg|True|string|エラーメッセージ|

- 400

|name|required|type|description|
|---|---|---|---|
|statusCode|True|integer|ステータスコード|
|reason|True|string|理由|
|errors|True|Array|None|

  - nested (errors[])

|name|required|type|description|
|---|---|---|---|
|code|True|string|エラーコード|
|msg|True|string|エラーメッセージ|


## 2) チャット一覧API（API設計書_チャット一覧.xlsx）

※設計書はテンプレ状態。確定しているのはヘッダーとエラー形式のみ。

### テンプレ（更新シート）

- method: **(未確定)**
- url: `/api/put/aaaaa`

**Headers**

|name|required|type|description|
|---|---|---|---|
|Content-Type|True|string|application/json|
|Authorization|True|string|ログイン時のJWT|

**Responses**

- 204

|name|required|type|description|
|---|---|---|---|

- 400

|name|required|type|description|
|---|---|---|---|
|statusCode|True|integer|ステータスコード|
|reason|True|string|理由|
|errors|True|Array|None|

  - nested (errors[])

|name|required|type|description|
|---|---|---|---|
|code|True|string|エラーコード|
|msg|True|string|エラーメッセージ|


## 3) DBテーブル定義（ユーザ情報_テーブル定義書.xlsx）

### ユーザ (user)

- logical: ユーザ

|No.|論理名|物理名|データ型|Not Null|
|---|---|---|---|---|
|1|ユーザID|user_id|character varying(6)|Yes (PK)|
|2|ユーザ名|user_name|character varying(100)|Yes |
|3|本部名1|primary_head_office_name|character varying(100)||
|4|本部名2|secondary_head_office_name|character varying(100)||
|5|部署名|department_name|character varying(100)||
|6|オフィスID|office_id|int||
|7|所属階|floor|int||
|8|性別|gender|character varying(20)||
|9|在籍年数|affiliation_year|int||
|10|出社ステータス|working_status|character varying(30)|Yes|
|11|マッチングユーザID|matching_user_id|character varying(6)||
|12|ユーザ画像名|picture_name|character varying(100)||

### マッチング (matching)

- logical: マッチング

|No.|論理名|物理名|データ型|Not Null|
|---|---|---|---|---|
|1|日付|date|date|Yes (PK)|
|2|ユーザID|user_id|int|Yes (PK)|
|3|マッチングユーザID|matching_user_id|int|Yes|

### オフィス (office)

- logical: オフィス

- notes: 自販機がある拠点のみ

|No.|論理名|物理名|データ型|Not Null|
|---|---|---|---|---|
|1|オフィスID|office_id|int|Yes (PK)|
|2|オフィス名|office_name|character varying(30)|Yes|


## 4) 画面設計：個別チャット（画面設計書_個別チャット.xls → xlsx変換）

### 項目定義

|No|項目名|項目種別|表示書式|型|必須|最小|最大|列挙|初期値|
|---|---|---|---|---|---|---|---|---|---|
|1|日付ヘッダー|ラベル|M/d(aaa)|date|-|-|-|-|システム|
|2|相手名|ラベル|-|string|-|-|50|-|-|
|3|自分が送信したメッセージ|ラベル(可変)|-|string|-|1|2000|-|-|
|4|相手が送信したメッセージ|ラベル(可変)|-|string|-|1|2000|-|-|
|5|送信時刻|ラベル|HH:mm|dateTIme|-|-|-|-|-|
|6|相手アイコン|画像|-|Image|-|-|-|-|デフォルト|
|7|エラーメッセージ|ラベル|-|string|-|-|-|-|非表示|
|8|メッセージ入力欄|テキストエリア|赤文字|String|必須|1|1000|-|(メッセージを入力してください)|
|9|送信ボタン|ボタン|-|-|-|-|-|-|-|
|10|戻るボタン|ボタン|-|-|-|-|-|-|-|
|11|リアクションボタン|ボタン|-|number|-|-|-|1:Smile, 2:Good, 3:Like, 4: Sad |非表示|
|12|拒否ボタン|ボタン|-|||||||

### アクション定義

|項目No|項目名|項目種別|アクション定義|
|---|---|---|---|
|1|送信ボタン|ボタン|クリック時：入力値のバリデーション、メッセージ送信APIを呼び出す。成功時に個別メッセージ画面の最下部にメッセージを追加する。失敗時に画面下部にエラーメッセージを表示する|
|2|戻るボタン|ボタン|クリック時：遷移元の画面へ戻る|
|3|相手が送信したメッセージ|ラベル|長押し時：リアクションボタンのタブを表示する|
|4|リアクションボタン|ボタン|クリック時：リアクション送信APIを呼び出す。成功時にメッセージ下部にリアクションマークを追加する。失敗時に画面下部にエラーメッセージを表示する|
|5|メッセージ入力欄|テキストエリア|入力変更時：入力文字数をカウントする。最大文字数超過の場合、送信ボタンを非活性にする。|
|6|相手アイコン|画像|クリック時：相手のプロフィールを表示する|
|7|メッセージリスト|画面|スクロール時：スクロール位置が最上部に達したら、過去ログ取得用のAPIを追加で呼び出す。取得した過去ログをリスト上部に結合して表示する|
|8|画面全体|画面|メッセージ履歴取得APIを呼び出す。取得データを時系列順に表示し、最下部へスクロールする。通信失敗時はポップアップ表示等で通知する。|

### 初期表示

|No|概要|詳細|
|---|---|---|
|1|画面描画準備|ローディングインジケータを表示し、ユーザー操作を一時的にブロック|
|2|チャット履歴取得|非同期でサーバーからチャットメッセージ履歴を取得する。|
|3|メッセージリストの作成|取得した履歴データを時系列順に展開して表示する。送信者IDが自分なら右寄せ、相手なら左寄せにする。|
|4|スクロール|メッセージ表示後、自動的に画面最下部までスクロールする|
|5|入力欄の初期化|メッセージ入力欄を空にする。保存された下書き等があればセットする。|
|6|エラーハンドリング|通信失敗時はローディングを中止し、エラーメッセージを表示欄に出す。|
