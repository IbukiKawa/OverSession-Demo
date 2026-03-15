import Backdrop from '@mui/material/Backdrop';
import CircularProgress from '@mui/material/CircularProgress';
import Stack from '@mui/material/Stack';
import Typography from '@mui/material/Typography';

export default function LoadingOverlay() {
  return (
    <Backdrop open sx={{ zIndex: 50, bgcolor: 'rgba(255,255,255,0.7)', color: 'inherit' }}>
      <Stack alignItems="center" spacing={2}>
        <CircularProgress color="primary" />
        <Typography variant="body2" color="text.secondary">
          読み込み中...
        </Typography>
      </Stack>
    </Backdrop>
  );
}
