'use client';

import { useRef, useEffect, useCallback } from 'react';
import type { Message, ReactionType } from '@/api/types';
import MessageBubble from './MessageBubble';

interface MessageListProps {
  messages: Message[];
  currentUserId: string;
  partnerName: string;
  partnerImageUrl?: string;
  onReact: (messageId: string, type: ReactionType) => void;
  onAvatarClick: () => void;
  onLoadMore: () => Promise<void>;
  hasMore: boolean;
  loadingMore: boolean;
  scrollToBottomTrigger: number;
}

function formatDateHeader(isoString: string): string {
  const d = new Date(isoString);
  const days = ['日', '月', '火', '水', '木', '金', '土'];
  return `${d.getMonth() + 1}/${d.getDate()}(${days[d.getDay()]})`;
}

function isSameDay(a: string, b: string): boolean {
  return new Date(a).toDateString() === new Date(b).toDateString();
}

export default function MessageList({
  messages,
  currentUserId,
  partnerName,
  partnerImageUrl,
  onReact,
  onAvatarClick,
  onLoadMore,
  hasMore,
  loadingMore,
  scrollToBottomTrigger,
}: MessageListProps) {
  const listRef = useRef<HTMLDivElement>(null);
  const bottomRef = useRef<HTMLDivElement>(null);
  const prevScrollHeight = useRef<number>(0);

  useEffect(() => {
    if (scrollToBottomTrigger > 0) {
      bottomRef.current?.scrollIntoView({ behavior: 'smooth' });
    }
  }, [scrollToBottomTrigger]);

  useEffect(() => {
    if (!loadingMore && listRef.current && prevScrollHeight.current > 0) {
      const diff = listRef.current.scrollHeight - prevScrollHeight.current;
      listRef.current.scrollTop = diff;
      prevScrollHeight.current = 0;
    }
  }, [loadingMore, messages]);

  const handleScroll = useCallback(async () => {
    const el = listRef.current;
    if (!el || loadingMore || !hasMore) return;
    if (el.scrollTop === 0) {
      prevScrollHeight.current = el.scrollHeight;
      await onLoadMore();
    }
  }, [onLoadMore, loadingMore, hasMore]);

  useEffect(() => {
    const el = listRef.current;
    if (!el) return;
    el.addEventListener('scroll', handleScroll);
    return () => el.removeEventListener('scroll', handleScroll);
  }, [handleScroll]);

  return (
    <div
      ref={listRef}
      className="flex-1 overflow-y-auto px-4 py-4 flex flex-col gap-3"
    >
      {loadingMore && (
        <div className="text-center text-xs text-gray-400 py-2">
          <span className="inline-block w-4 h-4 border-2 border-gray-300 border-t-blue-500 rounded-full animate-spin mr-1 align-middle" />
          読み込み中...
        </div>
      )}
      {!hasMore && messages.length > 0 && (
        <div className="text-center text-xs text-gray-300 py-1">最初のメッセージ</div>
      )}

      {messages.map((msg, idx) => {
        const prevMsg = idx > 0 ? messages[idx - 1] : null;
        const showDateHeader = !prevMsg || !isSameDay(msg.sentAt, prevMsg.sentAt);

        return (
          <div key={msg.messageId}>
            {showDateHeader && (
              <div className="flex justify-center my-2">
                <span className="bg-gray-200 text-gray-600 text-xs px-3 py-1 rounded-full">
                  {formatDateHeader(msg.sentAt)}
                </span>
              </div>
            )}
            <MessageBubble
              message={msg}
              isMine={msg.senderUserId === currentUserId}
              onReact={onReact}
              onAvatarClick={onAvatarClick}
              partnerName={partnerName}
              partnerImageUrl={partnerImageUrl}
            />
          </div>
        );
      })}

      <div ref={bottomRef} />
    </div>
  );
}
