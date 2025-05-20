// components/ImagePreviewModal.tsx
import { Dialog, DialogContent, DialogTrigger } from "@/components/ui/dialog";

interface ImagePreviewModalProps {
  imageUrl: string;
  trigger: React.ReactNode;
}

export const ImagePreviewModal = ({
  imageUrl,
  trigger,
}: ImagePreviewModalProps) => {
  return (
    <Dialog>
      <DialogTrigger asChild>{trigger}</DialogTrigger>
      <DialogContent className="w-[90vw] max-w-4xl p-4">
        <img
          src={imageUrl}
          alt="Room preview"
          className="w-full max-h-[80vh] object-contain rounded"
        />
      </DialogContent>
    </Dialog>
  );
};
