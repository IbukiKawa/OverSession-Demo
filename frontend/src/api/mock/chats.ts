import type {
  ChatSummary,
  Message,
  ReactionType,
  SendMessageRequest,
  SendReactionRequest,
} from '@/api/types';
import { getStorage, setStorage } from './storage';

const CHATS_KEY = 'mock_chats';
const MESSAGES_KEY = 'mock_messages';
const MSG_ID_COUNTER_KEY = 'mock_msg_id_counter';

export const MOCK_CURRENT_USER_ID = 'user01';

// Helper to build a past ISO timestamp
function past(offsetMinutes: number): string {
  return new Date(Date.now() - offsetMinutes * 60 * 1000).toISOString();
}

const INITIAL_CHATS: ChatSummary[] = [
  {
    chatId: 'chat01',
    partnerUserId: 'user02',
    partnerUserName: '鈴木 花子',
    partnerPictureName: 'https://i.pravatar.cc/150?u=user02',
    lastMessage: '明日もよろしくお願いします！',
    lastMessageAt: past(9),
    unreadCount: 2,
  },
  {
    chatId: 'chat02',
    partnerUserId: 'user03',
    partnerUserName: '山田 健一',
    partnerPictureName: 'https://i.pravatar.cc/150?u=user03',
    lastMessage: 'ありがとうございました',
    lastMessageAt: past(60 * 24),
    unreadCount: 0,
  },
];

const INITIAL_MESSAGES: Message[] = [
  // --- chat01: 古い履歴（無限スクロールデモ用） ---
  {
    messageId: 'msg000',
    chatId: 'chat01',
    senderUserId: MOCK_CURRENT_USER_ID,
    text: '先日はありがとうございました！',
    sentAt: past(60 * 2 + 30),
    readAt: past(60 * 2 + 15), // 既読
  },
  {
    messageId: 'msg001',
    chatId: 'chat01',
    senderUserId: 'user02',
    text: 'こちらこそです。また話しましょう。',
    sentAt: past(60 * 2 + 20),
    readAt: past(60 * 2 + 10), // 既読（user01が読んだ）
  },
  // --- chat01: 最近のやりとり ---
  {
    messageId: 'msg002',
    chatId: 'chat01',
    senderUserId: MOCK_CURRENT_USER_ID,
    text: 'こんにちは！マッチングしましたね。',
    sentAt: past(30),
    readAt: past(28), // 既読（user02が読んだ）
  },
  {
    messageId: 'msg003',
    chatId: 'chat01',
    senderUserId: 'user02',
    text: 'こんにちは！よろしくお願いします。',
    sentAt: past(25),
    readAt: past(24), // 既読（user01が読んだ）
  },
  {
    messageId: 'msg004',
    chatId: 'chat01',
    senderUserId: MOCK_CURRENT_USER_ID,
    text: '今日ランチでもどうですか？',
    sentAt: past(20),
    readAt: past(18), // 既読（user02が読んだ）
    reactions: [{ type: 1 as ReactionType, count: 1, reactedByMe: false }],
  },
  {
    messageId: 'msg005',
    chatId: 'chat01',
    senderUserId: 'user02',
    text: 'いいですね！12時でどうでしょう？',
    sentAt: past(15),
    readAt: past(14), // 既読（user01が読んだ）
  },
  {
    messageId: 'msg006',
    chatId: 'chat01',
    senderUserId: MOCK_CURRENT_USER_ID,
    text: '12時で大丈夫です。',
    sentAt: past(12),
    readAt: past(11), // 既読（user02が読んだ = 直後に返信してるため）
  },
  // --- 未読メッセージ（user02 → user01、unreadCount: 2 に対応） ---
  {
    messageId: 'msg007',
    chatId: 'chat01',
    senderUserId: 'user02',
    text: 'よろしくお願いします！',
    sentAt: past(10),
    readAt: null, // 未読
  },
  {
    messageId: 'msg007b',
    chatId: 'chat01',
    senderUserId: 'user02',
    text: '明日もよろしくお願いします！',
    sentAt: past(9),
    readAt: null, // 未読
  },
  // --- chat02 ---
  {
    messageId: 'msg008',
    chatId: 'chat02',
    senderUserId: 'user03',
    text: 'はじめまして！',
    sentAt: past(60 * 48),
    readAt: past(60 * 47 + 30), // 既読
  },
  {
    messageId: 'msg009',
    chatId: 'chat02',
    senderUserId: MOCK_CURRENT_USER_ID,
    text: 'はじめまして！よろしくお願いします。',
    sentAt: past(60 * 47),
    readAt: past(60 * 46), // 既読
  },
  {
    messageId: 'msg010',
    chatId: 'chat02',
    senderUserId: 'user03',
    text: 'ありがとうございました',
    sentAt: past(60 * 24),
    readAt: past(60 * 23 + 30), // 既読（user01が読んだ）
  },
];

