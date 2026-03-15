# AWS CLI / SSM Plugin インストール手順書

> 最終更新: 2026-03-15

---

## 目次

1. [事前準備](#1-事前準備)
2. [AWS CLI のインストール](#2-aws-cli-のインストール)
3. [AWS CLI の初期設定](#3-aws-cli-の初期設定)
4. [Session Manager Plugin のインストール](#4-session-manager-plugin-のインストール)
5. [動作確認](#5-動作確認)

---

## 1. 事前準備

作業を開始する前に以下が揃っていることを確認してください。

- [ ] [スイッチロール手順書](./スイッチロール手順書)を完了していること（MFA有効化済み）
- [ ] IAMユーザーのアクセスキー（CSVファイル）を手元に用意していること

---

## 2. AWS CLI のインストール

OS別の公式インストール手順に従ってください。

### macOS

📖 [macOS への AWS CLI のインストール - AWS公式](https://docs.aws.amazon.com/ja_jp/cli/latest/userguide/getting-started-install.html#getting-started-install-instructions)

公式ページの手順に従い pkg インストーラーを使うのが最も簡単です。Homebrew を使う場合は以下のコマンドでインストールできます。

```bash
brew install awscli
```

### Windows

📖 [Windows への AWS CLI のインストール - AWS公式](https://docs.aws.amazon.com/ja_jp/cli/latest/userguide/getting-started-install.html#getting-started-install-instructions)

公式ページから MSI インストーラーをダウンロードして実行してください。

### Linux

📖 [Linux への AWS CLI のインストール - AWS公式](https://docs.aws.amazon.com/ja_jp/cli/latest/userguide/getting-started-install.html#getting-started-install-instructions)

```bash
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install
```

### インストール確認

```bash
aws --version
# 例: aws-cli/2.x.x Python/3.x.x ...
```

---

## 3. AWS CLI の初期設定

### 3-1. 認証情報の設定

`~/.aws/credentials` と `~/.aws/config` を設定します。以下のコマンドを実行してください。

```bash
aws configure --profile myapp-dev-user
```

対話形式で以下を入力します。

| 項目 | 値 |
|------|-----|
| AWS Access Key ID | CSVに記載されているアクセスキーID |
| AWS Secret Access Key | CSVに記載されているシークレットアクセスキー |
| Default region name | `ap-northeast-1` |
| Default output format | `json` |

### 3-2. スイッチロール用プロファイルの設定

`~/.aws/config` に以下を追記します。

```ini
[profile myapp-dev]
role_arn       = arn:aws:iam::202516976897:role/myapp-dev-role
source_profile = myapp-dev-user
region         = ap-northeast-1
mfa_serial     = arn:aws:iam::202516976897:mfa/自分のユーザー名
```

> ⚠️ `mfa_serial` はMFAを有効化した際に発行された仮想デバイスのARNです。IAM → ユーザー → セキュリティ認証情報タブ → 「多要素認証（MFA）」の欄で確認できます。

> 💡 `自分のユーザー名` の部分は実際のIAMユーザー名に置き換えてください（例: `01-kawagishi`）。

---

## 4. Session Manager Plugin のインストール

ECS Exec（Bastionコンテナへのログイン）やSSMポートフォワード（RDS接続）を行うために必要です。

📖 [Session Manager プラグインのインストール - AWS公式](https://docs.aws.amazon.com/ja_jp/systems-manager/latest/userguide/session-manager-working-with-install-plugin.html)

### macOS

```bash
curl "https://s3.amazonaws.com/session-manager-downloads/plugin/latest/mac/sessionmanager-bundle.zip" -o "sessionmanager-bundle.zip"
unzip sessionmanager-bundle.zip
sudo ./sessionmanager-bundle/install -i /usr/local/sessionmanagerplugin -b /usr/local/bin/session-manager-plugin
```

Homebrew を使う場合は以下でインストールできます。

```bash
brew install --cask session-manager-plugin
```

### Windows

📖 [Windows への Session Manager プラグインのインストール - AWS公式](https://docs.aws.amazon.com/ja_jp/systems-manager/latest/userguide/session-manager-working-with-install-plugin.html#install-plugin-windows)

公式ページからインストーラーをダウンロードして実行してください。

### Linux

```bash
curl "https://s3.amazonaws.com/session-manager-downloads/plugin/latest/ubuntu_64bit/session-manager-plugin.deb" -o "session-manager-plugin.deb"
sudo dpkg -i session-manager-plugin.deb
```

### インストール確認

```bash
session-manager-plugin --version
# 例: 1.2.x.0
```

---

## 5. 動作確認

### 5-1. スイッチロールの確認

以下のコマンドを実行し、`myapp-dev-role` にスイッチできているか確認します。
MFAコードの入力を求められるので、Google Authenticatorに表示されている6桁のコードを入力してください。

```bash
aws sts get-caller-identity --profile myapp-dev
```

以下のようにロールARNが表示されれば成功です。

```json
{
    "UserId": "XXXXXXXXXXXXXXXXXXXXX:botocore-session-XXXXXXXXXX",
    "Account": "202516976897",
    "Arn": "arn:aws:sts::202516976897:assumed-role/myapp-dev-role/botocore-session-XXXXXXXXXX"
}
```

### 5-2. デフォルトプロファイルの設定（任意）

毎回 `--profile` を指定するのが面倒な場合は環境変数で設定できます。

```bash
export AWS_PROFILE=myapp-dev
```

`.zshrc` や `.bashrc` に追記しておくと起動時に自動で設定されます。

```bash
echo 'export AWS_PROFILE=myapp-dev' >> ~/.zshrc
source ~/.zshrc
```

---

## 参考リンク

- [AWS CLI インストールと設定 - AWS公式](https://docs.aws.amazon.com/ja_jp/cli/latest/userguide/getting-started-install.html)
- [AWS CLI 名前付きプロファイル - AWS公式](https://docs.aws.amazon.com/ja_jp/cli/latest/userguide/cli-configure-files.html)
- [Session Manager プラグインのインストール - AWS公式](https://docs.aws.amazon.com/ja_jp/systems-manager/latest/userguide/session-manager-working-with-install-plugin.html)