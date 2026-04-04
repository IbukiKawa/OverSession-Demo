import type { User } from '@/api/types';

const SESSION_KEY = 'oversession_current_user';

export function getCurrentUser(): User | null {
  if (typeof window === 'undefined') return null;
  const raw = sessionStorage.getItem(SESSION_KEY);
  if (!raw) return null;
  try {
    return JSON.parse(raw) as User;
  } catch {
    return null;
  }
}

export function setCurrentUser(user: User): void {
  sessionStorage.setItem(SESSION_KEY, JSON.stringify(user));
}
