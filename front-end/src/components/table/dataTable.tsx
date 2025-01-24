'use client';

import * as React from 'react';
import { ChevronDownIcon, ChevronUpIcon } from '@radix-ui/react-icons';
import {
  ColumnDef,
  ColumnFiltersState,
  SortingState,
  VisibilityState,
  flexRender,
  getCoreRowModel,
  getFilteredRowModel,
  getPaginationRowModel,
  getSortedRowModel,
  useReactTable,
} from '@tanstack/react-table';

import {
  DropdownMenu,
  DropdownMenuCheckboxItem,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from '../ui/dropdown_menu';
import { Input } from '../ui/input';
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '../ui/table';

import { ScrollArea } from '../ui/scrol_area';
import { FaEdit, FaTrash } from 'react-icons/fa';
import { ChevronLeftIcon, ChevronRightIcon } from 'lucide-react';
import { useState } from 'react';
import { Button, Checkbox, Dropdown } from '@nextui-org/react';

type TableAction = {
  view?: boolean;
  duplicate?: boolean;
  edit?: boolean;
  delete?: boolean;
};
type TableData = { [key: string]: string | number };

type Column = {
  accessorKey: string;
  header: string;
};

interface DataTable {
  title?: string;
  data: any[] | undefined;
  columns: Column[];
  height: string;
  actions: TableAction[];
  onEdit: (data: any) => void;
  onDelete: (data: any) => void;
  pageLimit?: string;
  changePageSize?: (value: string) => void;
  dataForNextPage?: [
    number,
    number,
    React.Dispatch<React.SetStateAction<string>>,
  ];
  dataForPrevPage?: [number, React.Dispatch<React.SetStateAction<string>>];
}

const EditButton: React.FC<{
  data: TableData;
  onEdit: (data: TableData) => void;
}> = ({ data, onEdit }) => {
  return (
    <button className="mx-1" onClick={() => onEdit(data)}>
      <FaEdit className="text-custom-blue cursor-pointer" />
    </button>
  );
};

const DeleteButton: React.FC<{
  data: TableData;
  onDelete: (data: TableData) => void;
}> = ({ data, onDelete }) => {
  return (
    <button className="mx-1" onClick={() => onDelete(data)}>
      <FaTrash className="cursor-pointer text-red-700" />
    </button>
  );
};

export function DataTable({
  title,
  data = [],
  columns,
  height,
  actions,
  onEdit,
  onDelete,
  pageLimit = '10',
  changePageSize,
  dataForNextPage,
  dataForPrevPage,
}: DataTable) {
  const [sorting, setSorting] = useState<SortingState>([]);
  const [columnFilters, setColumnFilters] = useState<ColumnFiltersState>([]);
  const [columnVisibility, setColumnVisibility] = useState<VisibilityState>({});
  const [rowSelection, setRowSelection] = useState({});
  const [globalFilter, setGlobalFilter] = useState('');
  const [isNextDisabled, setIsNextDisabled] = useState(false);
  const [isPrevDisabled, setIsPrevDisabled] = useState(true);
  const [pageSize, setPageSize] = useState(pageLimit);

  const table = useReactTable({
    data,
    columns,
    onSortingChange: setSorting,
    onColumnFiltersChange: setColumnFilters,
    getCoreRowModel: getCoreRowModel(),
    getPaginationRowModel: getPaginationRowModel(),
    getSortedRowModel: getSortedRowModel(),
    getFilteredRowModel: getFilteredRowModel(),
    onColumnVisibilityChange: setColumnVisibility,
    onRowSelectionChange: setRowSelection,
    onGlobalFilterChange: setGlobalFilter,
    state: { sorting, columnFilters, columnVisibility, rowSelection, globalFilter },
  });

  const hasActions = actions.some((action) =>
    Object.values(action).some(Boolean),
  );

  const handlePageSizeChange = (value: string) => {
    if (changePageSize) {
      changePageSize(value);
    }
  };

  const changeNextPage = ([currentPage, totalPages, setGridPageNumber]: [
    number,
    number,
    React.Dispatch<React.SetStateAction<string>>,
  ]) => {
    if (currentPage != null && totalPages != null && currentPage < totalPages - 1) {
      setGridPageNumber((currentPage + 1).toString());
      setIsNextDisabled(currentPage + 1 >= totalPages);
      setIsPrevDisabled(false);
    }
  };

  const changePrevPage = ([currentPage, setGridPageNumber]: [
    number,
    React.Dispatch<React.SetStateAction<string>>,
  ]) => {
    if (currentPage != null && currentPage > 0) {
      setGridPageNumber((currentPage - 1).toString());
      setIsPrevDisabled(currentPage - 1 <= 0);
      setIsNextDisabled(false);
    }
  };

  return (
    <div className="w-full">
      <div className="flex items-center justify-between py-4">
        <Input
          placeholder="Filter data..."
          className="mr-2  max-w-sm rounded-md shadow-sm focus:border-blue-400 focus:outline-none"
          value={globalFilter ?? ''}
          onChange={(e) => table.setGlobalFilter(e.target.value)}
        />
      </div>
      <div className="rounded-md border">
        <Table>
          <ScrollArea style={{ minHeight: 200, height }}>
            <TableHeader>
              {table.getHeaderGroups().map((headerGroup) => (
                <TableRow
                  key={headerGroup.id}
                  className="bg-sky-200 dark:bg-sky-900 text-center"
                >

                  {headerGroup.headers.map((header) =>
                    header.isPlaceholder ? null : (
                      <TableHead key={header.id}>
                        {header.column.getCanSort() ? (
                          <div
                            className="flex cursor-pointer items-center"
                            onClick={header.column.getToggleSortingHandler()}
                          >
                            {flexRender(
                              header.column.columnDef.header,
                              header.getContext(),
                            )}
                            {header.column.getIsSorted() === 'asc' && (
                              <ChevronUpIcon className="ml-2 h-4 w-4" />
                            )}
                            {header.column.getIsSorted() === 'desc' && (
                              <ChevronDownIcon className="ml-2 h-4 w-4" />
                            )}
                          </div>
                        ) : (
                          flexRender(
                            header.column.columnDef.header,
                            header.getContext(),
                          )
                        )}
                      </TableHead>
                    ),
                  )}
                  {hasActions && <TableHead>Actions</TableHead>}
                </TableRow>
              ))}
            </TableHeader>
            <TableBody>
              {table.getRowModel().rows.map((row, index) => (
                <TableRow key={row.id} className={index % 2 === 0 ? 'bg-white dark:bg-gray-800' : 'bg-gray-200 dark:bg-gray-900'}>
                  {row.getVisibleCells().map((cell) => (
                    <TableCell key={cell.id}>
                      {flexRender(cell.column.columnDef.cell, cell.getContext())}
                    </TableCell>
                  ))}
                  {hasActions && (
                    <TableCell>
                      <div className="flex justify-center">
                        {actions.map((action, i) => (
                          <React.Fragment key={i}>
                            {action.edit && <EditButton data={row.original} onEdit={onEdit} />}
                            {action.delete && <DeleteButton data={row.original} onDelete={onDelete} />}
                          </React.Fragment>
                        ))}
                      </div>
                    </TableCell>
                  )}
                </TableRow>
              ))}
            </TableBody>
          </ScrollArea>
        </Table>
      </div>
      {/* <div className="flex items-center justify-end space-x-2 py-2">
      <DropdownMenu>
        <DropdownMenuTrigger>
          <Button>
            Rows per page: {pageSize}
          </Button>
        </DropdownMenuTrigger>
        <DropdownMenuContent>
          <DropdownMenuItem onClick={() => handlePageSizeChange('10')}>10</DropdownMenuItem>
          <DropdownMenuItem onClick={() => handlePageSizeChange('20')}>20</DropdownMenuItem>
          <DropdownMenuItem onClick={() => handlePageSizeChange('50')}>50</DropdownMenuItem>
        </DropdownMenuContent>
      </DropdownMenu>

        <div className="space-x-2">
          <Button
            variant="bordered"
            size="sm"
            disabled={isPrevDisabled}
            onClick={() => changePrevPage(dataForPrevPage!)}
            className="h-[25px] w-[30px] p-0"
          >
            <ChevronLeftIcon className="h-4 w-4" />
          </Button>
          <Button
            size="sm"
            disabled={isNextDisabled}
            onClick={() => changeNextPage(dataForNextPage!)}
            className="h-[25px] w-[30px] p-0"
          >
            <ChevronRightIcon className="h-4 w-4" />
          </Button>
        </div>
      </div> */}
    </div>
  );
}
