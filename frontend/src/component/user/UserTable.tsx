import type { User } from '@/api/types';
import Avatar from '@/component/chat/Avatar';

interface UserTableProps {
  users: User[];
  onEdit: (user: User) => void;
}

const WORKING_STATUS_LABEL: Record<string, string> = {
  出社: '🟢 出社',
  不在: '⚫ 不在',
};

export default function UserTable({ users, onEdit }: UserTableProps) {
  if (users.length === 0) {
    return <p className="text-gray-400 text-sm py-4 text-center">ユーザが見つかりません</p>;
  }

  return (
    <div className="overflow-x-auto">
      <table className="w-full text-sm border-collapse">
        <thead>
          <tr className="bg-gray-50 text-left text-gray-600">
            <th className="px-3 py-2 border-b font-medium">アイコン</th>
            <th className="px-3 py-2 border-b font-medium">ユーザID</th>
            <th className="px-3 py-2 border-b font-medium">ユーザ名</th>
            <th className="px-3 py-2 border-b font-medium">本部名1</th>
            <th className="px-3 py-2 border-b font-medium">部署名</th>
            <th className="px-3 py-2 border-b font-medium">出社ステータス</th>
            <th className="px-3 py-2 border-b font-medium">操作</th>
          </tr>
        </thead>
        <tbody>
          {users.map((user) => (
            <tr key={user.userId} className="hover:bg-gray-50 border-b last:border-b-0">
              <td className="px-3 py-2">
                <Avatar
                  name={user.userName}
                  imageUrl={user.pictureName}
                  size="sm"
                />
              </td>
              <td className="px-3 py-2 font-mono text-xs text-gray-500">{user.userId}</td>
              <td className="px-3 py-2">{user.userName}</td>
              <td className="px-3 py-2 text-gray-500">{user.primaryHeadOfficeName ?? '—'}</td>
              <td className="px-3 py-2 text-gray-500">{user.departmentName ?? '—'}</td>
              <td className="px-3 py-2">
                {WORKING_STATUS_LABEL[user.workingStatus] ?? user.workingStatus}
              </td>
              <td className="px-3 py-2">
                <button
                  onClick={() => onEdit(user)}
                  className="text-blue-500 hover:text-blue-700 text-xs font-medium"
                >
                  編集
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
