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
  clientRegisterUser,
  clientUpdateUser,
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

export function registerUser(
  req: RegisterUserRequest
): Promise<{ userId: string }> {
  return USE_MOCK ? mockRegisterUser(req) : clientRegisterUser(req);
}

export function updateUser(req: UpdateUserRequest): Promise<void> {
  return USE_MOCK ? mockUpdateUser(req) : clientUpdateUser(req);
}

// -------------------- Chat --------------------
export function getChats(userId: string): Promise<{ chats: ChatSummary[] }> {
  return USE_MOCK ? mockGetChats(userId) : clientGetChats(userId);
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

export function markMessagesRead(chatId: string, userId: string): Promise<void> {
  return USE_MOCK
    ? mockMarkMessagesRead(chatId, userId)
    : clientMarkMessagesRead(chatId, userId);
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
