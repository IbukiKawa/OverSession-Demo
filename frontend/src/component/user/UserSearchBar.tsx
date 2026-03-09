'use client';

import { useState } from 'react';

interface UserSearchBarProps {
  onSearch: (userId: string) => void;
  loading?: boolean;
}

export default function UserSearchBar({ onSearch, loading }: UserSearchBarProps) {
  const [userId, setUserId] = useState('');

  function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    onSearch(userId.trim());
  }

  return (
    <form onSubmit={handleSubmit} className="flex gap-2 mb-4">
      <input
        type="text"
        placeholder="ユーザID（空欄で全件）"
        value={userId}
        onChange={(e) => setUserId(e.target.value)}
        className="flex-1 border border-gray-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-400"
      />
      <button
        type="submit"
        disabled={loading}
        className="px-4 py-2 bg-blue-500 text-white rounded-lg text-sm font-medium disabled:opacity-50 hover:bg-blue-600 transition-colors"
      >
        {loading ? '検索中…' : '検索'}
      </button>
    </form>
  );
}
