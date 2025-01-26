'use client'
import React from 'react';
import { MdErrorOutline } from 'react-icons/md';
import PopUpModal from './popUpModal';

export interface ErrorNotificationProps {
  description?: string;
  isOpen: boolean;
  onClose: () => void;
}

export default function ErrorNotification({
  description = 'Something went wrong. Please try again.',
  isOpen,
  onClose,
}: ErrorNotificationProps) {
  return (
    <div
      onKeyDown={(e) => {
        if (e.key === 'Enter') {
          onClose();
          e.preventDefault();
        }
      }}
    >
      <PopUpModal
        open={isOpen}
        title="Error"
        onClose={onClose}
        width='400px'
      >
        <div className="flex flex-col items-center text-center gap-4 px-4">
          <MdErrorOutline className="h-12 w-12 text-red-500" />
          <p className="text-sm text-gray-700">{description}</p>
          <button
            onClick={onClose}
            className="px-4 py-2 text-white bg-red-500 rounded-md hover:bg-red-600 transition"
          >
            Dismiss
          </button>
        </div>
      </PopUpModal>
    </div>
  );
}
