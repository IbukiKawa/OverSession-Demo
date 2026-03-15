import Alert from '@mui/material/Alert';

interface ErrorBannerProps {
  message: string;
  onClose?: () => void;
}

export default function ErrorBanner({ message, onClose }: ErrorBannerProps) {
  return (
    <Alert
      severity="error"
      onClose={onClose}
      sx={{
        position: 'fixed',
        bottom: 80,
        left: 16,
        right: 16,
        zIndex: 50,
        boxShadow: 3,
      }}
    >
      {message}
    </Alert>
  );
}
