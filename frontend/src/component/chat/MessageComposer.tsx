'use client';

import { useState } from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';

const MAX_LENGTH = 1000;

interface MessageComposerProps {
  onSend: (text: string) => Promise<void>;
  disabled?: boolean;
}

export default function MessageComposer({ onSend, disabled }: MessageComposerProps) {
  const [text, setText] = useState('');
  const [sending, setSending] = useState(false);

  const isOver = text.length > MAX_LENGTH;
  const canSend = text.trim().length > 0 && !isOver && !sending && !disabled;

  async function handleSend() {
    if (!canSend) return;
    setSending(true);
    try {
      await onSend(text.trim());
      setText('');
    } finally {
      setSending(false);
    }
  }

  function handleKeyDown(e: React.KeyboardEvent) {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      handleSend();
    }
  }

  return (
    <Box sx={{ borderTop: 1, borderColor: 'divider', bgcolor: 'background.paper', px: 1.5, py: 1 }}>
      <Box display="flex" alignItems="flex-end" gap={1}>
        <TextField
          fullWidth
          multiline
          maxRows={4}
          size="small"
          placeholder="メッセージを入力してください"
          value={text}
          onChange={(e) => setText(e.target.value)}
          onKeyDown={handleKeyDown}
          disabled={disabled || sending}
        />
        <Button
          variant="contained"
          onClick={handleSend}
          disabled={!canSend}
          sx={{ whiteSpace: 'nowrap', flexShrink: 0 }}
        >
          {sending ? '送信中…' : '送信'}
        </Button>
      </Box>
      <Typography
        variant="caption"
        display="block"
        textAlign="right"
        color={isOver ? 'error' : 'text.secondary'}
        fontWeight={isOver ? 'bold' : undefined}
        mt={0.5}
      >
        {text.length} / {MAX_LENGTH}
      </Typography>
    </Box>
  );
}
