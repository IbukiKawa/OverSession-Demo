#1 gradleを使ってbuild。jarの作成
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app
#gradleの設定ファイルをコピー
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY gradlew ./
RUN chmod +x gradlew
#依存パッケージのダウンロード
RUN ./gradlew dependencies --no-daemon

#ソースコードをコピーしてビルド
COPY src ./src
RUN ./gradlew bootJar --no-daemon

#2 buildしたファイルをJREで実行
FROM eclipse-temurin:21-jre-alpine AS production

WORKDIR /app

RUN addgroup -g 1001 appuser && \
    adduser -u 1001 -G appuser -s /bin/sh -D appuser

#buildしたjarだけコピー
COPY --from=builder /app/build/libs/*.jar app.jar

RUN chown -R appuser:appuser /app
USER appuser

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]