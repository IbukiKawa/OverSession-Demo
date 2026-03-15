import Table from '@mui/material/Table';
import TableHead from '@mui/material/TableHead';
import TableBody from '@mui/material/TableBody';
import TableRow from '@mui/material/TableRow';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import Paper from '@mui/material/Paper';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Chip from '@mui/material/Chip';
import type { User } from '@/api/types';
import { getPictureUrl } from '@/api';
import Avatar from '@/component/chat/Avatar';

interface UserTableProps {
  users: User[];
  onEdit: (user: User) => void;
}

const STATUS_COLOR: Record<string, 'success' | 'default'> = {
  出社: 'success',
  不在: 'default',
};

export default function UserTable({ users, onEdit }: UserTableProps) {
  if (users.length === 0) {
    return (
      <Typography color="text.secondary" variant="body2" textAlign="center" py={4}>
        ユーザが見つかりません
      </Typography>
    );
  }

  return (
    <TableContainer component={Paper} variant="outlined">
      <Table size="small">
        <TableHead>
          <TableRow sx={{ bgcolor: 'grey.50' }}>
            <TableCell>アイコン</TableCell>
            <TableCell>ユーザID</TableCell>
            <TableCell>ユーザ名</TableCell>
            <TableCell>本部名1</TableCell>
            <TableCell>部署名</TableCell>
            <TableCell>出社ステータス</TableCell>
            <TableCell>操作</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {users.map((user) => (
            <TableRow key={user.userId} hover>
              <TableCell>
                <Avatar name={user.userName} imageUrl={getPictureUrl(user.pictureName)} size="sm" />
              </TableCell>
              <TableCell>
                <Typography variant="caption" fontFamily="monospace" color="text.secondary">
                  {user.userId}
                </Typography>
              </TableCell>
              <TableCell>{user.userName}</TableCell>
              <TableCell sx={{ color: 'text.secondary' }}>
                {user.primaryHeadOfficeName ?? '—'}
              </TableCell>
              <TableCell sx={{ color: 'text.secondary' }}>
                {user.departmentName ?? '—'}
              </TableCell>
              <TableCell>
                <Chip
                  label={user.workingStatus}
                  size="small"
                  color={STATUS_COLOR[user.workingStatus] ?? 'default'}
                />
              </TableCell>
              <TableCell>
                <Button size="small" onClick={() => onEdit(user)}>
                  編集
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