function loadChats(): ChatSummary[] {
  const stored = getStorage<ChatSummary[] | null>(CHATS_KEY, null);
  if (stored === null) {
    setStorage(CHATS_KEY, INITIAL_CHATS);
    return INITIAL_CHATS;
  }
  return stored;
}

function loadMessages(): Message[] {
  const stored = getStorage<Message[] | null>(MESSAGES_KEY, null);
  if (stored === null) {
    setStorage(MESSAGES_KEY, INITIAL_MESSAGES);
    return INITIAL_MESSAGES;
  }
  return stored;
}

function nextMsgId(): string {
  const counter = getStorage<number>(MSG_ID_COUNTER_KEY, 11);
  const id = `msg${String(counter).padStart(3, '0')}`;
  setStorage(MSG_ID_COUNTER_KEY, counter + 1);
  return id;
}

function delay(ms: number): Promise<void> {
  return new Promise((resolve) => setTimeout(resolve, ms));
}

export async function mockGetChats(
  _userId: string
): Promise<{ chats: ChatSummary[] }> {
  await delay(300);
  return { chats: loadChats() };
}

export async function mockGetChatMessages(
  chatId: string,
  cursor?: string,
  limit = 20
): Promise<{ messages: Message[]; nextCursor: string | null }> {
  await delay(300);
  const all = loadMessages()
    .filter((m) => m.chatId === chatId)
    .sort((a, b) => new Date(a.sentAt).getTime() - new Date(b.sentAt).getTime());

  if (cursor) {
    const cursorIdx = all.findIndex((m) => m.messageId === cursor);
    if (cursorIdx <= 0) return { messages: [], nextCursor: null };
    const slice = all.slice(Math.max(0, cursorIdx - limit), cursorIdx);
    const nextCursor = cursorIdx - limit > 0 ? slice[0].messageId : null;
    return { messages: slice, nextCursor };
  }

  const slice = all.slice(Math.max(0, all.length - limit));
  const nextCursor = all.length > limit ? slice[0].messageId : null;
  return { messages: slice, nextCursor };
}

/** チャットを開いたとき、相手から届いたメッセージを既読にする */
export async function mockMarkMessagesRead(
  chatId: string,
  currentUserId: string
): Promise<void> {
  await delay(100);
  const messages = loadMessages();
  const now = new Date().toISOString();
  let changed = false;

  messages.forEach((m) => {
    if (m.chatId === chatId && m.senderUserId !== currentUserId && m.readAt === null) {
      m.readAt = now;
      changed = true;
    }
  });

  if (changed) {
    setStorage(MESSAGES_KEY, messages);

    // unreadCount をクリア
    const chats = loadChats();
    const chatIdx = chats.findIndex((c) => c.chatId === chatId);
    if (chatIdx !== -1 && (chats[chatIdx].unreadCount ?? 0) > 0) {
      chats[chatIdx].unreadCount = 0;
      setStorage(CHATS_KEY, chats);
    }
  }
}

export async function mockSendMessage(
  req: SendMessageRequest
): Promise<{ messageId: string; sentAt: string }> {
  await delay(300);
  const messages = loadMessages();
  const chats = loadChats();

  const messageId = nextMsgId();
  const sentAt = new Date().toISOString();

  messages.push({
    messageId,
    chatId: req.chatId,
    senderUserId: req.senderUserId,
    text: req.text,
    sentAt,
    readAt: null, // 送信直後は未読
  });
  setStorage(MESSAGES_KEY, messages);

  const chatIdx = chats.findIndex((c) => c.chatId === req.chatId);
  if (chatIdx !== -1) {
    chats[chatIdx].lastMessage = req.text;
    chats[chatIdx].lastMessageAt = sentAt;
    setStorage(CHATS_KEY, chats);
  }

  return { messageId, sentAt };
}

export async function mockSendReaction(req: SendReactionRequest): Promise<void> {
  await delay(200);
  const messages = loadMessages();
  const idx = messages.findIndex((m) => m.messageId === req.messageId);
  if (idx === -1) return;

  const msg = { ...messages[idx], reactions: [...(messages[idx].reactions ?? [])] };
  const reactionIdx = msg.reactions.findIndex((r) => r.type === req.type);

  if (reactionIdx !== -1) {
    const r = { ...msg.reactions[reactionIdx] };
    if (r.reactedByMe) {
      r.count = Math.max(0, r.count - 1);
      r.reactedByMe = false;
      if (r.count === 0) {
        msg.reactions.splice(reactionIdx, 1);
      } else {
        msg.reactions[reactionIdx] = r;
      }
    } else {
      msg.reactions[reactionIdx] = { ...r, count: r.count + 1, reactedByMe: true };
    }
  } else {
    msg.reactions.push({ type: req.type, count: 1, reactedByMe: true });
  }

  messages[idx] = msg;
  setStorage(MESSAGES_KEY, messages);
}
