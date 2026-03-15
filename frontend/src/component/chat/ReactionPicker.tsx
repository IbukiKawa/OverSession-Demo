import Paper from '@mui/material/Paper';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
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
      <div style={{ position: 'fixed', inset: 0, zIndex: 40 }} onClick={onClose} />
      {/* Picker */}
      <Paper
        elevation={4}
        sx={{
          position: 'absolute',
          bottom: '100%',
          mb: 0.5,
          left: 0,
          display: 'flex',
          zIndex: 50,
          borderRadius: 10,
          px: 0.5,
          py: 0.25,
        }}
      >
        {REACTIONS.map(({ type, label, emoji }) => (
          <Tooltip key={type} title={label} placement="top">
            <IconButton
              size="small"
              onClick={(e) => {
                e.stopPropagation();
                onSelect(type);
              }}
              sx={{
                fontSize: '1.5rem',
                transition: 'transform 0.1s',
                '&:hover': { transform: 'scale(1.25)', bgcolor: 'transparent' },
              }}
            >
              {emoji}
            </IconButton>
          </Tooltip>
        ))}
      </Paper>
    </>
  );
}

export { REACTIONS };
