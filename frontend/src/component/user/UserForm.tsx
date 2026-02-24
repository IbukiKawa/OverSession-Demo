'use client';

import { useState } from 'react';
import type { User, RegisterUserRequest, UpdateUserRequest, ApiError } from '@/api/types';
import { registerUser, updateUser } from '@/api';

interface UserFormProps {
  user?: User; // undefined = new user mode
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
    <div className="fixed inset-0 bg-black bg-opacity-40 flex items-center justify-center z-50 p-4">
      <div className="bg-white rounded-xl shadow-xl w-full max-w-lg max-h-[90vh] overflow-y-auto">
        <div className="px-6 py-4 border-b flex items-center justify-between">
          <h2 className="text-lg font-semibold">{isEdit ? 'ユーザ編集' : '新規ユーザ登録'}</h2>
          <button onClick={onCancel} className="text-gray-400 hover:text-gray-600 text-xl">✕</button>
        </div>
        <form onSubmit={handleSubmit} className="px-6 py-4 space-y-4">
          {/* Validation errors */}
          {clientErrors.length > 0 && (
            <div className="bg-red-50 border border-red-200 rounded p-3 text-sm text-red-700">
              <ul className="list-disc list-inside space-y-1">
                {clientErrors.map((e, i) => <li key={i}>{e}</li>)}
              </ul>
            </div>
          )}
          {apiError && (
            <div className="bg-red-50 border border-red-200 rounded p-3 text-sm text-red-700">
              <p className="font-medium">{apiError.reason}</p>
              {apiError.errors.map((e, i) => (
                <p key={i}>{e.msg}</p>
              ))}
            </div>
          )}

          <Field label="ユーザ名 *">
            <input
              type="text"
              value={form.userName}
              onChange={(e) => set('userName', e.target.value)}
              className="input"
              required
            />
          </Field>

          <Field label="出社ステータス *">
            <select
              value={form.workingStatus}
              onChange={(e) => set('workingStatus', e.target.value)}
              className="input"
              required
            >
              <option value="出社">出社</option>
              <option value="不在">不在</option>
            </select>
          </Field>

          <Field label="本部名1">
            <input type="text" value={form.primaryHeadOfficeName} onChange={(e) => set('primaryHeadOfficeName', e.target.value)} className="input" />
          </Field>
          <Field label="本部名2">
            <input type="text" value={form.secondaryHeadOfficeName} onChange={(e) => set('secondaryHeadOfficeName', e.target.value)} className="input" />
          </Field>
          <Field label="部署名">
            <input type="text" value={form.departmentName} onChange={(e) => set('departmentName', e.target.value)} className="input" />
          </Field>
          <div className="grid grid-cols-2 gap-4">
            <Field label="オフィスID">
              <input type="number" value={form.officeId} onChange={(e) => set('officeId', e.target.value)} className="input" min={0} />
            </Field>
            <Field label="所属階">
              <input type="number" value={form.floor} onChange={(e) => set('floor', e.target.value)} className="input" min={0} />
            </Field>
          </div>
          <div className="grid grid-cols-2 gap-4">
            <Field label="性別">
              <select value={form.gender} onChange={(e) => set('gender', e.target.value)} className="input">
                <option value="">未選択</option>
                <option value="男性">男性</option>
                <option value="女性">女性</option>
                <option value="その他">その他</option>
              </select>
            </Field>
            <Field label="在籍年数">
              <input type="number" value={form.affiliationYear} onChange={(e) => set('affiliationYear', e.target.value)} className="input" min={0} />
            </Field>
          </div>
          <Field label="マッチングユーザID">
            <input type="text" value={form.matchingUserId} onChange={(e) => set('matchingUserId', e.target.value)} className="input" />
          </Field>
          <Field label="プロフィール画像URL">
            <input type="text" value={form.userImageUrl} onChange={(e) => set('userImageUrl', e.target.value)} className="input" placeholder="https://..." />
          </Field>

          {isEdit && (
            <Field label="">
              <label className="flex items-center gap-2 text-sm cursor-pointer">
                <input
                  type="checkbox"
                  checked={form.deleted}
                  onChange={(e) => set('deleted', e.target.checked)}
                  className="w-4 h-4"
                />
                <span className="text-red-600">削除済みにする</span>
              </label>
            </Field>
          )}

          <div className="flex gap-3 pt-2">
            <button
              type="button"
              onClick={onCancel}
              className="flex-1 py-2 border border-gray-300 rounded-lg text-sm text-gray-600 hover:bg-gray-50"
            >
              キャンセル
            </button>
            <button
              type="submit"
              disabled={submitting}
              className="flex-1 py-2 bg-blue-500 text-white rounded-lg text-sm font-medium disabled:opacity-50 hover:bg-blue-600"
            >
              {submitting ? '送信中…' : isEdit ? '更新' : '登録'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

function Field({ label, children }: { label: string; children: React.ReactNode }) {
  return (
    <div>
      {label && <label className="block text-sm text-gray-600 mb-1">{label}</label>}
      {children}
    </div>
  );
}
