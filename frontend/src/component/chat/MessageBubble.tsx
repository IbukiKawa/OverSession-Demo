'use client';

import { useState, useRef } from 'react';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Typography from '@mui/material/Typography';
import Chip from '@mui/material/Chip';
import IconButton from '@mui/material/IconButton';
import type { Message, ReactionType } from '@/api/types';
import Avatar from './Avatar';
import ReactionPicker, { REACTIONS } from './ReactionPicker';

interface MessageBubbleProps {
  message: Message;
  isMine: boolean;
  onReact: (messageId: string, type: ReactionType) => void;
  onAvatarClick?: () => void;
  partnerName?: string;
  partnerImageUrl?: string;
}

function formatTime(isoString: string): string {
  const d = new Date(isoString);
  return `${String(d.getHours()).padStart(2, '0')}:${String(d.getMinutes()).padStart(2, '0')}`;
}

export default function MessageBubble({
  message,
  isMine,
  onReact,
  onAvatarClick,
  partnerName = '',
  partnerImageUrl,
}: MessageBubbleProps) {
  const [showPicker, setShowPicker] = useState(false);
  const [hovered, setHovered] = useState(false);
  const pressTimer = useRef<ReturnType<typeof setTimeout> | null>(null);

  function handlePointerDown() {
    if (isMine) return;
    pressTimer.current = setTimeout(() => setShowPicker(true), 500);
  }
  function handlePointerUp() {
    if (pressTimer.current) {
      clearTimeout(pressTimer.current);
      pressTimer.current = null;
    }
  }
  function handleContextMenu(e: React.MouseEvent) {
    if (isMine) return;
    e.preventDefault();
    setShowPicker(true);
  }

  // 自分のメッセージに既読/未読を表示
  const readLabel = isMine ? (
    message.readAt ? (
      <Typography variant="caption" color="primary.main" sx={{ ml: 0.5 }}>
        既読
      </Typography>
    ) : (
      <Typography variant="caption" color="text.disabled" sx={{ ml: 0.5 }}>
        未読
      </Typography>
    )
  ) : null;

  return (
    <Box
      display="flex"
      alignItems="flex-end"
      gap={1}
      flexDirection={isMine ? 'row-reverse' : 'row'}
      onMouseEnter={() => setHovered(true)}
      onMouseLeave={() => setHovered(false)}
    >
      {/* Avatar（相手のみ） */}
      {!isMine && (
        <Avatar name={partnerName} imageUrl={partnerImageUrl} size="sm" onClick={onAvatarClick} />
      )}

      <Box
        display="flex"
        flexDirection="column"
        alignItems={isMine ? 'flex-end' : 'flex-start'}
        maxWidth="70%"
      >
        {/* 吹き出し + リアクションボタン */}
        <Box
          display="flex"
          alignItems="flex-end"
          gap={0.5}
          flexDirection={isMine ? 'row-reverse' : 'row'}
        >
          {/* 相手メッセージにだけホバーで😊ボタン */}
          {!isMine && (
            <IconButton
              size="small"
              onClick={() => setShowPicker(true)}
              title="リアクション"
              sx={{
                fontSize: '1.25rem',
                opacity: hovered ? 0.6 : 0,
                transition: 'opacity 0.2s',
                flexShrink: 0,
                mb: 0.25,
                '&:hover': { opacity: 1, bgcolor: 'transparent' },
              }}
            >
              😊
            </IconButton>
          )}

          <Box position="relative">
            <Paper
              onPointerDown={handlePointerDown}
              onPointerUp={handlePointerUp}
              onPointerCancel={handlePointerUp}
              onContextMenu={handleContextMenu}
              elevation={0}
              sx={{
                px: 2,
                py: 1,
                borderRadius: 3,
                ...(isMine
                  ? { bgcolor: 'primary.main', color: '#fff', borderBottomRightRadius: 4 }
                  : { bgcolor: 'grey.100', color: 'text.primary', borderBottomLeftRadius: 4 }),
                whiteSpace: 'pre-wrap',
                wordBreak: 'break-word',
                userSelect: 'none',
                cursor: 'default',
              }}
            >
              <Typography variant="body2">{message.text}</Typography>
            </Paper>

            {showPicker && (
              <ReactionPicker
                onSelect={(type) => {
                  setShowPicker(false);
                  onReact(message.messageId, type);
                }}
                onClose={() => setShowPicker(false)}
              />
            )}
          </Box>
        </Box>

        {/* リアクション表示 */}
        {message.reactions && message.reactions.length > 0 && (
          <Box display="flex" gap={0.5} mt={0.5} flexWrap="wrap">
            {message.reactions
              .filter((r) => r.count > 0)
              .map((r) => {
                const info = REACTIONS.find((rx) => rx.type === r.type);
                return (
                  <Chip
                    key={r.type}
                    label={`${info?.emoji} ${r.count}`}
                    size="small"
                    onClick={() => onReact(message.messageId, r.type)}
                    variant={r.reactedByMe ? 'filled' : 'outlined'}
                    color={r.reactedByMe ? 'primary' : 'default'}
                    sx={{ fontSize: '0.72rem', height: 24 }}
                  />
                );
              })}
          </Box>
        )}

        {/* タイムスタンプ + 既読/未読 */}
        <Box display="flex" alignItems="center" mt={0.25}>
          {isMine && readLabel}
          <Typography variant="caption" color="text.secondary">
            {formatTime(message.sentAt)}
          </Typography>
        </Box>
      </Box>
    </Box>
  );
}
