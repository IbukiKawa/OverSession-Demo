import Image from 'next/image';

interface AvatarProps {
  name: string;
  imageUrl?: string;
  size?: 'sm' | 'md' | 'lg';
  onClick?: () => void;
}

const COLORS = [
  'bg-blue-400',
  'bg-green-400',
  'bg-purple-400',
  'bg-yellow-400',
  'bg-pink-400',
  'bg-indigo-400',
];

function colorFromName(name: string): string {
  let hash = 0;
  for (let i = 0; i < name.length; i++) hash += name.charCodeAt(i);
  return COLORS[hash % COLORS.length];
}

const sizeMap = {
  sm: { cls: 'w-8 h-8 text-sm', px: 32 },
  md: { cls: 'w-10 h-10 text-base', px: 40 },
  lg: { cls: 'w-14 h-14 text-xl', px: 56 },
};

export default function Avatar({ name, imageUrl, size = 'md', onClick }: AvatarProps) {
  const { cls, px } = sizeMap[size];
  const interactiveCls = onClick ? 'cursor-pointer hover:opacity-80' : '';

  if (imageUrl) {
    return (
      <div
        className={`${cls} rounded-full overflow-hidden flex-shrink-0 ${interactiveCls}`}
        onClick={onClick}
        title={name}
      >
        <Image
          src={imageUrl}
          alt={name}
          width={px}
          height={px}
          className="w-full h-full object-cover"
          unoptimized
        />
      </div>
    );
  }

  return (
    <div
      className={`${cls} ${colorFromName(name)} rounded-full flex items-center justify-center text-white font-bold flex-shrink-0 ${interactiveCls}`}
      onClick={onClick}
      title={name}
    >
      {name.charAt(0)}
    </div>
  );
}
