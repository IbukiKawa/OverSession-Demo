#1 ビルド
FROM node:22-alpine AS builder

WORKDIR /app

COPY package*.json ./
RUN npm ci

# NEXT_PUBLIC_* はビルド時に bundle へ埋め込まれるため ARG/ENV で渡す
ARG NEXT_PUBLIC_DATA_SOURCE=api
ARG NEXT_PUBLIC_API_BASE_URL
ARG NEXT_PUBLIC_CURRENT_USER_ID

ENV NEXT_PUBLIC_DATA_SOURCE=$NEXT_PUBLIC_DATA_SOURCE
ENV NEXT_PUBLIC_API_BASE_URL=$NEXT_PUBLIC_API_BASE_URL
ENV NEXT_PUBLIC_CURRENT_USER_ID=$NEXT_PUBLIC_CURRENT_USER_ID

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