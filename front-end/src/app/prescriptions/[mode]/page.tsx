'use client';

import React, { useEffect, useState } from "react";
import { Input, Textarea, Dropdown, DropdownTrigger, DropdownMenu, DropdownItem, Button, Spacer, SharedSelection } from "@nextui-org/react";
import { Gender, PrescriptionDto, SearchPrescription } from "../../../type/prescription";
import { capitalize } from "../../../components/table/utils";
import { useSearchParams } from "next/navigation";
import NotFound from 'next/error';
import { useCreatePrescriptionMutation, useUpdatePrescriptionMutation } from "../../../store/ApiSlices/prescriptionsApiSlice";
import { FaBackward, FaFastBackward } from "react-icons/fa";
import Link from "next/link";
import { ArrowLeft } from "lucide-react";
import { useErrorNotification } from "../../../store/errorNotifyProvider";
import toast from "react-hot-toast";

const initialPrescriptionData: PrescriptionDto = {
  prescriptionDate: new Date().toISOString().split('T')[0],
  patientName: '',
  patientAge: undefined,
  patientGender: undefined,
  diagnosis: '',
  medicines: '',
  nextVisitDate: '',
};


export default function PrescriptionInnerPage() {
  const searchParams = useSearchParams();

  const [formData, setFormData] = useState<PrescriptionDto>(initialPrescriptionData);
  const [formErrors, setFormErrors] = useState<Record<string, boolean>>({
    prescriptionDate: false,
    patientName: false,
    patientAge: false,
    patientGender: false,
  });

  const [isLoading, setIsLoading] = useState(false)
  const mode = searchParams.get('mode')

  const {notify} = useErrorNotification()

  const [update] = useUpdatePrescriptionMutation();
  const [create, { isLoading: creating }] = useCreatePrescriptionMutation();


  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: name === "patientAge" ? parseInt(value) : value,
    }));
  };

  const handleGenderChange = (e: SharedSelection) => {
    setFormData((prevData) => ({
      ...prevData,
      patientGender: e.currentKey
    }));
  };

  useEffect(() => {
    const getEditData = () => {
      if (mode === 'edit') {
        const editDataSaved = localStorage.getItem('editData');
        const editData = JSON.parse(editDataSaved??'');
        const { publicId,prescriptionDate, patientName, patientAge, patientGender, diagnosis, medicines, nextVisitDate } = editData;
        setFormData({
          publicId,
          prescriptionDate,
          patientName,
          patientAge,
          patientGender,
          diagnosis,
          medicines,
          nextVisitDate
        });
      }
    }
    getEditData();
  }, [mode]);

  const handleSubmit = async () => {
    if (!validateForm()) {
      return; 
    }
    try {
      if (mode === 'create') {
        await create(formData).unwrap();
        toast.success("Prescription created")
      }
      else if (mode === 'edit') {
        await update({ id: formData.publicId!, body: formData }).unwrap();
        toast.success("Prescription updated")
      }
      
    } catch (error:any) {
      notify({
        description:error.data.message
      })
    }
  };

  if (!mode || (mode !== 'create' && mode !== 'edit')) {
    return <NotFound statusCode={404} />
  }

  
  
  const validateForm = () => {
    const errors = {
      prescriptionDate: !formData.prescriptionDate,
      patientName: !formData.patientName && formData.patientName.length < 2,
      patientAge: !formData.patientAge && formData.patientAge! < 0 && formData.patientAge! >150,
      patientGender: !formData.patientGender,
    };
    setFormErrors(errors);
    return !Object.values(errors).some((error) => error);
  };
  

  return (
    <>
      <div className="container mx-auto max-w-lg overflow-x-hidden">
        <h1 className="text-2xl font-bold text-center my-4">{capitalize(Array.isArray(mode) ? mode[0]! : mode!)} Prescription</h1>

        <div className="flex flex-col my-4 mx-4">
          {/* Prescription Date */}
          <Input
            label="Prescription Date"
            className={`mb-4 shadow-lg rounded-lg ${
              formErrors.prescriptionDate ? 'ring-2 ring-red-500' : ''
            }`}
            name="prescriptionDate"
            type="date"
            minLength={8}
            value={formData.prescriptionDate}
            onChange={handleChange}
            fullWidth
            isRequired
          />

          {/* Patient Name */}
          <Input
            label="Patient Name"
            className={`mb-4 shadow-lg rounded-lg ${
              formErrors.patientName ? 'ring-2 ring-red-500' : ''
            }`}
            name="patientName"
            placeholder="Enter Patient Name"
            type="text"
            minLength={2}
            value={formData.patientName}
            onChange={handleChange}
            fullWidth
            isRequired
          />

          {/* Patient Age */}
          <Input
            label="Patient Age"
            className={`mb-4 shadow-lg rounded-lg ${
              formErrors.patientAge ? 'ring-2 ring-red-500' : ''
            }`}
            name="patientAge"
            placeholder="Enter Patient Age"
            type="number"
            maxLength={3}
            max={150}
            value={formData.patientAge?.toString() || ''}
            onChange={handleChange}
            fullWidth
            isRequired
          />

          {/* Patient Gender Dropdown */}
          <div className={`mb-4 ${formErrors.patientGender ? 'ring-2 ring-red-500 p-2 rounded-lg' : ''}`}>
            <Dropdown >
              <DropdownTrigger>
                <Button fullWidth>
                  {formData.patientGender || "Select Gender"}
                </Button>
              </DropdownTrigger>
              <DropdownMenu
                aria-label="Select Gender"
                selectionMode="single"
                onSelectionChange={(e) => handleGenderChange(e)}
              >
                <DropdownItem key={Gender.MALE} value={Gender.MALE}>Male</DropdownItem>
                <DropdownItem key={Gender.FEMALE} value={Gender.FEMALE}>Female</DropdownItem>
                <DropdownItem key={Gender.OTHER} value={Gender.OTHER}>Other</DropdownItem>
              </DropdownMenu>
            </Dropdown>
          </div>

          {/* Diagnosis */}
          <Textarea
            label="Diagnosis"
            className="mb-4 shadow-lg rounded-lg"
            name="diagnosis"
            placeholder="Enter Diagnosis"
            value={formData.diagnosis}
            onChange={handleChange}
            fullWidth
            rows={3}
          />

          {/* Medicines */}
          <Textarea
            label="Medicines"
            className="mb-4 shadow-lg rounded-lg"
            name="medicines"
            placeholder="Enter Medicines"
            value={formData.medicines}
            onChange={handleChange}
            fullWidth
            rows={3}
          />

          {/* Next Visit Date */}
          <Input
            label="Next Visit Date"
            className="mb-4 shadow-lg rounded-lg"
            name="nextVisitDate"
            type="date"
            value={formData.nextVisitDate}
            onChange={handleChange}
            fullWidth
          />
          {/* Submit Button */}
          <Spacer y={1} />
          <Button className="flex item-center justify-center" color="primary" onClick={handleSubmit} >
            {mode === 'create' ? 'Save' : 'Edit'}
          </Button>
        </div>
      </div>
      <div className="flex item-center justify-start">
        <Link href={'/prescriptions'}>
          <Button variant="ghost" className="border-none rounded-full">
            <ArrowLeft />
          </Button>
        </Link>
      </div>
    </>
  );
}
