'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import Button from '@mui/material/Button';
import List from '@mui/material/List';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemAvatar from '@mui/material/ListItemAvatar';
import ListItemText from '@mui/material/ListItemText';
import CircularProgress from '@mui/material/CircularProgress';
import Alert from '@mui/material/Alert';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import type { User } from '@/api/types';
import { getUsers, getPictureUrl } from '@/api';
import { setCurrentUser } from '@/lib/session';
import Avatar from '@/component/chat/Avatar';

interface Props {
  open: boolean;
  onCancel: () => void;
}

export default function UserSelectModal({ open, onCancel }: Props) {
  const router = useRouter();
  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!open) return;
    setLoading(true);
    setError(null);
    getUsers()
      .then(setUsers)
      .catch(() => setError('ユーザの取得に失敗しました'))
      .finally(() => setLoading(false));
  }, [open]);

  function handleSelect(user: User) {
    setCurrentUser(user);
    router.push('/chats');
  }

  return (
    <Dialog open={open} onClose={onCancel} maxWidth="xs" fullWidth>
      <DialogTitle>ログインユーザを選択</DialogTitle>
      <DialogContent dividers sx={{ p: 0 }}>
        {loading && (
          <Box display="flex" justifyContent="center" py={4}>
            <CircularProgress />
          </Box>
        )}
        {error && (
          <Alert severity="error" sx={{ m: 2 }}>
            {error}
          </Alert>
        )}
        {!loading && !error && (
          <List disablePadding>
            {users.map((user) => (
              <ListItemButton
                key={user.userId}
                onClick={() => handleSelect(user)}
                divider
              >
                <ListItemAvatar>
                  <Avatar
                    name={user.userName}
                    imageUrl={getPictureUrl(user.pictureName)}
                    size="sm"
                  />
                </ListItemAvatar>
                <ListItemText
                  primary={
                    <Typography variant="subtitle2" fontWeight="bold">
                      {user.userName}
                    </Typography>
                  }
                  secondary={
                    <Typography variant="caption" color="text.secondary">
                      {[user.departmentName, user.primaryHeadOfficeName]
                        .filter(Boolean)
                        .join(' / ')}
                    </Typography>
                  }
                  disableTypography
                />
              </ListItemButton>
            ))}
          </List>
        )}
      </DialogContent>
      <DialogActions>
        <Button onClick={onCancel}>キャンセル</Button>
      </DialogActions>
    </Dialog>
  );
}
