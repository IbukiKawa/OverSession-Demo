'use client';

import { useState } from 'react';

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

  function handleKeyDown(e: React.KeyboardEvent<HTMLTextAreaElement>) {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      handleSend();
    }
  }

  return (
    <div className="border-t bg-white px-3 py-2">
      <div className="flex items-end gap-2">
        <textarea
          className="flex-1 resize-none border border-gray-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-400 min-h-[44px] max-h-32"
          placeholder="メッセージを入力してください"
          value={text}
          onChange={(e) => setText(e.target.value)}
          onKeyDown={handleKeyDown}
          rows={1}
          disabled={disabled || sending}
        />
        <button
          onClick={handleSend}
          disabled={!canSend}
          className="px-4 py-2 bg-blue-500 text-white rounded-lg text-sm font-medium disabled:opacity-40 disabled:cursor-not-allowed hover:bg-blue-600 transition-colors"
        >
          {sending ? '送信中…' : '送信'}
        </button>
      </div>
      <div className={`text-right text-xs mt-1 ${isOver ? 'text-red-500 font-semibold' : 'text-gray-400'}`}>
        {text.length} / {MAX_LENGTH}
      </div>
    </div>
  );
}
