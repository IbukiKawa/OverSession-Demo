'use client';

import { useState } from 'react';
import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import TextField from '@mui/material/TextField';
import Select from '@mui/material/Select';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import InputLabel from '@mui/material/InputLabel';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import Alert from '@mui/material/Alert';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import CloseIcon from '@mui/icons-material/Close';
import type { User, RegisterUserRequest, UpdateUserRequest, ApiError } from '@/api/types';
import { registerUser, updateUser } from '@/api';

interface UserFormProps {
  user?: User;
  onSuccess: () => void;
  onCancel: () => void;
}

type FormData = {
  userName: string;
  primaryHeadOfficeName: string;
  secondaryHeadOfficeName: string;
  departmentName: string;
  officeId: string;
  floor: string;
  gender: string;
  affiliationYear: string;
  workingStatus: string;
  matchingUserId: string;
  userImageUrl: string;
  deleted: boolean;
};

function initForm(user?: User): FormData {
  return {
    userName: user?.userName ?? '',
    primaryHeadOfficeName: user?.primaryHeadOfficeName ?? '',
    secondaryHeadOfficeName: user?.secondaryHeadOfficeName ?? '',
    departmentName: user?.departmentName ?? '',
    officeId: user?.officeId != null ? String(user.officeId) : '',
    floor: user?.floor != null ? String(user.floor) : '',
    gender: user?.gender ?? '',
    affiliationYear: user?.affiliationYear != null ? String(user.affiliationYear) : '',
    workingStatus: user?.workingStatus ?? '出社',
    matchingUserId: user?.matchingUserId ?? '',
    userImageUrl: '',
    deleted: user?.deleted ?? false,
  };
}

function validate(form: FormData): string[] {
  const errors: string[] = [];
  if (!form.userName.trim()) errors.push('ユーザ名は必須です');
  if (!form.workingStatus) errors.push('出社ステータスは必須です');
  return errors;
}

