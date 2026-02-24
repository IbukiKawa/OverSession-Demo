import type { NextConfig } from 'next';

const nextConfig: NextConfig = {
  images: {
    // モックで使用するプレースホルダー画像ドメイン
    remotePatterns: [
      {
        protocol: 'https',
        hostname: 'i.pravatar.cc',
      },
    ],
  },
};

export default nextConfig;
