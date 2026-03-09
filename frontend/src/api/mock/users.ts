import type { User, RegisterUserRequest, UpdateUserRequest } from '@/api/types';
import { getStorage, setStorage } from './storage';

const STORAGE_KEY = 'mock_users';
const ID_COUNTER_KEY = 'mock_user_id_counter';

export const MOCK_CURRENT_USER_ID = 'user01';

// プレースホルダ画像URL（pictureName にフルURLを格納）
const INITIAL_USERS: User[] = [
  {
    userId: 'user01',
    userName: '田中 太郎',
    primaryHeadOfficeName: '東京本社',
    departmentName: '開発部',
    officeId: 1,
    floor: 3,
    gender: '男性',
    affiliationYear: 5,
    workingStatus: '出社',
    pictureName: 'https://i.pravatar.cc/150?u=user01',
    deleted: false,
  },
  {
    userId: 'user02',
    userName: '鈴木 花子',
    primaryHeadOfficeName: '大阪支社',
    departmentName: '営業部',
    officeId: 2,
    floor: 2,
    gender: '女性',
    affiliationYear: 3,
    workingStatus: '出社',
    matchingUserId: 'user01',
    pictureName: 'https://i.pravatar.cc/150?u=user02',
    deleted: false,
  },
  {
    userId: 'user03',
    userName: '山田 健一',
    primaryHeadOfficeName: '東京本社',
    departmentName: '人事部',
    officeId: 1,
    floor: 5,
    gender: '男性',
    affiliationYear: 8,
    workingStatus: '不在',
    pictureName: 'https://i.pravatar.cc/150?u=user03',
    deleted: false,
  },
];

function loadUsers(): User[] {
  const stored = getStorage<User[] | null>(STORAGE_KEY, null);
  if (stored === null) {
    setStorage(STORAGE_KEY, INITIAL_USERS);
    return INITIAL_USERS;
  }
  return stored;
}

function nextUserId(): string {
  const counter = getStorage<number>(ID_COUNTER_KEY, 4);
  const id = `user${String(counter).padStart(2, '0')}`;
  setStorage(ID_COUNTER_KEY, counter + 1);
  return id;
}

function delay(ms: number): Promise<void> {
  return new Promise((resolve) => setTimeout(resolve, ms));
}

export async function mockGetUsers(userId?: string): Promise<User[]> {
  await delay(300);
  const users = loadUsers();
  if (userId) {
    return users.filter((u) => u.userId === userId);
  }
  return users.filter((u) => !u.deleted);
}

export async function mockRegisterUser(
  req: RegisterUserRequest
): Promise<{ userId: string }> {
  await delay(300);
  const users = loadUsers();
  const userId = nextUserId();
  const newUser: User = {
    userId,
    userName: req.userName,
    primaryHeadOfficeName: req.primaryHeadOfficeName,
    secondaryHeadOfficeName: req.secondaryHeadOfficeName,
    departmentName: req.departmentName,
    officeId: req.officeId,
    floor: req.floor,
    gender: req.gender,
    affiliationYear: req.affiliationYear,
    workingStatus: req.workingStatus,
    matchingUserId: req.matchingUserId,
    pictureName: req.userImageUrl,
    deleted: false,
  };
  users.push(newUser);
  setStorage(STORAGE_KEY, users);
  return { userId };
}

export async function mockUpdateUser(req: UpdateUserRequest): Promise<void> {
  await delay(300);
  const users = loadUsers();
  const idx = users.findIndex((u) => u.userId === req.userId);
  if (idx === -1) {
    throw {
      statusCode: 400,
      reason: 'ユーザが見つかりません',
      errors: [{ code: 'USER_NOT_FOUND', msg: 'ユーザが見つかりません' }],
    };
  }
  users[idx] = {
    ...users[idx],
    userName: req.userName,
    primaryHeadOfficeName: req.primaryHeadOfficeName,
    secondaryHeadOfficeName: req.secondaryHeadOfficeName,
    departmentName: req.departmentName,
    officeId: req.officeId,
    floor: req.floor,
    gender: req.gender,
    affiliationYear: req.affiliationYear,
    workingStatus: req.workingStatus,
    matchingUserId: req.matchingUserId,
    pictureName: req.userImageUrl ?? users[idx].pictureName,
    deleted: req.deleted,
  };
  setStorage(STORAGE_KEY, users);
}
