'use client'

import { useEffect, useState } from "react";
import { useDeletePrescriptionMutation, useGetAllPrescriptionsQuery, useGetPrescriptionQuery, useUpdatePrescriptionMutation } from "../../store/ApiSlices/prescriptionsApiSlice";
import React from "react";
import { DataTable } from "../../components/table/dataTable";
import {
  Pagination,
  PaginationContent,
  PaginationEllipsis,
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious,
} from "../../components/ui/pagination";
import { intialSearch, PrescriptionDto, SearchPrescription } from "../../type/prescription";
import { Button, Input, Modal } from "@nextui-org/react";
import { PlusCircledIcon } from "@radix-ui/react-icons";
import { useRouter } from 'next/navigation';
import { useErrorNotification } from "../../store/errorNotifyProvider";
import ConfirmPopUp from "../../components/confirmPopUp";
import toast from "react-hot-toast";
import { prepareQueryString } from "../../lib/utils";

const columns = [
  { accessorKey: "prescriptionDate", header: "Prescription Date", sortable: true },
  { accessorKey: "patientName", header: "Patient Name", sortable: true },
  { accessorKey: "patientAge", header: "Patient Age", sortable: true },
  { accessorKey: "patientGender", header: "Patient Gender", sortable: true },
  { accessorKey: "diagnosis", header: "Diagnosis", sortable: false },
  { accessorKey: "medicines", header: "Medicines", sortable: false },
  { accessorKey: "nextVisitDate", header: "Next Visit Date", sortable: true },
];

export default function PrescriptionPage() {
  const router = useRouter();
  const [id, setId] = useState<string | undefined>();

  const [page, setPage] = useState<number>(0);
  const [size, setSize] = useState<number>(10);

  const [confirmAction, setConfirmAction] = useState<() => void>(() => { });
  const [confirmModalOpen, setConfirmModalOpen] = useState(false);
  const [confirmText, setConfirmText] = useState('');

  const [searchForm, setSearchForm] = useState<SearchPrescription>(intialSearch)
  const [queryParams, setQueryParams] = useState<string>('')

  const { data: prescriptionDataList, isLoading } = useGetAllPrescriptionsQuery(`page=${page}&size=${size}${queryParams}`);
  const [deletePrescription] = useDeletePrescriptionMutation();

  const { notify } = useErrorNotification()

  const handleEdit = (data: PrescriptionDto) => {
    localStorage.setItem('editData', JSON.stringify(data));
    router.push('/prescriptions/m?mode=edit');
  };

  const handleDelete = async (data: PrescriptionDto) => {
    handleConfirmAction(
      async () => {
        try {
          if (data.publicId === undefined) {
            notify({
              description: 'Public ID is missing'
            });
            return
          }
          await deletePrescription(data.publicId!).unwrap();
          toast.success('Prescription deleted.');
        } catch (error: any) {
          notify({
            description: error?.data.message
          })
        }
      },
      `Confirm Delete`,
      `Are you sure to delete this prescription?`,
    );
  }

  const handleConfirmAction = (
    action: () => void,
    title: string,
    text: string,
  ) => {
    setConfirmAction(() => action);
    setConfirmText(text);
    setConfirmModalOpen(true);
  };

  if (isLoading) {
    return <div>Loading...</div>;
  }


  const getMiddlePages = () => {
    const totalPages = prescriptionDataList?.totalPages ?? 0;
    const currentPage = prescriptionDataList?.currentPage ?? 0;

    if (totalPages <= 3) {
      return Array.from({ length: totalPages }, (_, i) => i + 1);
    }
    if (currentPage === 1) {
      return [1, 2, 3];
    }
    if (currentPage === totalPages) {
      return [totalPages - 2, totalPages - 1, totalPages];
    }
    return [currentPage - 1, currentPage, currentPage + 1];
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setSearchForm((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const currentPage = prescriptionDataList?.currentPage ?? 0;
  const totalPages = prescriptionDataList?.totalPages ?? 0;

  async function handleSearch() {
    setQueryParams(prepareQueryString(searchForm))
  }

  return (
    <div className="container mx-auto px-4 overflow-x-hidden">
      {/* Search Section */}
      <div className="flex flex-wrap items-center justify-between bg-slate-100 dark:bg-gray-800 rounded-sm gap-4 p-4 mt-4">
        <Input
          label="From Date"
          className="shadow-lg rounded-lg w-full sm:w-auto"
          name="prescriptionFromDate"
          type="date"
          minLength={8}
          value={searchForm?.prescriptionFromDate??''}
          onChange={handleChange}
        />
        <Input
          label="To Date"
          className="shadow-lg rounded-lg w-full sm:w-auto"
          name="prescriptionToDate"
          type="date"
          minLength={8}
          value={searchForm?.prescriptionToDate??''}
          onChange={handleChange}
        />
        <Button
          className="bg-primary shadow-lg rounded-lg w-full sm:w-auto"
          variant="ghost"
          onClick={handleSearch}
        >
          Search
        </Button>
      </div>

      <div className="flex items-center justify-end mt-4 my-2">
        <Button
          className="bg-primary text-semibold"
          variant="solid"
          onClick={() => router.push('/prescriptions/m?mode=create')}
        >
          <PlusCircledIcon className="ml-2 w-4 h-4" />
          Add New Prescription
        </Button>
      </div>

      {/* Data Table */}
      <div className="overflow-x-auto">
        <DataTable
          columns={columns}
          data={prescriptionDataList?.body ?? []}
          height={"auto"}
          actions={[{ edit: true, delete: true }]}
          onEdit={handleEdit}
          onDelete={handleDelete}
        />
      </div>

      {/* Pagination Section */}
      <div className="flex flex-wrap flex-col sm:flex-row items-center justify-center mt-4">
        <Pagination
          className="flex flex-wrap items-center gap-4"
          aria-label="Prescription Pagination"
        >
          <PaginationContent className="flex items-center gap-4">
            <PaginationItem>
              <PaginationPrevious
                className="flex flex-row items-center"
                href="#"
                size={"md"}
                style={{
                  pointerEvents: currentPage === 1 ? 'none' : 'auto',
                  opacity: currentPage === 1 ? 0.5 : 1,
                }}
                onClick={() => setPage((prev) => Math.max(Number(prev) - 1, 0))}
              />
            </PaginationItem>
            {getMiddlePages().map((page) => (
              <PaginationItem key={page}>
                <PaginationLink
                  href="#"
                  size={"md"}
                  onClick={() => setPage(page - 1)}
                  className={
                    currentPage === page ? "font-bold text-blue-500" : ""
                  }
                >
                  {page}
                </PaginationLink>
              </PaginationItem>
            ))}
            {totalPages > 3 && currentPage < totalPages - 2 && (
              <PaginationItem>
                <PaginationEllipsis />
              </PaginationItem>
            )}
            <PaginationItem>
              <PaginationNext
                className="flex flex-row items-center"
                href="#"
                size={"md"}
                style={{
                  pointerEvents: currentPage === totalPages ? 'none' : 'auto',
                  opacity: currentPage === totalPages ? 0.5 : 1,
                }}
                onClick={() => setPage((prev) => Math.min(prev + 1, totalPages))}
              />
            </PaginationItem>
          </PaginationContent>
        </Pagination>
      </div>

      {/* Confirm Pop-up */}
      <ConfirmPopUp
        confirmAction={() => {
          confirmAction();
          setConfirmModalOpen(false);
        }}
        confirmModalOpen={confirmModalOpen}
        setConfirmModalOpen={setConfirmModalOpen}
        confirmText={'OK'}
        innerText={confirmText}
      />
    </div>
  );
}
