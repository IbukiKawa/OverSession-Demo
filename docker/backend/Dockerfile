#1 Mavenを使ってbuild。jarの作成
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Maven Wrapperのインストール
RUN apk add --no-cache maven

#Mavenの設定ファイルをコピー
COPY pom.xml ./

#依存パッケージのダウンロード
RUN mvn dependency:go-offline -B

#ソースコードをコピーしてビルド
COPY backend/src ./backend/src
RUN mvn package -DskipTests -B

#2 buildしたファイルをJREで実行
FROM eclipse-temurin:21-jre-alpine AS production

WORKDIR /app

RUN addgroup -g 1001 appuser && \
    adduser -u 1001 -G appuser -s /bin/sh -D appuser

#buildしたjarだけコピー
COPY --from=builder /app/target/*.jar app.jar

RUN chown -R appuser:appuser /app
USER appuser

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
