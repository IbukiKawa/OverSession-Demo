'use client';

import { useState } from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

interface UserSearchBarProps {
  onSearch: (keyword: string) => void;
  loading?: boolean;
}

export default function UserSearchBar({ onSearch, loading }: UserSearchBarProps) {
  const [keyword, setKeyword] = useState('');

  function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    onSearch(keyword.trim());
  }

  return (
    <Box component="form" onSubmit={handleSubmit} display="flex" gap={1} mb={2}>
      <TextField
        size="small"
        fullWidth
        placeholder="キーワード検索（空欄で全件表示）"
        value={keyword}
        onChange={(e) => setKeyword(e.target.value)}
      />
      <Button type="submit" variant="contained" disabled={loading} sx={{ whiteSpace: 'nowrap' }}>
        {loading ? '検索中…' : '検索'}
      </Button>
    </Box>
  );
}
