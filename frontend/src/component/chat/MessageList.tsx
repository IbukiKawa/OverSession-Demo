'use client';

import { useRef, useEffect, useCallback } from 'react';
import Box from '@mui/material/Box';
import Chip from '@mui/material/Chip';
import CircularProgress from '@mui/material/CircularProgress';
import Typography from '@mui/material/Typography';
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
    <Box
      ref={listRef}
      sx={{
        flex: 1,
        overflowY: 'auto',
        px: 2,
        py: 2,
        display: 'flex',
        flexDirection: 'column',
        gap: 1.5,
      }}
    >
      {loadingMore && (
        <Box display="flex" justifyContent="center" alignItems="center" gap={1} py={1}>
          <CircularProgress size={14} />
          <Typography variant="caption" color="text.secondary">
            読み込み中...
          </Typography>
        </Box>
      )}

      {!hasMore && messages.length > 0 && (
        <Typography variant="caption" color="text.disabled" textAlign="center" py={0.5}>
          最初のメッセージ
        </Typography>
      )}

      {messages.map((msg, idx) => {
        const prevMsg = idx > 0 ? messages[idx - 1] : null;
        const showDateHeader = !prevMsg || !isSameDay(msg.sentAt, prevMsg.sentAt);

        return (
          <Box key={msg.messageId}>
            {showDateHeader && (
              <Box display="flex" justifyContent="center" my={1}>
                <Chip
                  label={formatDateHeader(msg.sentAt)}
                  size="small"
                  sx={{ bgcolor: 'grey.200', color: 'text.secondary', fontSize: '0.72rem' }}
                />
              </Box>
            )}
            <MessageBubble
              message={msg}
              isMine={msg.senderUserId === currentUserId}
              onReact={onReact}
              onAvatarClick={onAvatarClick}
              partnerName={partnerName}
              partnerImageUrl={partnerImageUrl}
            />
          </Box>
        );
      })}

      <div ref={bottomRef} />
    </Box>
  );
}
