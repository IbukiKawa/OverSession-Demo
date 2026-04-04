'use client';

import { useEffect, useState } from 'react';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import Container from '@mui/material/Container';
import CircularProgress from '@mui/material/CircularProgress';
import Alert from '@mui/material/Alert';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import AddIcon from '@mui/icons-material/Add';
import type { User } from '@/api/types';
import { getUsers, searchUsers } from '@/api';
import UserSearchBar from '@/component/user/UserSearchBar';
import UserTable from '@/component/user/UserTable';
import UserForm from '@/component/user/UserForm';
import UserSelectModal from '@/component/user/UserSelectModal';

export default function UsersPage() {
  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [editTarget, setEditTarget] = useState<User | 'new' | null>(null);
  const [selectModalOpen, setSelectModalOpen] = useState(false);

  async function fetchUsers(keyword?: string) {
    setLoading(true);
    setError(null);
    try {
      const result = keyword
        ? await searchUsers(keyword)
        : await getUsers();
      setUsers(result);
    } catch (e: unknown) {
      const err = e as { reason?: string };
      setError(err?.reason ?? 'ユーザの取得に失敗しました');
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    fetchUsers();
  }, []);

  function handleFormSuccess() {
    setEditTarget(null);
    fetchUsers();
  }

  return (
    <Box sx={{ minHeight: '100vh' }}>
      <AppBar position="sticky" color="inherit" elevation={1}>
        <Toolbar>
          <Button
            startIcon={<ArrowBackIcon />}
            size="small"
            sx={{ mr: 2 }}
            onClick={() => setSelectModalOpen(true)}
          >
            チャット
          </Button>
          <Typography variant="h6" fontWeight="bold" sx={{ flex: 1 }}>
            ユーザ管理
          </Typography>
          <Button
            variant="contained"
            startIcon={<AddIcon />}
            size="small"
            onClick={() => setEditTarget('new')}
          >
            新規登録
          </Button>
        </Toolbar>
      </AppBar>

      <Container maxWidth="lg" sx={{ py: 3 }}>
        <UserSearchBar onSearch={fetchUsers} loading={loading} />

        {error && (
          <Alert severity="error" sx={{ mb: 2 }}>
            {error}
          </Alert>
        )}

        {loading ? (
          <Box display="flex" justifyContent="center" py={6}>
            <CircularProgress />
          </Box>
        ) : (
          <UserTable users={users} onEdit={(user) => setEditTarget(user)} />
        )}
      </Container>

      {editTarget !== null && (
        <UserForm
          user={editTarget === 'new' ? undefined : editTarget}
          onSuccess={handleFormSuccess}
          onCancel={() => setEditTarget(null)}
        />
      )}

      <UserSelectModal
        open={selectModalOpen}
        onCancel={() => setSelectModalOpen(false)}
      />
    </Box>
  );
}
