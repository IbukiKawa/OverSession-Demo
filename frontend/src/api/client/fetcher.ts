import type {
  User,
  RegisterUserRequest,
  UpdateUserRequest,
  ChatSummary,
  Message,
  SendMessageRequest,
  SendReactionRequest,
  ApiError,
} from '@/api/types';

const BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL ?? '';

async function request<T>(
  method: string,
  path: string,
  options?: { body?: unknown; query?: Record<string, string> }
): Promise<T> {
  const url = new URL(`${BASE_URL}${path}`, window.location.origin);
  if (options?.query) {
    Object.entries(options.query).forEach(([k, v]) => url.searchParams.set(k, v));
  }

  const jwt = typeof window !== 'undefined' ? localStorage.getItem('jwt') : null;
  const res = await fetch(url.toString(), {
    method,
    headers: {
      'Content-Type': 'application/json',
      ...(jwt ? { Authorization: `Bearer ${jwt}` } : {}),
    },
    body: options?.body !== undefined ? JSON.stringify(options.body) : undefined,
  });

  if (!res.ok) {
    const error: ApiError = await res.json();
    throw error;
  }

  if (res.status === 204) return undefined as T;
  return res.json() as Promise<T>;
}

export async function clientGetUsers(userId?: string): Promise<User[]> {
  const query = userId ? { userId } : undefined;
  return request<User[]>('GET', '/api/get/user', { query });
}

export async function clientRegisterUser(
  req: RegisterUserRequest
): Promise<{ userId: string }> {
  return request<{ userId: string }>('POST', '/api/post/user', { body: req });
}

export async function clientUpdateUser(req: UpdateUserRequest): Promise<void> {
  return request<void>('PUT', '/api/put/user', { body: req });
}

export async function clientGetChats(
  userId: string
): Promise<{ chats: ChatSummary[] }> {
  return request<{ chats: ChatSummary[] }>('GET', '/api/get/chats', {
    query: { userId },
  });
}

export async function clientGetChatMessages(
  chatId: string,
  cursor?: string,
  limit = 20
): Promise<{ messages: Message[]; nextCursor: string | null }> {
  const query: Record<string, string> = { chatId, limit: String(limit) };
  if (cursor) query.cursor = cursor;
  return request<{ messages: Message[]; nextCursor: string | null }>(
    'GET',
    '/api/get/chat/messages',
    { query }
  );
}

export async function clientSendMessage(
  req: SendMessageRequest
): Promise<{ messageId: string; sentAt: string }> {
  return request<{ messageId: string; sentAt: string }>(
    'POST',
    '/api/post/chat/message',
    { body: req }
  );
}

export async function clientSendReaction(req: SendReactionRequest): Promise<void> {
  return request<void>('POST', '/api/post/chat/reaction', { body: req });
}

export async function clientMarkMessagesRead(
  chatId: string,
  userId: string
): Promise<void> {
  return request<void>('POST', '/api/post/chat/read', {
    body: { chatId, userId },
  });
}
