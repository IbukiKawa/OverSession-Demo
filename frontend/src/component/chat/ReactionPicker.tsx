import type { ReactionType } from '@/api/types';

const REACTIONS: { type: ReactionType; label: string; emoji: string }[] = [
  { type: 1, label: 'Smile', emoji: '😊' },
  { type: 2, label: 'Good', emoji: '👍' },
  { type: 3, label: 'Like', emoji: '❤️' },
  { type: 4, label: 'Sad', emoji: '😢' },
];

interface ReactionPickerProps {
  onSelect: (type: ReactionType) => void;
  onClose: () => void;
}

export default function ReactionPicker({ onSelect, onClose }: ReactionPickerProps) {
  return (
    <>
      {/* Backdrop */}
      <div className="fixed inset-0 z-40" onClick={onClose} />
      {/* Picker */}
      <div className="absolute z-50 bottom-full mb-1 left-0 flex gap-1 bg-white border border-gray-200 rounded-full shadow-lg px-2 py-1">
        {REACTIONS.map(({ type, label, emoji }) => (
          <button
            key={type}
            title={label}
            onClick={(e) => {
              e.stopPropagation();
              onSelect(type);
            }}
            className="text-2xl hover:scale-125 transition-transform px-1"
          >
            {emoji}
          </button>
        ))}
      </div>
    </>
  );
}

export { REACTIONS };
