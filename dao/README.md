Javaバックエンド向け DAO README

概要:
このディレクトリはサーバーサイド（Java）で使うDAO (Data Access Object) の雛形、実装、テストを置きます。

目的:
- 永続化（DB）に依存するロジックを切り出してテスト／差し替えを容易にする

パッケージ構成例:
- `src/main/java/com/oversession/model` — エンティティ（POJO）
- `src/main/java/com/oversession/dao` — DAOのインターフェースと実装
- `src/test/java/com/oversession/dao` — 単体テスト

推奨インターフェース（例）:

```java
package com.oversession.dao;

import com.oversession.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findById(String id);
    List<User> findAll();
    void save(User user);
    void update(User user);
    void deleteById(String id);
}
```

簡易実装（テスト用） — InMemory:

```java
public class InMemoryUserDao implements UserDao {
    // ConcurrentHashMap 等を使ってメモリ上に保存する実装
}
```

テスト:
- JUnit 5 を使った単体テストを `src/test/java/...` に置いてください。
- 例: `InMemoryUserDaoTest` は `save/find/update/delete` の基本動作を検証します。

ビルド／テスト実行（プロジェクトが Maven の場合）:

```bash
mvn -q test
```

または Gradle の場合:

```bash
./gradlew test
```

次の推奨ステップ:
- 既存のビルドツール（Maven or Gradle）に合わせて `pom.xml` または `build.gradle` を用意する
- 実環境向けの実装（JPA/ JDBC / MyBatis 等）を `implementation` パッケージとして追加する
- 作成したソースをコミットしてプッシュする（必要なら私がコミットします）
