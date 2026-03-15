interface ErrorBannerProps {
  message: string;
  onClose?: () => void;
}

export default function ErrorBanner({ message, onClose }: ErrorBannerProps) {
  return (
    <div className="fixed bottom-20 left-0 right-0 mx-4 flex items-center justify-between bg-red-100 border border-red-400 text-red-700 px-4 py-2 rounded shadow z-50">
      <span className="text-sm">{message}</span>
      {onClose && (
        <button onClick={onClose} className="ml-4 text-red-700 hover:text-red-900 font-bold">
          ✕
        </button>
      )}
    </div>
  );
}
