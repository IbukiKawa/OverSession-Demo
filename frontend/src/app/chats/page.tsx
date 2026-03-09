'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import type { ChatSummary } from '@/api/types';
import { getChats, CURRENT_USER_ID } from '@/api';
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

export default function ChatsPage() {
  const router = useRouter();
  const [chats, setChats] = useState<ChatSummary[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    getChats(CURRENT_USER_ID)
      .then((res) => setChats(res.chats))
      .catch((e) => setError(e?.reason ?? 'チャット一覧の取得に失敗しました'))
      .finally(() => setLoading(false));
  }, []);

  return (
    <div className="max-w-2xl mx-auto min-h-screen">
      {/* Header */}
      <div className="sticky top-0 bg-white border-b px-4 py-3 flex items-center justify-between">
        <h1 className="text-lg font-semibold">チャット</h1>
        <a href="/users" className="text-sm text-blue-500 hover:underline">
          ユーザ管理
        </a>
      </div>

      {loading && (
        <div className="flex justify-center items-center py-16">
          <div className="w-8 h-8 border-4 border-gray-200 border-t-blue-500 rounded-full animate-spin" />
        </div>
      )}

      {error && (
        <div className="mx-4 mt-4 bg-red-50 border border-red-200 rounded p-3 text-sm text-red-700">
          {error}
        </div>
      )}

      {!loading && !error && chats.length === 0 && (
        <p className="text-center text-gray-400 py-16">チャットがありません</p>
      )}

      <ul>
        {chats.map((chat) => (
          <li
            key={chat.chatId}
            onClick={() => router.push(`/chats/${chat.chatId}`)}
            className="flex items-center gap-3 px-4 py-4 border-b hover:bg-gray-50 cursor-pointer transition-colors"
          >
            <Avatar name={chat.partnerUserName} imageUrl={chat.partnerPictureName} size="md" />
            <div className="flex-1 min-w-0">
              <div className="flex items-center justify-between">
                <span className="font-semibold text-gray-800 truncate">
                  {chat.partnerUserName}
                </span>
                {chat.lastMessageAt && (
                  <span className="text-xs text-gray-400 ml-2 flex-shrink-0">
                    {formatLastMessageAt(chat.lastMessageAt)}
                  </span>
                )}
              </div>
              <div className="flex items-center justify-between mt-0.5">
                <span className="text-sm text-gray-500 truncate">
                  {chat.lastMessage ?? ''}
                </span>
                {chat.unreadCount && chat.unreadCount > 0 ? (
                  <span className="ml-2 bg-red-500 text-white text-xs font-medium rounded-full px-2 py-0.5 flex-shrink-0">
                    {chat.unreadCount}
                  </span>
                ) : null}
              </div>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}
