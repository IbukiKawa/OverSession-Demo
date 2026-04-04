'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import List from '@mui/material/List';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemAvatar from '@mui/material/ListItemAvatar';
import ListItemText from '@mui/material/ListItemText';
import Badge from '@mui/material/Badge';
import CircularProgress from '@mui/material/CircularProgress';
import Alert from '@mui/material/Alert';
import Box from '@mui/material/Box';
import Divider from '@mui/material/Divider';
import PersonIcon from '@mui/icons-material/Person';
import type { ChatSummary, User } from '@/api/types';
import { getChats, getPictureUrl } from '@/api';
import { getCurrentUser } from '@/lib/session';
import Avatar from '@/component/chat/Avatar';

function formatLastMessageAt(isoString: string): string {
  const d = new Date(isoString);
  const now = new Date();
  const isToday = d.toDateString() === now.toDateString();
  if (isToday) {
    return `${String(d.getHours()).padStart(2, '0')}:${String(d.getMinutes()).padStart(2, '0')}`;
  }
  const diffDays = Math.floor((now.getTime() - d.getTime()) / (1000 * 60 * 60 * 24));
  if (diffDays === 1) return '昨日';
  return `${d.getMonth() + 1}/${d.getDate()}`;
}

function NoUserScreen() {
  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      justifyContent="center"
      sx={{ minHeight: '100vh', gap: 2, px: 3 }}
    >
      <PersonIcon sx={{ fontSize: 64, color: 'text.disabled' }} />
      <Typography variant="h6" color="text.secondary" textAlign="center">
        ログインユーザが選択されていません
      </Typography>
      <Typography variant="body2" color="text.secondary" textAlign="center">
        ユーザ管理画面からユーザを選択してチャットを開始してください。
      </Typography>
      <Button variant="contained" href="/users">
        ユーザ管理へ戻る
      </Button>
    </Box>
  );
}

export default function ChatsPage() {
  const router = useRouter();
  const [currentUser, setCurrentUser_] = useState<User | null | undefined>(undefined);
  const [chats, setChats] = useState<ChatSummary[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const user = getCurrentUser();
    setCurrentUser_(user);
    if (!user) {
      setLoading(false);
      return;
    }
    getChats(user.userId)
      .then((res) => setChats(res.chats))
      .catch((e) => setError(e?.reason ?? 'チャット一覧の取得に失敗しました'))
      .finally(() => setLoading(false));
  }, []);

  // SessionStorage の確認中は何も表示しない（hydration mismatch 防止）
  if (currentUser === undefined) return null;

  if (currentUser === null) return <NoUserScreen />;

  return (
    <Box sx={{ maxWidth: 600, mx: 'auto', minHeight: '100vh' }}>
      <AppBar position="sticky" color="inherit" elevation={1}>
        <Toolbar>
          <Box display="flex" alignItems="center" gap={1} sx={{ flex: 1 }}>
            <Avatar
              name={currentUser.userName}
              imageUrl={getPictureUrl(currentUser.pictureName)}
              size="sm"
            />
            <Typography variant="h6" fontWeight="bold">
              チャット
            </Typography>
          </Box>
          <Button href="/users" size="small">
            ユーザ管理
          </Button>
        </Toolbar>
      </AppBar>

      {loading && (
        <Box display="flex" justifyContent="center" py={8}>
          <CircularProgress />
        </Box>
      )}

      {error && (
        <Alert severity="error" sx={{ mx: 2, mt: 2 }}>
          {error}
        </Alert>
      )}

      {!loading && !error && chats.length === 0 && (
        <Typography color="text.secondary" textAlign="center" py={8}>
          チャットがありません
        </Typography>
      )}

      <List disablePadding>
        {chats.map((chat, idx) => (
          <Box key={chat.chatId}>
            <ListItemButton
              onClick={() => router.push(`/chats/${chat.chatId}`)}
              sx={{ px: 2, py: 1.5 }}
            >
              <ListItemAvatar>
                <Avatar name={chat.partnerUserName} imageUrl={getPictureUrl(chat.partnerPictureName)} size="md" />
              </ListItemAvatar>
              <ListItemText
                primary={
                  <Box display="flex" justifyContent="space-between" alignItems="center">
                    <Typography variant="subtitle2" fontWeight="bold" noWrap sx={{ flex: 1 }}>
                      {chat.partnerUserName}
                    </Typography>
                    {chat.lastMessageAt && (
                      <Typography variant="caption" color="text.secondary" sx={{ ml: 1, flexShrink: 0 }}>
                        {formatLastMessageAt(chat.lastMessageAt)}
                      </Typography>
                    )}
                  </Box>
                }
                secondary={
                  <Box display="flex" justifyContent="space-between" alignItems="center" mt={0.25}>
                    <Typography variant="body2" color="text.secondary" noWrap sx={{ flex: 1 }}>
                      {chat.lastMessage ?? ''}
                    </Typography>
                    {chat.unreadCount && chat.unreadCount > 0 ? (
                      <Badge
                        badgeContent={chat.unreadCount}
                        color="error"
                        sx={{ ml: 1, flexShrink: 0 }}
                      />
                    ) : null}
                  </Box>
                }
                disableTypography
              />
            </ListItemButton>
            {idx < chats.length - 1 && <Divider component="li" />}
          </Box>
        ))}
      </List>
    </Box>
  );
}
