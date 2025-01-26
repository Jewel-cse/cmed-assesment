import React from 'react';
import ReactDOM from 'react-dom';

interface PopUpModalProps {
  open: boolean;
  title?: string;
  onClose: () => void;
  width?: string;
  children: React.ReactNode;
}

export default function PopUpModal({
  open,
  title = '',
  onClose,
  width = '400px',
  children,
}: PopUpModalProps) {
  if (!open) return null;

  return ReactDOM.createPortal(
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
      <div
        className={`relative bg-white rounded-lg shadow-lg p-6`}
        style={{ width }}
      >
        {title && (
          <div className="mb-4 flex justify-between items-center">
            <h2 className="text-lg font-medium text-gray-900">{title}</h2>
            <button
              onClick={onClose}
              className="text-gray-400 hover:text-gray-600 transition"
            >
              ✕
            </button>
          </div>
        )}
        <div>{children}</div>
      </div>
    </div>,
    document.body
  );
}
