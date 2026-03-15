'use client';

import { useRouter } from 'next/navigation';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';

interface ChatHeaderProps {
  partnerName: string;
}

export default function ChatHeader({ partnerName }: ChatHeaderProps) {
  const router = useRouter();
  return (
    <AppBar position="sticky" color="inherit" elevation={1} sx={{ zIndex: 10 }}>
      <Toolbar variant="dense">
        <IconButton edge="start" color="primary" onClick={() => router.back()} sx={{ mr: 1 }}>
          <ArrowBackIcon />
        </IconButton>
        <Typography variant="subtitle1" fontWeight="bold" noWrap>
          {partnerName}
        </Typography>
      </Toolbar>
    </AppBar>
  );
}
