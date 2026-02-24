'use client';

import { useRouter } from 'next/navigation';

interface ChatHeaderProps {
  partnerName: string;
}

export default function ChatHeader({ partnerName }: ChatHeaderProps) {
  const router = useRouter();

  return (
    <div className="flex items-center gap-3 px-4 py-3 border-b bg-white sticky top-0 z-10">
      <button
        onClick={() => router.back()}
        className="text-blue-500 hover:text-blue-700 text-sm font-medium"
      >
        ← 戻る
      </button>
      <h1 className="font-semibold text-gray-800 truncate">{partnerName}</h1>
    </div>
  );
}
