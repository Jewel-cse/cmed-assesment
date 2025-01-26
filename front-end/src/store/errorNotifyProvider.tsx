'use client'
import React, { createContext, useContext, useState } from 'react';
import ErrorNotification from '../components/notification';

type NotificationContextType = {
  notify: (options: { description?: string }) => void;
};

const NotificationContext = createContext<NotificationContextType | undefined>(
  undefined
);

export const NotificationProvider: React.FC<{ children: React.ReactNode }> = ({
  children,
}) => {
  const [notificationState, setNotificationState] = useState<{
    isOpen: boolean;
    description?: string;
  }>({
    isOpen: false,
    description: '',
  });

  const closeNotification = () => {
    setNotificationState({ isOpen: false, description: '' });
  };

  const notify = ({ description = 'An error occurred.' }: { description?: string }) => {
    setNotificationState({ isOpen: true, description });
  };

  return (
    <NotificationContext.Provider value={{ notify }}>
      {children}
      {/* Notification component */}
      <ErrorNotification
        description={notificationState.description}
        isOpen={notificationState.isOpen}
        onClose={closeNotification}
      />
    </NotificationContext.Provider>
  );
};

export const useErrorNotification = (): NotificationContextType => {
  const context = useContext(NotificationContext);
  if (!context) {
    throw new Error('useNotification must be used within a NotificationProvider');
  }
  return context;
};
