// ===================== User =====================
export interface User {
  userId: string;
  userName: string;
  primaryHeadOfficeName?: string;
  secondaryHeadOfficeName?: string;
  departmentName?: string;
  officeId?: number;
  floor?: number;
  gender?: string;
  affiliationYear?: number;
  workingStatus: string; // "出社" | "不在"
  matchingUserId?: string;
  pictureName?: string;
  deleted: boolean;
}

export interface RegisterUserRequest {
  userName: string;
  primaryHeadOfficeName?: string;
  secondaryHeadOfficeName?: string;
  departmentName?: string;
  officeId?: number;
  floor?: number;
  gender?: string;
  affiliationYear?: number;
  workingStatus: string;
  matchingUserId?: string;
  userImageUrl?: string;
}

export interface UpdateUserRequest {
  userId: string;
  userName: string;
  primaryHeadOfficeName?: string;
  secondaryHeadOfficeName?: string;
  departmentName?: string;
  officeId?: number;
  floor?: number;
  gender?: string;
  affiliationYear?: number;
  workingStatus: string;
  matchingUserId?: string;
  userImageUrl?: string;
  deleted: boolean;
}

export interface RegisterUserResponse {
  userId: string;
}

// ===================== Chat =====================
export interface ChatSummary {
  chatId: string;
  partnerUserId: string;
  partnerUserName: string;
  partnerPictureName?: string;
  lastMessage?: string;
  lastMessageAt?: string;
  unreadCount?: number;
}

export type ReactionType = 1 | 2 | 3 | 4;

export interface Reaction {
  type: ReactionType;
  count: number;
  reactedByMe?: boolean;
}

export interface Message {
  messageId: string;
  chatId: string;
  senderUserId: string;
  text: string;
  sentAt: string; // ISO 8601
  reactions?: Reaction[];
  /** 受信者が既読にした日時。null=未読。自分が送ったメッセージでは「相手が読んだか」を示す */
  readAt?: string | null;
}

export interface GetChatsResponse {
  chats: ChatSummary[];
}

export interface GetMessagesResponse {
  messages: Message[];
  nextCursor?: string | null;
}

export interface SendMessageRequest {
  chatId: string;
  senderUserId: string;
  text: string;
}

export interface SendMessageResponse {
  messageId: string;
  sentAt: string;
}

export interface SendReactionRequest {
  chatId: string;
  messageId: string;
  type: ReactionType;
  reactorUserId: string;
}

// ===================== Error =====================
export interface ApiErrorItem {
  code: string;
  msg: string;
}

export interface ApiError {
  statusCode: number;
  reason: string;
  errors: ApiErrorItem[];
}
