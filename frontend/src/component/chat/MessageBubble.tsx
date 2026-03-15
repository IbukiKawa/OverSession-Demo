'use client';

import { useState, useRef } from 'react';
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
  const pressTimer = useRef<ReturnType<typeof setTimeout> | null>(null);

  // Long-press (mobile)
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
  // Right-click (PC)
  function handleContextMenu(e: React.MouseEvent) {
    if (isMine) return;
    e.preventDefault();
    setShowPicker(true);
  }

  // 既読/未読ラベル（自分が送ったメッセージのみ）
  const readLabel = isMine
    ? message.readAt
      ? <span className="text-xs text-blue-400 ml-1">既読</span>
      : <span className="text-xs text-gray-300 ml-1">未読</span>
    : null;

  return (
    <div className={`flex items-end gap-2 group ${isMine ? 'flex-row-reverse' : 'flex-row'}`}>
      {/* Avatar（相手のみ） */}
      {!isMine && (
        <Avatar
          name={partnerName}
          imageUrl={partnerImageUrl}
          size="sm"
          onClick={onAvatarClick}
        />
      )}

      <div className={`flex flex-col ${isMine ? 'items-end' : 'items-start'} max-w-[70%]`}>
        {/* 吹き出し + リアクションボタン */}
        <div className={`flex items-end gap-1 ${isMine ? 'flex-row-reverse' : 'flex-row'}`}>
          {/* 相手メッセージにだけホバーで😊ボタン */}
          {!isMine && (
            <button
              onClick={() => setShowPicker(true)}
              className="text-lg opacity-0 group-hover:opacity-60 hover:!opacity-100 transition-opacity flex-shrink-0 mb-1"
              title="リアクション"
            >
              😊
            </button>
          )}

          <div className="relative">
            <div
              onPointerDown={handlePointerDown}
              onPointerUp={handlePointerUp}
              onPointerCancel={handlePointerUp}
              onContextMenu={handleContextMenu}
              className={`px-4 py-2 rounded-2xl text-sm whitespace-pre-wrap break-words select-none ${
                isMine
                  ? 'bg-blue-500 text-white rounded-br-sm'
                  : 'bg-gray-100 text-gray-800 rounded-bl-sm'
              }`}
            >
              {message.text}
            </div>

            {/* リアクションピッカー */}
            {showPicker && (
              <ReactionPicker
                onSelect={(type) => {
                  setShowPicker(false);
                  onReact(message.messageId, type);
                }}
                onClose={() => setShowPicker(false)}
              />
            )}
          </div>
        </div>

        {/* リアクション表示 */}
        {message.reactions && message.reactions.length > 0 && (
          <div className="flex gap-1 mt-1 flex-wrap">
            {message.reactions
              .filter((r) => r.count > 0)
              .map((r) => {
                const info = REACTIONS.find((rx) => rx.type === r.type);
                return (
                  <button
                    key={r.type}
                    onClick={() => onReact(message.messageId, r.type)}
                    className={`flex items-center gap-0.5 text-xs rounded-full px-2 py-0.5 border transition-colors ${
                      r.reactedByMe
                        ? 'bg-blue-100 border-blue-400 text-blue-700'
                        : 'bg-white border-gray-200 text-gray-600 hover:bg-gray-50'
                    }`}
                  >
                    <span>{info?.emoji}</span>
                    <span>{r.count}</span>
                  </button>
                );
              })}
          </div>
        )}

        {/* タイムスタンプ + 既読/未読 */}
        <div className="flex items-center mt-0.5">
          {isMine && readLabel}
          <span className="text-xs text-gray-400">{formatTime(message.sentAt)}</span>
        </div>
      </div>
    </div>
  );
}
