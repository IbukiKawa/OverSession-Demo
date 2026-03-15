'use client';

import { useEffect, useState, useCallback, useRef } from 'react';
import { useParams } from 'next/navigation';
import type { Message, ReactionType, User } from '@/api/types';
import {
  getChatMessages,
  sendMessage,
  sendReaction,
  markMessagesRead,
  getChats,
  getUsers,
  CURRENT_USER_ID,
} from '@/api';
import ChatHeader from '@/component/chat/ChatHeader';
import MessageList from '@/component/chat/MessageList';
import MessageComposer from '@/component/chat/MessageComposer';
import LoadingOverlay from '@/component/chat/LoadingOverlay';
import ErrorBanner from '@/component/chat/ErrorBanner';
import Avatar from '@/component/chat/Avatar';

// プロフィールモーダル
function ProfileModal({ user, onClose }: { user: User; onClose: () => void }) {
  return (
    <div className="fixed inset-0 bg-black bg-opacity-40 flex items-center justify-center z-50 p-4">
      <div className="bg-white rounded-xl shadow-xl w-full max-w-sm p-6">
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-lg font-semibold">プロフィール</h2>
          <button onClick={onClose} className="text-gray-400 hover:text-gray-600">✕</button>
        </div>
        {/* アバター */}
        <div className="flex justify-center mb-4">
          <Avatar name={user.userName} imageUrl={user.pictureName} size="lg" />
        </div>
        <dl className="space-y-2 text-sm">
          <Row label="ユーザID" value={user.userId} />
          <Row label="ユーザ名" value={user.userName} />
          <Row label="本部名1" value={user.primaryHeadOfficeName} />
          <Row label="部署名" value={user.departmentName} />
          <Row label="性別" value={user.gender} />
          <Row
            label="在籍年数"
            value={user.affiliationYear != null ? `${user.affiliationYear}年` : undefined}
          />
          <Row label="出社ステータス" value={user.workingStatus} />
        </dl>
        <button
          onClick={onClose}
          className="mt-5 w-full py-2 bg-blue-500 text-white rounded-lg text-sm font-medium hover:bg-blue-600"
        >
          閉じる
        </button>
      </div>
    </div>
  );
}

function Row({ label, value }: { label: string; value?: string }) {
  if (!value) return null;
  return (
    <div className="flex gap-2">
      <dt className="text-gray-500 w-28 flex-shrink-0">{label}</dt>
      <dd className="text-gray-800">{value}</dd>
    </div>
  );
}

export default function ChatPage() {
  const params = useParams();
  const chatId = params.chatId as string;

  const [messages, setMessages] = useState<Message[]>([]);
  const [nextCursor, setNextCursor] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);
  const [loadingMore, setLoadingMore] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [partnerName, setPartnerName] = useState('');
  const [partnerUserId, setPartnerUserId] = useState('');
  const [partnerImageUrl, setPartnerImageUrl] = useState<string | undefined>();
  const [profileUser, setProfileUser] = useState<User | null>(null);
  const [scrollTrigger, setScrollTrigger] = useState(0);
  const initialized = useRef(false);

  // 相手情報取得
  useEffect(() => {
    getChats(CURRENT_USER_ID).then((res) => {
      const chat = res.chats.find((c) => c.chatId === chatId);
      if (chat) {
        setPartnerName(chat.partnerUserName);
        setPartnerUserId(chat.partnerUserId);
        setPartnerImageUrl(chat.partnerPictureName);
      }
    });
  }, [chatId]);

  // 初期メッセージ読み込み & 既読処理
  useEffect(() => {
    if (initialized.current) return;
    initialized.current = true;

    setLoading(true);
    getChatMessages(chatId)
      .then((res) => {
        setMessages(res.messages);
        setNextCursor(res.nextCursor ?? null);
        setScrollTrigger(1);
        // 相手メッセージを既読に
        markMessagesRead(chatId, CURRENT_USER_ID).catch(() => {});
      })
      .catch((e) => {
        setError(e?.reason ?? 'メッセージの取得に失敗しました');
      })
      .finally(() => setLoading(false));
  }, [chatId]);

  // 過去ログ読み込み
  const handleLoadMore = useCallback(async () => {
    if (!nextCursor || loadingMore) return;
    setLoadingMore(true);
    try {
      const res = await getChatMessages(chatId, nextCursor);
      setMessages((prev) => [...res.messages, ...prev]);
      setNextCursor(res.nextCursor ?? null);
    } catch (e: unknown) {
      const err = e as { reason?: string };
      setError(err?.reason ?? '過去ログの取得に失敗しました');
    } finally {
      setLoadingMore(false);
    }
  }, [chatId, nextCursor, loadingMore]);

  // メッセージ送信
  async function handleSend(text: string) {
    setError(null);
    const res = await sendMessage({ chatId, senderUserId: CURRENT_USER_ID, text }).catch(
      (e: unknown) => {
        const err = e as { reason?: string };
        setError(err?.reason ?? 'メッセージの送信に失敗しました');
        throw e;
      }
    );
    const newMsg: Message = {
      messageId: res.messageId,
      chatId,
      senderUserId: CURRENT_USER_ID,
      text,
      sentAt: res.sentAt,
      readAt: null,
    };
    setMessages((prev) => [...prev, newMsg]);
    setScrollTrigger((n) => n + 1);
  }

  // リアクション送信
  const handleReact = useCallback(
    async (messageId: string, type: ReactionType) => {
      setError(null);
      try {
        await sendReaction({ chatId, messageId, type, reactorUserId: CURRENT_USER_ID });
        // 楽観的更新
        setMessages((prev) =>
          prev.map((m) => {
            if (m.messageId !== messageId) return m;
            const reactions = [...(m.reactions ?? [])];
            const idx = reactions.findIndex((r) => r.type === type);
            if (idx !== -1) {
              const r = { ...reactions[idx] };
              if (r.reactedByMe) {
                r.count = Math.max(0, r.count - 1);
                r.reactedByMe = false;
                if (r.count === 0) reactions.splice(idx, 1);
                else reactions[idx] = r;
              } else {
                reactions[idx] = { ...r, count: r.count + 1, reactedByMe: true };
              }
            } else {
              reactions.push({ type, count: 1, reactedByMe: true });
            }
            return { ...m, reactions };
          })
        );
      } catch (e: unknown) {
        const err = e as { reason?: string };
        setError(err?.reason ?? 'リアクションの送信に失敗しました');
      }
    },
    [chatId]
  );

  // 相手プロフィール表示
  async function handleAvatarClick() {
    if (!partnerUserId) return;
    try {
      const users = await getUsers(partnerUserId);
      if (users.length > 0) setProfileUser(users[0]);
    } catch {
      // ignore
    }
  }

  return (
    <div className="flex flex-col h-screen max-w-2xl mx-auto">
      {loading && <LoadingOverlay />}

      <ChatHeader partnerName={partnerName || 'チャット'} />

      <MessageList
        messages={messages}
        currentUserId={CURRENT_USER_ID}
        partnerName={partnerName}
        partnerImageUrl={partnerImageUrl}
        onReact={handleReact}
        onAvatarClick={handleAvatarClick}
        onLoadMore={handleLoadMore}
        hasMore={!!nextCursor}
        loadingMore={loadingMore}
        scrollToBottomTrigger={scrollTrigger}
      />

      {error && <ErrorBanner message={error} onClose={() => setError(null)} />}

      <MessageComposer onSend={handleSend} />

      {profileUser && (
        <ProfileModal user={profileUser} onClose={() => setProfileUser(null)} />
      )}
    </div>
  );
}