export default function UserForm({ user, onSuccess, onCancel }: UserFormProps) {
  const isEdit = !!user;
  const [form, setForm] = useState<FormData>(initForm(user));
  const [submitting, setSubmitting] = useState(false);
  const [clientErrors, setClientErrors] = useState<string[]>([]);
  const [apiError, setApiError] = useState<ApiError | null>(null);

  function set(field: keyof FormData, value: string | boolean) {
    setForm((prev) => ({ ...prev, [field]: value }));
  }

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    const errs = validate(form);
    if (errs.length > 0) {
      setClientErrors(errs);
      return;
    }
    setClientErrors([]);
    setApiError(null);
    setSubmitting(true);

    try {
      const intOrUndef = (v: string) => (v ? parseInt(v, 10) : undefined);
      const strOrUndef = (v: string) => v.trim() || undefined;

      if (isEdit) {
        const req: UpdateUserRequest = {
          userId: user!.userId,
          userName: form.userName.trim(),
          primaryHeadOfficeName: strOrUndef(form.primaryHeadOfficeName),
          secondaryHeadOfficeName: strOrUndef(form.secondaryHeadOfficeName),
          departmentName: strOrUndef(form.departmentName),
          officeId: intOrUndef(form.officeId),
          floor: intOrUndef(form.floor),
          gender: strOrUndef(form.gender),
          affiliationYear: intOrUndef(form.affiliationYear),
          workingStatus: form.workingStatus,
          matchingUserId: strOrUndef(form.matchingUserId),
          userImageUrl: strOrUndef(form.userImageUrl),
          deleted: form.deleted,
        };
        await updateUser(req);
      } else {
        const req: RegisterUserRequest = {
          userName: form.userName.trim(),
          primaryHeadOfficeName: strOrUndef(form.primaryHeadOfficeName),
          secondaryHeadOfficeName: strOrUndef(form.secondaryHeadOfficeName),
          departmentName: strOrUndef(form.departmentName),
          officeId: intOrUndef(form.officeId),
          floor: intOrUndef(form.floor),
          gender: strOrUndef(form.gender),
          affiliationYear: intOrUndef(form.affiliationYear),
          workingStatus: form.workingStatus,
          matchingUserId: strOrUndef(form.matchingUserId),
          userImageUrl: strOrUndef(form.userImageUrl),
        };
        await registerUser(req);
      }
      onSuccess();
    } catch (err) {
      setApiError(err as ApiError);
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <Dialog open onClose={onCancel} maxWidth="sm" fullWidth>
      <DialogTitle sx={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
        {isEdit ? 'ユーザ編集' : '新規ユーザ登録'}
        <IconButton onClick={onCancel} size="small">
          <CloseIcon />
        </IconButton>
      </DialogTitle>

      <DialogContent dividers>
        <Box component="form" id="user-form" onSubmit={handleSubmit} display="flex" flexDirection="column" gap={2} pt={0.5}>
          {clientErrors.length > 0 && (
            <Alert severity="error">
              <ul style={{ margin: 0, paddingLeft: '1.25rem' }}>
                {clientErrors.map((e, i) => <li key={i}>{e}</li>)}
              </ul>
            </Alert>
          )}
          {apiError && (
            <Alert severity="error">
              <Typography variant="body2" fontWeight="bold">{apiError.reason}</Typography>
              {apiError.errors.map((e, i) => (
                <Typography key={i} variant="body2">{e.msg}</Typography>
              ))}
            </Alert>
          )}

          <TextField
            label="ユーザ名 *"
            size="small"
            fullWidth
            value={form.userName}
            onChange={(e) => set('userName', e.target.value)}
            required
          />

          <FormControl size="small" fullWidth required>
            <InputLabel>出社ステータス *</InputLabel>
            <Select
              label="出社ステータス *"
              value={form.workingStatus}
              onChange={(e) => set('workingStatus', e.target.value)}
            >
              <MenuItem value="出社">出社</MenuItem>
              <MenuItem value="不在">不在</MenuItem>
            </Select>
          </FormControl>

          <TextField
            label="本部名1"
            size="small"
            fullWidth
            value={form.primaryHeadOfficeName}
            onChange={(e) => set('primaryHeadOfficeName', e.target.value)}
          />
          <TextField
            label="本部名2"
            size="small"
            fullWidth
            value={form.secondaryHeadOfficeName}
            onChange={(e) => set('secondaryHeadOfficeName', e.target.value)}
          />
          <TextField
            label="部署名"
            size="small"
            fullWidth
            value={form.departmentName}
            onChange={(e) => set('departmentName', e.target.value)}
          />

          <Box display="grid" gridTemplateColumns="1fr 1fr" gap={2}>
            <TextField
              label="オフィスID"
              size="small"
              type="number"
              value={form.officeId}
              onChange={(e) => set('officeId', e.target.value)}
              inputProps={{ min: 0 }}
            />
            <TextField
              label="所属階"
              size="small"
              type="number"
              value={form.floor}
              onChange={(e) => set('floor', e.target.value)}
              inputProps={{ min: 0 }}
            />
          </Box>

          <Box display="grid" gridTemplateColumns="1fr 1fr" gap={2}>
            <FormControl size="small" fullWidth>
              <InputLabel>性別</InputLabel>
              <Select
                label="性別"
                value={form.gender}
                onChange={(e) => set('gender', e.target.value)}
              >
                <MenuItem value="">未選択</MenuItem>
                <MenuItem value="男性">男性</MenuItem>
                <MenuItem value="女性">女性</MenuItem>
                <MenuItem value="その他">その他</MenuItem>
              </Select>
            </FormControl>
            <TextField
              label="在籍年数"
              size="small"
              type="number"
              value={form.affiliationYear}
              onChange={(e) => set('affiliationYear', e.target.value)}
              inputProps={{ min: 0 }}
            />
          </Box>

          <TextField
            label="マッチングユーザID"
            size="small"
            fullWidth
            value={form.matchingUserId}
            onChange={(e) => set('matchingUserId', e.target.value)}
          />
          <TextField
            label="プロフィール画像URL"
            size="small"
            fullWidth
            placeholder="https://..."
            value={form.userImageUrl}
            onChange={(e) => set('userImageUrl', e.target.value)}
          />

          {isEdit && (
            <FormControlLabel
              control={
                <Checkbox
                  checked={form.deleted}
                  onChange={(e) => set('deleted', e.target.checked)}
                  color="error"
                />
              }
              label={
                <Typography variant="body2" color="error">
                  削除済みにする
                </Typography>
              }
            />
          )}
        </Box>
      </DialogContent>

      <DialogActions sx={{ px: 3, py: 2, gap: 1 }}>
        <Button variant="outlined" onClick={onCancel} fullWidth>
          キャンセル
        </Button>
        <Button
          type="submit"
          form="user-form"
          variant="contained"
          disabled={submitting}
          fullWidth
        >
          {submitting ? '送信中…' : isEdit ? '更新' : '登録'}
        </Button>
      </DialogActions>
    </Dialog>
  );
}
