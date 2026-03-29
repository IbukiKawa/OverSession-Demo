import type {
  User,
  RegisterUserRequest,
  UpdateUserRequest,
  ChatSummary,
  Message,
  SendMessageRequest,
  SendReactionRequest,
  ApiError,
  CreateChatRequest,
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

  // 204 No Content、または空ボディ (Content-Length: 0) の場合は json パースをスキップ
  const contentLength = res.headers.get('content-length');
  if (res.status === 204 || contentLength === '0') return undefined as T;

  // ボディがテキストとして空の場合もガード
  const text = await res.text();
  if (!text) return undefined as T;

  // JSON パースを試み、失敗した場合は生のテキストを返す
  // （バックエンドが String を直接返すエンドポイント用）
  try {
    return JSON.parse(text) as T;
  } catch {
    return text as unknown as T;
  }
}

export async function clientGetUsers(userId?: string): Promise<User[]> {
  const query = userId ? { userId } : undefined;
  return request<User[]>('GET', '/api/users', { query });
}

export async function clientSearchUsers(keyword: string): Promise<User[]> {
  return request<User[]>('GET', '/api/users/search', { query: { keyword } });
}

export async function clientRegisterUser(
  req: RegisterUserRequest
): Promise<{ userId: string }> {
  // バックエンドは User オブジェクトを受け取り、String (userId) を返す
  const body: Record<string, unknown> = {
    userName: req.userName,
    primaryHeadOfficeName: req.primaryHeadOfficeName,
    secondaryHeadOfficeName: req.secondaryHeadOfficeName,
    departmentName: req.departmentName,
    officeId: req.officeId,
    floor: req.floor,
    gender: req.gender,
    affiliationYear: req.affiliationYear,
    workingStatus: req.workingStatus,
    matchingUserId: req.matchingUserId,
    pictureName: req.userImageUrl, // userImageUrl → pictureName
    deleted: false,
  };
  const userId = await request<string>('POST', '/api/users', { body });
  return { userId };
}

export async function clientUpdateUser(req: UpdateUserRequest): Promise<void> {
  // バックエンドは User オブジェクトを受け取る。userImageUrl → pictureName に変換
  const body: Record<string, unknown> = {
    userId: req.userId,
    userName: req.userName,
    primaryHeadOfficeName: req.primaryHeadOfficeName,
    secondaryHeadOfficeName: req.secondaryHeadOfficeName,
    departmentName: req.departmentName,
    officeId: req.officeId,
    floor: req.floor,
    gender: req.gender,
    affiliationYear: req.affiliationYear,
    workingStatus: req.workingStatus,
    matchingUserId: req.matchingUserId,
    pictureName: req.userImageUrl, // userImageUrl → pictureName
    deleted: req.deleted,
  };
  return request<void>('PUT', `/api/users/${req.userId}`, { body });
}

export async function clientDeleteUser(userId: string): Promise<void> {
  return request<void>('DELETE', `/api/users/${userId}`);
}

// バックエンドは ChatSummary[] をフラットな配列で返す
export async function clientGetChats(
  userId: string
): Promise<ChatSummary[]> {
  return request<ChatSummary[]>('GET', '/api/chats', {
    query: { userId },
  });
}

export async function clientGetChat(
  chatId: string,
): Promise<ChatSummary> {
  return request<ChatSummary>('GET', `/api/chats/${chatId}`);
}

// バックエンドは Message[] をフラットな配列で返す（nextCursor なし）
export async function clientGetChatMessages(
  chatId: string,
  cursor?: string,
  limit = 20
): Promise<{ messages: Message[]; nextCursor: string | null }> {
  const query: Record<string, string> = { limit: String(limit) };
  if (cursor) query.cursor = cursor;
  const messages = await request<Message[]>(
    'GET',
    `/api/chats/${chatId}/messages`,
    { query }
  );
  // バックエンドが返すリストが limit 件ちょうどなら、まだ過去ログがある可能性あり
  const nextCursor = messages.length >= limit ? messages[0].messageId : null;
  return { messages, nextCursor };
}

export async function createChat(
  req: CreateChatRequest
): Promise<string> {
  // バックエンドは chatId を String で返す
  return request<string>('POST', '/api/chats', { body: req });
}

export async function clientSendMessage(
  req: SendMessageRequest
): Promise<{ messageId: string; sentAt: string }> {
  // バックエンドは messageId を String で返す
  const messageId = await request<string>(
    'POST',
    `/api/chats/${req.chatId}/messages`,
    { body: { senderUserId: req.senderUserId, text: req.text } }
  );
  return { messageId, sentAt: new Date().toISOString() };
}

export async function clientSendReaction(req: SendReactionRequest): Promise<void> {
  // POST /api/chats/{chatId}/messages/{messageId}/reactions
  // body: { userId, reactionType }
  return request<void>(
    'POST',
    `/api/chats/${req.chatId}/messages/${req.messageId}/reactions`,
    { body: { userId: req.reactorUserId, reactionType: req.type } }
  );
}

export async function clientMarkMessagesRead(
  chatId: string,
  userId: string,
  messageIds: string[]
): Promise<void> {
  // バックエンドはメッセージ1件ずつ PUT で既読化
  // PUT /api/chats/{chatId}/messages/{messageId}/read, body: { readerId }
  await Promise.all(
    messageIds.map((messageId) =>
      request<void>('PUT', `/api/chats/${chatId}/messages/${messageId}/read`, {
        body: { readerId: userId },
      })
    )
  );
}
