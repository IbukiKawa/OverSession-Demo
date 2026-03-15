'use client';

import { useEffect, useState, useCallback, useRef } from 'react';
import { useParams } from 'next/navigation';
import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Stack from '@mui/material/Stack';
import CloseIcon from '@mui/icons-material/Close';
import type { Message, ReactionType, User } from '@/api/types';
import {
  getChatMessages,
  sendMessage,
  sendReaction,
  markMessagesRead,
  getChats,
  getUsers,
  getPictureUrl,
  CURRENT_USER_ID,
} from '@/api';
import ChatHeader from '@/component/chat/ChatHeader';
import MessageList from '@/component/chat/MessageList';
import MessageComposer from '@/component/chat/MessageComposer';
import LoadingOverlay from '@/component/chat/LoadingOverlay';
import ErrorBanner from '@/component/chat/ErrorBanner';
import Avatar from '@/component/chat/Avatar';

// プロフィール行コンポーネント
function ProfileRow({ label, value }: { label: string; value?: string }) {
  if (!value) return null;
  return (
    <Box display="flex" gap={1} py={0.5}>
      <Typography variant="body2" color="text.secondary" sx={{ width: 100, flexShrink: 0 }}>
        {label}
      </Typography>
      <Typography variant="body2">{value}</Typography>
    </Box>
  );
}

// プロフィールモーダル
function ProfileModal({ user, onClose }: { user: User; onClose: () => void }) {
  return (
    <Dialog open onClose={onClose} maxWidth="xs" fullWidth>
      <DialogTitle sx={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
        プロフィール
        <IconButton onClick={onClose} size="small">
          <CloseIcon />
        </IconButton>
      </DialogTitle>
      <DialogContent>
        <Stack alignItems="center" mb={2}>
          <Avatar name={user.userName} imageUrl={getPictureUrl(user.pictureName)} size="lg" />
        </Stack>
        <ProfileRow label="ユーザID" value={user.userId} />
        <ProfileRow label="ユーザ名" value={user.userName} />
        <ProfileRow label="本部名1" value={user.primaryHeadOfficeName} />
        <ProfileRow label="部署名" value={user.departmentName} />
        <ProfileRow label="性別" value={user.gender} />
        <ProfileRow
          label="在籍年数"
          value={user.affiliationYear != null ? `${user.affiliationYear}年` : undefined}
        />
        <ProfileRow label="出社ステータス" value={user.workingStatus} />
      </DialogContent>
      <DialogActions sx={{ px: 3, pb: 2 }}>
        <Button variant="contained" onClick={onClose} fullWidth>
          閉じる
        </Button>
      </DialogActions>
    </Dialog>
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
        setPartnerImageUrl(getPictureUrl(chat.partnerPictureName));
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
    <Box display="flex" flexDirection="column" sx={{ height: '100vh', maxWidth: 600, mx: 'auto' }}>
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
    </Box>
  );
}
