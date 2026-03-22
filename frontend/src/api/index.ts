/**
 * API entry point.
 * Screens import everything from here.
 * NEXT_PUBLIC_DATA_SOURCE=mock (default) → mock implementation
 * NEXT_PUBLIC_DATA_SOURCE=api           → real API
 */
import type {
  User,
  RegisterUserRequest,
  UpdateUserRequest,
  ChatSummary,
  Message,
  SendMessageRequest,
  SendReactionRequest,
} from './types';

import {
  mockGetUsers,
  mockRegisterUser,
  mockUpdateUser,
  mockSearchUsers,
  MOCK_CURRENT_USER_ID,
} from './mock/users';
import {
  mockGetChats,
  mockGetChatMessages,
  mockMarkMessagesRead,
  mockSendMessage,
  mockSendReaction,
} from './mock/chats';
import {
  clientGetUsers,
  clientSearchUsers,
  clientRegisterUser,
  clientUpdateUser,
  clientDeleteUser,
  clientGetChats,
  clientGetChatMessages,
  clientMarkMessagesRead,
  clientSendMessage,
  clientSendReaction,
} from './client/fetcher';

const USE_MOCK = process.env.NEXT_PUBLIC_DATA_SOURCE !== 'api';

/** The current logged-in user ID (mock: fixed, real API: from NEXT_PUBLIC_CURRENT_USER_ID) */
export const CURRENT_USER_ID: string = USE_MOCK
  ? MOCK_CURRENT_USER_ID
  : (process.env.NEXT_PUBLIC_CURRENT_USER_ID ?? '');

// -------------------- User --------------------
export function getUsers(userId?: string): Promise<User[]> {
  return USE_MOCK ? mockGetUsers(userId) : clientGetUsers(userId);
}

export function searchUsers(keyword: string): Promise<User[]> {
  return USE_MOCK ? mockSearchUsers(keyword) : clientSearchUsers(keyword);
}

export function registerUser(
  req: RegisterUserRequest
): Promise<{ userId: string }> {
  return USE_MOCK ? mockRegisterUser(req) : clientRegisterUser(req);
}

export function updateUser(req: UpdateUserRequest): Promise<void> {
  return USE_MOCK ? mockUpdateUser(req) : clientUpdateUser(req);
}

export function deleteUser(userId: string): Promise<void> {
  if (USE_MOCK) {
    // モックでは UpdateUser の deleted フラグを使う
    return mockUpdateUser({
      userId,
      userName: '',
      workingStatus: '不在',
      deleted: true,
    });
  }
  return clientDeleteUser(userId);
}

// -------------------- Chat --------------------
// バックエンドは ChatSummary[] を返す。{ chats: [...] } にラップして返す。
export async function getChats(userId: string): Promise<{ chats: ChatSummary[] }> {
  if (USE_MOCK) return mockGetChats(userId);
  const chats = await clientGetChats(userId);
  return { chats };
}

export function getChatMessages(
  chatId: string,
  cursor?: string,
  limit?: number
): Promise<{ messages: Message[]; nextCursor: string | null }> {
  return USE_MOCK
    ? mockGetChatMessages(chatId, cursor, limit)
    : clientGetChatMessages(chatId, cursor, limit);
}

export function sendMessage(
  req: SendMessageRequest
): Promise<{ messageId: string; sentAt: string }> {
  return USE_MOCK ? mockSendMessage(req) : clientSendMessage(req);
}

export function sendReaction(req: SendReactionRequest): Promise<void> {
  return USE_MOCK ? mockSendReaction(req) : clientSendReaction(req);
}

/**
 * チャット内の未読メッセージを既読にする。
 * messageIds: 既読にするメッセージID一覧（実APIでは個別にPUTを呼ぶ）
 */
export function markMessagesRead(
  chatId: string,
  userId: string,
  messageIds?: string[]
): Promise<void> {
  if (USE_MOCK) return mockMarkMessagesRead(chatId, userId);
  return clientMarkMessagesRead(chatId, userId, messageIds ?? []);
}

// -------------------- Image URL --------------------
/**
 * pictureName から表示用画像URLを構築する。
 * - モック: pictureName はフルURL → そのまま返す
 * - 実API: pictureName は S3オブジェクトキー → CloudFront URL を付加する
 */
export function getPictureUrl(pictureName?: string | null): string | undefined {
  if (!pictureName) return undefined;
  if (pictureName.startsWith('http://') || pictureName.startsWith('https://')) {
    return pictureName;
  }
  const cfUrl = process.env.NEXT_PUBLIC_CLOUDFRONT_URL ?? '';
  if (!cfUrl) return undefined;
  return `${cfUrl.replace(/\/$/, '')}/${pictureName}`;
}
