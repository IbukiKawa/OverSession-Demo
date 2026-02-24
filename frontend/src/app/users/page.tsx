'use client';

import { useEffect, useState } from 'react';
import type { User } from '@/api/types';
import { getUsers } from '@/api';
import UserSearchBar from '@/component/user/UserSearchBar';
import UserTable from '@/component/user/UserTable';
import UserForm from '@/component/user/UserForm';

export default function UsersPage() {
  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [editTarget, setEditTarget] = useState<User | 'new' | null>(null);

  async function fetchUsers(userId?: string) {
    setLoading(true);
    setError(null);
    try {
      const result = await getUsers(userId || undefined);
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
    <div className="max-w-5xl mx-auto min-h-screen px-4 py-6">
      {/* Header */}
      <div className="flex items-center justify-between mb-6">
        <div className="flex items-center gap-3">
          <a href="/chats" className="text-blue-500 hover:underline text-sm">
            ← チャット
          </a>
          <h1 className="text-xl font-semibold">ユーザ管理</h1>
        </div>
        <button
          onClick={() => setEditTarget('new')}
          className="px-4 py-2 bg-blue-500 text-white rounded-lg text-sm font-medium hover:bg-blue-600 transition-colors"
        >
          + 新規登録
        </button>
      </div>

      <UserSearchBar onSearch={fetchUsers} loading={loading} />

      {error && (
        <div className="mb-4 bg-red-50 border border-red-200 rounded p-3 text-sm text-red-700">
          {error}
        </div>
      )}

      {loading ? (
        <div className="flex justify-center py-12">
          <div className="w-8 h-8 border-4 border-gray-200 border-t-blue-500 rounded-full animate-spin" />
        </div>
      ) : (
        <UserTable
          users={users}
          onEdit={(user) => setEditTarget(user)}
        />
      )}

      {editTarget !== null && (
        <UserForm
          user={editTarget === 'new' ? undefined : editTarget}
          onSuccess={handleFormSuccess}
          onCancel={() => setEditTarget(null)}
        />
      )}
    </div>
  );
}
