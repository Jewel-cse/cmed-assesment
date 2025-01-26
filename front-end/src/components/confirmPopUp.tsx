import { ExclamationTriangleIcon } from '@radix-ui/react-icons';
import PopUpModal from './popUpModal';
import { Button } from '@nextui-org/react';

interface ConfirmPopUpProps {
  confirmModalOpen: boolean;
  setConfirmModalOpen: (open: boolean) => void;
  title?: string;
  innerText: string;
  confirmAction: () => void;
  cancelText?: string;
  confirmText?: string;
  width?: string;
}

const ConfirmPopUp: React.FC<ConfirmPopUpProps> = ({
  confirmModalOpen,
  setConfirmModalOpen,
  title,
  innerText,
  confirmAction,
  cancelText = 'Cancel',
  confirmText = 'Delete',
  width = '280px',
}) => {
  return (
    <PopUpModal
      open={confirmModalOpen}
      title={title}
      onClose={() => setConfirmModalOpen(false)}
      width={width!}
    >
      <div className="flex flex-row items-center justify-between">
        <div className="m-0 flex text-sm">
          <span><strong>{innerText}</strong></span>
        </div>
        <div>
          <ExclamationTriangleIcon className="m-2 h-10 w-10 text-red-500" />
        </div>
      </div>

      <div className="mt-4 flex justify-end gap-2">
        <Button
          variant="ghost"
          onClick={() => setConfirmModalOpen(false)}
          className="bg-primary text-semibold"
        >
          {cancelText}
        </Button>
        <Button
          variant="ghost"
          className="bg-danger text-semibold"
          onClick={confirmAction}
        >
          {confirmText}
        </Button>
      </div>
    </PopUpModal>
  );
};

export default ConfirmPopUp;
