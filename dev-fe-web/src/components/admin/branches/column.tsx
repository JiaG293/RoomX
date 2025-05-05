import { ColumnDef } from "@tanstack/react-table";
import { Button } from "@/components/ui/button";
import { Link } from "react-router-dom";

export type Branch = {
  branchId: string;
  branchCode: string;
  name: string;
};

export const columns: ColumnDef<Branch>[] = [
  {
    id: "stt",
    header: "STT",
    cell: ({ row }) => row.index + 1,
  },
  {
    accessorKey: "branchCode",
    header: "Mã chi nhánh",
  },
  {
    accessorKey: "name",
    header: "Chi nhánh",
  },
  {
    id: "actions",
    cell: ({ row }) => {
      // const isEven = row.index % 2 === 0;
      return (
        <Button
          asChild
          className="px-3 py-2 bg-[var(--view-button-bg)] text-[var(--view-button-text)] hover:bg-[var(--view-button-bg-hover)] hover:shadow-lg transform transition-transform duration-200 ease-in-out rounded-[var(--view-button-border-radius)] shadow-[var(--view-button-box-shadow)]"
        >
          <Link
            to={`/admin/branches/${row.original.branchId}`}
          >
            Xem chi tiết
          </Link>
        </Button>
      );
    },
  },
];
