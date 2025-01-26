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
import { PrescriptionDto } from "../../type/prescription";
import { Button, Modal } from "@nextui-org/react";
import { PlusCircledIcon } from "@radix-ui/react-icons";
import { useRouter } from 'next/navigation';
import { useErrorNotification } from "../../store/errorNotifyProvider";
import ConfirmPopUp from "../../components/confirmPopUp";
import toast from "react-hot-toast";

const columns = [
  // { accessorKey: "publicId", header: "Public ID", sortable: false },
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
  const [size, setSize] = useState<number>(5);

  const [confirmAction, setConfirmAction] = useState<() => void>(() => {});
  const [confirmModalOpen, setConfirmModalOpen] = useState(false);
  const [confirmText, setConfirmText] = useState('');


  const { data: prescriptionDataList, isLoading } = useGetAllPrescriptionsQuery(`page=${page}&size=${size}`);
  const [deletePrescription] = useDeletePrescriptionMutation();

  const {notify} = useErrorNotification()

  const handleEdit = (data: PrescriptionDto) => {
    localStorage.setItem('editData', JSON.stringify(data));
    router.push('/prescriptions/m?mode=edit');
  };

  const handleDelete = async(data:PrescriptionDto)=>{
    handleConfirmAction(
      async () => {
        try {
          if(data.publicId === undefined){
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

  if (prescriptionDataList) {
    console.log(prescriptionDataList);
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

  const currentPage = prescriptionDataList?.currentPage ?? 0;
  const totalPages = prescriptionDataList?.totalPages ?? 0;

  return (
    <div className="container mx-auto overflow-x-hidden">
      <div className="flex item-center justify-end my-4">
        <Button className="bg-primary text-semibold " variant="solid" onClick={() => router.push('/prescriptions/m?mode=create')}>
          <PlusCircledIcon className="ml-2 w-4 h-4" />
          Add New Prescription
        </Button>
      </div>
      <DataTable
        columns={columns}
        data={prescriptionDataList?.body ?? []}
        height={"auto"}
        actions={[{ edit: true, delete: true }]}
        onEdit={handleEdit}
        onDelete={handleDelete}
      />
      {/* The pagination section */}
      <div className="flex item-center justify-center mt-4">
        <Pagination className="flex item-center" aria-label="Prescription Pagination">
          <PaginationContent className="flex gap-8 item-center ">
            <PaginationItem>
              <PaginationPrevious
                className="flex flex-row item-center"
                href="#"
                size={"md"}
                style={{ pointerEvents: currentPage === 1 ? 'none' : 'auto', opacity: currentPage === 1 ? 0.5 : 1 }}
                onClick={() => setPage((prev) => (Math.max(Number(prev) - 1, 0)))}
              />
            </PaginationItem>
            {getMiddlePages().map((page) => (
              <PaginationItem key={page}>
                <PaginationLink
                  href="#"
                  size={"md"}
                  onClick={() => setPage(page - 1)}
                  className={currentPage === page ? "font-bold text-blue-500" : ""}
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
                className="flex flex-row item-center"
                href="#"
                size={"md"}
                style={{ pointerEvents: currentPage === totalPages ? 'none' : 'auto', opacity: currentPage === totalPages ? 0.5 : 1 }}
                onClick={() => setPage((prev) => Math.min(prev + 1, totalPages))}
              />
            </PaginationItem>
          </PaginationContent>
        </Pagination>
      </div>

      {/* confirm popup show when delete */}
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
