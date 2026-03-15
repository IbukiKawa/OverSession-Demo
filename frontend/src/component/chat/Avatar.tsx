import MuiAvatar from '@mui/material/Avatar';

interface AvatarProps {
  name: string;
  imageUrl?: string;
  size?: 'sm' | 'md' | 'lg';
  onClick?: () => void;
}

const SIZE_PX = { sm: 32, md: 40, lg: 56 };
const COLORS = ['#60a5fa', '#34d399', '#a78bfa', '#fbbf24', '#f472b6', '#818cf8'];

function colorFromName(name: string): string {
  let hash = 0;
  for (let i = 0; i < name.length; i++) hash += name.charCodeAt(i);
  return COLORS[hash % COLORS.length];
}

export default function Avatar({ name, imageUrl, size = 'md', onClick }: AvatarProps) {
  const px = SIZE_PX[size];
  return (
    <MuiAvatar
      src={imageUrl}
      alt={name}
      onClick={onClick}
      sx={{
        width: px,
        height: px,
        bgcolor: imageUrl ? undefined : colorFromName(name),
        cursor: onClick ? 'pointer' : 'default',
        flexShrink: 0,
        fontSize: size === 'sm' ? '0.8rem' : size === 'lg' ? '1.25rem' : '1rem',
        '&:hover': onClick ? { opacity: 0.8 } : undefined,
      }}
    >
      {!imageUrl && name.charAt(0)}
    </MuiAvatar>
  );
}
