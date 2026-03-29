package com.oversession.controller;

import com.oversession.model.ChatSummary;
import com.oversession.model.Message;
import com.oversession.model.MessageReaction;
import com.oversession.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Chat REST コントローラー
 */
@RestController
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    // ==================================================================
    // TODO: DB接続後にこのセクションをすべて削除する（ダミーデータ）
    // ==================================================================

    /** チャット参加者: chatId → [userId1, userId2] （DB接続後に削除） */
    private static final Map<String, List<String>> DUMMY_CHAT_PARTICIPANTS = new LinkedHashMap<>();

    /** ユーザ表示名: userId → [userName, pictureName] （DB接続後に削除） */
    private static final Map<String, String[]> DUMMY_USER_INFO = new HashMap<>();

    /** メッセージ一覧（DB接続後に削除） */
    private static final List<Message> DUMMY_MESSAGES = new ArrayList<>();

    /**
     * リアクション管理: messageId → reactionType → Set<userId>
     * リアクションの追加/削除はこのMapを更新し、メッセージの reactions フィールドに反映する
     * （DB接続後に削除）
     */
    private static final Map<String, Map<Integer, Set<String>>> DUMMY_REACTION_STORE = new HashMap<>();

    private static final AtomicInteger DUMMY_MSG_ID_COUNTER  = new AtomicInteger(20);
    private static final AtomicInteger DUMMY_CHAT_ID_COUNTER = new AtomicInteger(3);

    // ダミーデータの初期化（DB接続後に削除）
    static {
        // ── ユーザ情報 ──────────────────────────────────────────
        DUMMY_USER_INFO.put("user01", new String[]{"田中 太郎", null});
        DUMMY_USER_INFO.put("user02", new String[]{"鈴木 花子", null});
        DUMMY_USER_INFO.put("user03", new String[]{"山田 健一", null});

        // ── チャット参加者 ─────────────────────────────────────
        DUMMY_CHAT_PARTICIPANTS.put("chat01", Arrays.asList("user01", "user02"));
        DUMMY_CHAT_PARTICIPANTS.put("chat02", Arrays.asList("user01", "user03"));

        // ── メッセージ ─────────────────────────────────────────
        LocalDateTime now = LocalDateTime.now();

        // chat01
        addDummyMessage("msg001", "chat01", "user01", "こんにちは！マッチングしましたね。",
                now.minusMinutes(30), now.minusMinutes(28));
        addDummyMessage("msg002", "chat01", "user02", "こんにちは！よろしくお願いします。",
                now.minusMinutes(25), now.minusMinutes(24));
        addDummyMessage("msg003", "chat01", "user01", "今日ランチでもどうですか？",
                now.minusMinutes(20), now.minusMinutes(18));
        addDummyMessage("msg004", "chat01", "user02", "いいですね！12時でどうでしょう？",
                now.minusMinutes(15), now.minusMinutes(14));
        addDummyMessage("msg005", "chat01", "user01", "12時で大丈夫です。",
                now.minusMinutes(12), now.minusMinutes(11));
        addDummyMessage("msg006", "chat01", "user02", "よろしくお願いします！",
                now.minusMinutes(10), null);  // 未読
        addDummyMessage("msg007", "chat01", "user02", "明日もよろしくお願いします！",
                now.minusMinutes(9),  null);  // 未読

        // chat02
        addDummyMessage("msg008", "chat02", "user03", "はじめまして！",
                now.minusHours(48), now.minusHours(47).minusMinutes(30));
        addDummyMessage("msg009", "chat02", "user01", "はじめまして！よろしくお願いします。",
                now.minusHours(47), now.minusHours(46));
        addDummyMessage("msg010", "chat02", "user03", "ありがとうございました",
                now.minusHours(24), now.minusHours(23).minusMinutes(30));

        // ── リアクション（msg003 に user02 が type=1 でリアクション）─
        applyDummyReaction("msg003", "user02", 1);
    }

    /** ダミーメッセージ生成＆リスト追加（DB接続後に削除） */
    private static void addDummyMessage(String messageId, String chatId,
                                        String senderUserId, String text,
                                        LocalDateTime sentAt, LocalDateTime readAt) {
        Message m = new Message(messageId, chatId, senderUserId, text, sentAt);
        m.setReadAt(readAt);
        DUMMY_MESSAGES.add(m);
    }

    /** ダミーリアクションを REACTION_STORE に追加してメッセージに反映（DB接続後に削除） */
    private static void applyDummyReaction(String messageId, String userId, int reactionType) {
        DUMMY_REACTION_STORE
                .computeIfAbsent(messageId, k -> new HashMap<>())
                .computeIfAbsent(reactionType, k -> new HashSet<>())
                .add(userId);
        refreshMessageReactions(messageId);
    }

    /**
     * REACTION_STORE の内容をメッセージの reactions フィールドに反映するヘルパー
     * （DB接続後に削除）
     */
    private static void refreshMessageReactions(String messageId) {
        DUMMY_MESSAGES.stream()
                .filter(m -> m.getMessageId().equals(messageId))
                .findFirst()
                .ifPresent(m -> {
                    Map<Integer, Set<String>> byType = DUMMY_REACTION_STORE.get(messageId);
                    if (byType == null || byType.isEmpty()) {
                        m.setReactions(new ArrayList<>());
                        return;
                    }
                    List<MessageReaction> reactions = byType.entrySet().stream()
                            .map(e -> new MessageReaction(e.getKey(), e.getValue().size()))
                            .sorted(Comparator.comparingInt(MessageReaction::getType))
                            .collect(Collectors.toList());
                    m.setReactions(reactions);
                });
    }

    // ==================================================================
    // TODO: ダミーデータここまで
    // ==================================================================


    /**
     * GET /api/chats?userId={userId}
     * ユーザーのチャット一覧取得
     */
    @GetMapping
    public List<ChatSummary> getChats(@RequestParam("userId") String userId) {
        // TODO: DB接続後、以下コメントアウトを解除し、ダミーデータのコードを削除する
        // return chatService.getUserChats(userId);

        // ↓ ダミーデータから取得（DB接続後に削除）
        List<ChatSummary> result = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : DUMMY_CHAT_PARTICIPANTS.entrySet()) {
            String chatId       = entry.getKey();
            List<String> members = entry.getValue();
            if (!members.contains(userId)) continue;

            // 相手ユーザを特定
            String partnerId = members.stream()
                    .filter(id -> !id.equals(userId))
                    .findFirst().orElse(null);
            if (partnerId == null) continue;

            String[] partnerInfo = DUMMY_USER_INFO.getOrDefault(
                    partnerId, new String[]{partnerId, null});

            // このチャットのメッセージを時系列順に取得
            List<Message> chatMessages = DUMMY_MESSAGES.stream()
                    .filter(m -> m.getChatId().equals(chatId))
                    .sorted(Comparator.comparing(Message::getSentAt))
                    .collect(Collectors.toList());

            Message lastMsg = chatMessages.isEmpty()
                    ? null : chatMessages.get(chatMessages.size() - 1);
            long unreadCount = chatMessages.stream()
                    .filter(m -> !m.getSenderUserId().equals(userId) && m.getReadAt() == null)
                    .count();

            ChatSummary summary = new ChatSummary(chatId, partnerId, partnerInfo[0]);
            summary.setPartnerPictureName(partnerInfo[1]);
            if (lastMsg != null) {
                summary.setLastMessage(lastMsg.getText());
                summary.setLastMessageAt(lastMsg.getSentAt());
            }
            summary.setUnreadCount((int) unreadCount);
            result.add(summary);
        }
        return result;
    }


    /**
     * GET /api/chats/{chatId}
     * 指定チャット取得
     */
    @GetMapping("/{chatId}")
    public ResponseEntity<ChatSummary> getChat(@PathVariable("chatId") String chatId) {
        // TODO: DB接続後、以下コメントアウトを解除し、ダミーデータのコードを削除する
        // return chatService.getChatById(chatId)
        //         .map(ResponseEntity::ok)
        //         .orElse(ResponseEntity.notFound().build());

        // ↓ ダミーデータから取得（DB接続後に削除）
        List<String> members = DUMMY_CHAT_PARTICIPANTS.get(chatId);
        if (members == null) return ResponseEntity.notFound().build();

        String userId1   = members.get(0);
        String partnerId = members.get(1);
        String[] partnerInfo = DUMMY_USER_INFO.getOrDefault(
                partnerId, new String[]{partnerId, null});

        ChatSummary summary = new ChatSummary(chatId, partnerId, partnerInfo[0]);
        summary.setPartnerPictureName(partnerInfo[1]);
        return ResponseEntity.ok(summary);
    }


    /**
     * POST /api/chats
     * チャット作成
     */
    @PostMapping
    public String createChat(@RequestBody Map<String, String> body) {
        // TODO: DB接続後、以下コメントアウトを解除し、ダミーデータのコードを削除する
        // return chatService.createChat(body.get("userId1"), body.get("userId2"));

        // ↓ ダミーデータにチャットを追加（DB接続後に削除）
        String userId1 = body.get("userId1");
        String userId2 = body.get("userId2");

        // 既存チャットを検索して重複を防ぐ
        for (Map.Entry<String, List<String>> entry : DUMMY_CHAT_PARTICIPANTS.entrySet()) {
            if (entry.getValue().contains(userId1) && entry.getValue().contains(userId2)) {
                return entry.getKey();
            }
        }

        String chatId = String.format("chat%02d", DUMMY_CHAT_ID_COUNTER.getAndIncrement());
        DUMMY_CHAT_PARTICIPANTS.put(chatId, Arrays.asList(userId1, userId2));
        return chatId;
    }


    /**
     * GET /api/chats/{chatId}/messages
     * チャットメッセージ一覧取得（カーソルベースページネーション）
     *
     * cursor: このメッセージIDより古いメッセージを取得する（省略時は最新から取得）
     * limit:  取得件数（デフォルト 20）
     */
    @GetMapping("/{chatId}/messages")
    public List<Message> getChatMessages(
            @PathVariable("chatId") String chatId,
            @RequestParam(name = "cursor", required = false) String cursor,
            @RequestParam(name = "limit", defaultValue = "20") int limit) {
        // TODO: DB接続後、以下コメントアウトを解除し、ダミーデータのコードを削除する
        // return chatService.getChatMessagesWithCursor(chatId, cursor, limit);

        // ↓ ダミーデータから取得（DB接続後に削除）
        List<Message> all = DUMMY_MESSAGES.stream()
                .filter(m -> m.getChatId().equals(chatId))
                .sorted(Comparator.comparing(Message::getSentAt))
                .collect(Collectors.toList());

        // cursor がある場合: そのメッセージより前の limit 件を返す（過去ログ用）
        int endIndex = all.size();
        if (cursor != null && !cursor.isEmpty()) {
            for (int i = 0; i < all.size(); i++) {
                if (all.get(i).getMessageId().equals(cursor)) {
                    endIndex = i;
                    break;
                }
            }
        }
        int startIndex = Math.max(0, endIndex - limit);
        return new ArrayList<>(all.subList(startIndex, endIndex));
    }


    /**
     * POST /api/chats/{chatId}/messages
     * メッセージ送信
     */
    @PostMapping("/{chatId}/messages")
    public String sendMessage(@PathVariable("chatId") String chatId, @RequestBody Map<String, String> body) {
        // TODO: DB接続後、以下コメントアウトを解除し、ダミーデータのコードを削除する
        // return chatService.sendMessage(chatId, body.get("senderUserId"), body.get("text"));

        // ↓ ダミーデータにメッセージを追加（DB接続後に削除）
        String messageId = String.format("msg%03d", DUMMY_MSG_ID_COUNTER.getAndIncrement());
        Message message  = new Message(
                messageId, chatId, body.get("senderUserId"), body.get("text"), LocalDateTime.now());
        DUMMY_MESSAGES.add(message);
        return messageId;
    }


    /**
     * POST /api/chats/{chatId}/messages/{messageId}/reactions
     * リアクション追加
     */
    @PostMapping("/{chatId}/messages/{messageId}/reactions")
    public void addReaction(
            @PathVariable("chatId") String chatId,
            @PathVariable("messageId") String messageId,
            @RequestBody Map<String, Object> body) {
        // TODO: DB接続後、以下コメントアウトを解除し、ダミーデータのコードを削除する
        // chatService.addReactionToMessage(
        //         messageId,
        //         (String) body.get("userId"),
        //         (Integer) body.get("reactionType"));

        // ↓ ダミーデータのリアクションを追加（DB接続後に削除）
        String  userId       = (String) body.get("userId");
        Integer reactionType = ((Number) body.get("reactionType")).intValue();

        DUMMY_REACTION_STORE
                .computeIfAbsent(messageId, k -> new HashMap<>())
                .computeIfAbsent(reactionType, k -> new HashSet<>())
                .add(userId);

        refreshMessageReactions(messageId);
    }


    /**
     * DELETE /api/chats/{chatId}/messages/{messageId}/reactions
     * リアクション削除
     */
    @DeleteMapping("/{chatId}/messages/{messageId}/reactions")
    public void removeReaction(
            @PathVariable("chatId") String chatId,
            @PathVariable("messageId") String messageId,
            @RequestBody Map<String, Object> body) {
        // TODO: DB接続後、以下コメントアウトを解除し、ダミーデータのコードを削除する
        // chatService.removeReactionFromMessage(
        //         messageId,
        //         (String) body.get("userId"),
        //         (Integer) body.get("reactionType"));

        // ↓ ダミーデータのリアクションを削除（DB接続後に削除）
        String  userId       = (String) body.get("userId");
        Integer reactionType = ((Number) body.get("reactionType")).intValue();

        Map<Integer, Set<String>> byType = DUMMY_REACTION_STORE.get(messageId);
        if (byType != null) {
            Set<String> users = byType.get(reactionType);
            if (users != null) {
                users.remove(userId);
                if (users.isEmpty()) {
                    byType.remove(reactionType);
                }
            }
        }

        refreshMessageReactions(messageId);
    }


    /**
     * PUT /api/chats/{chatId}/messages/{messageId}/read
     * 既読マーク
     */
    @PutMapping("/{chatId}/messages/{messageId}/read")
    public void markAsRead(
            @PathVariable("chatId") String chatId,
            @PathVariable("messageId") String messageId,
            @RequestBody Map<String, String> body) {
        // TODO: DB接続後、以下コメントアウトを解除し、ダミーデータのコードを削除する
        // chatService.markMessageAsRead(messageId, body.get("readerId"));

        // ↓ ダミーデータのメッセージを既読に更新（DB接続後に削除）
        DUMMY_MESSAGES.stream()
                .filter(m -> m.getMessageId().equals(messageId))
                .findFirst()
                .ifPresent(m -> m.setReadAt(LocalDateTime.now()));
    }
}
