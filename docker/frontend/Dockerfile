#1 ビルド
FROM node:22-alpine AS builder

WORKDIR /app

COPY package*.json ./
RUN npm ci

COPY . .
RUN npm run build

#本番
FROM node:22-alpine AS production

WORKDIR /app

RUN addgroup -g 1001 nodejs && \
    adduser -u 1001 -G nodejs -s /bin/sh -D nodejs

#Next.jsのスタンドアロン出力をコピー
COPY --from=builder /app/.next/standalone ./
COPY --from=builder /app/.next/static ./.next/static
COPY --from=builder /app/public ./public

RUN chown -R nodejs:nodejs /app
USER nodejs

EXPOSE 3000

CMD ["node", "server.js"]