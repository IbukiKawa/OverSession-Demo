import type { Metadata } from 'next';
import './globals.css';

export const metadata: Metadata = {
  title: 'OverSession',
  description: 'ユーザマッチングシステム',
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="ja">
      <body className="antialiased bg-white text-gray-900">{children}</body>
    </html>
  );
}
