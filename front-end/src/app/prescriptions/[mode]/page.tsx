'use client';

import React, { useEffect, useState } from "react";
import { Input, Textarea, Dropdown, DropdownTrigger, DropdownMenu, DropdownItem, Button, Spacer } from "@nextui-org/react";
import { Gender, PrescriptionDto } from "../../../type/prescription";
import { capitalize } from "../../../components/table/utils";
import { useSearchParams } from "next/navigation";
import NotFound from 'next/error';
import { useCreatePrescriptionMutation, useUpdatePrescriptionMutation } from "../../../store/ApiSlices/prescriptionsApiSlice";

const initialPrescriptionData: PrescriptionDto = {
  prescriptionDate: new Date().toISOString().split('T')[0],
  patientName: '',
  patientAge: undefined,
  patientGender: undefined,
  diagnosis: '',
  medicines: '',
  nextVisitDate: '',
};

export default function PrescriptionPage() {
  const searchParams = useSearchParams()
  
  const [formData, setFormData] = useState<PrescriptionDto>(initialPrescriptionData);

  const [isLoading, setIsLoading] = useState(false)
  const mode = searchParams.get('mode')

  const[update] = useUpdatePrescriptionMutation();
  const[create,{isLoading:creating}] = useCreatePrescriptionMutation();
  

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: name === "patientAge" ? parseInt(value) : value, 
    }));
  };

  const handleGenderChange = (selectedKey: string) => {
    setFormData((prevData) => ({
      ...prevData,
      patientGender: selectedKey as unknown as Gender
    }));
  };

  if(mode ==='edit'){
    const editData = JSON.parse('editData');
    console.log('edit data from local storage :: ', editData);
    const {prescriptionDate,patientName,patientAge,patientGender,diagnosis,medicines,nextVisitDate} = editData;
    setFormData({
      prescriptionDate,
      patientName,
      patientAge,
      patientGender,
      diagnosis,
      medicines,
      nextVisitDate
    });
  }

  const handleSubmit = async() => {
    console.log("Form Data Submitted:", formData);
    console.log('mode is :: ',mode);
    try{
      if(mode==='create'){
        //create api call
        await create(formData).unwrap();
        alert('created!!!')
      }
      else if(mode === 'edit'){
        //update api will call
        await update({id:formData.publicId!, body:formData}).unwrap();
        alert('updated!!!')
      }
    }catch(error){
      alert('error happens')
    }
  };
  
  if (!mode || (mode !== 'create' && mode !== 'edit')) {
    return <NotFound statusCode={404}/>
  }

  return (
    <div className="container mx-auto max-w-lg overflow-x-hidden">
      <h1 className="text-2xl font-bold text-center my-4">{capitalize(Array.isArray(mode) ? mode[0]! : mode!)} Prescription</h1>

      <div className="flex flex-col my-4">
        {/* Prescription Date */}
        <Input
          label="Prescription Date"
          className="mb-4 shadow-lg rounded-lg"
          name="prescriptionDate"
          type="date"
          value={formData.prescriptionDate}
          onChange={handleChange}
          fullWidth
          required
        />

        {/* Patient Name */}
        <Input
          label="Patient Name"
          className="mb-4 shadow-lg rounded-lg"
          name="patientName"
          placeholder="Enter Patient Name"
          type="text"
          value={formData.patientName}
          onChange={handleChange}
          fullWidth
          required
        />

        {/* Patient Age */}
        <Input
          label="Patient Age"
          className="mb-4 shadow-lg rounded-lg"
          name="patientAge"
          placeholder="Enter Patient Age"
          type="number"
          value={formData.patientAge?.toString() || ''}
          onChange={handleChange}
          fullWidth
        />

        {/* Patient Gender Dropdown */}
        <div className="mb-4">
          <Dropdown>
            <DropdownTrigger>
              <Button fullWidth>
                {formData.patientGender || "Select Gender"}
              </Button>
            </DropdownTrigger>
            <DropdownMenu
              aria-label="Select Gender"
              selectionMode="single"
              onSelectionChange={(key) => handleGenderChange(key as string)}
            >
              <DropdownItem key={'MALE'}>Male</DropdownItem>
              <DropdownItem key={'FEMALE'}>Female</DropdownItem>
              <DropdownItem key={'OTHER'}>Other</DropdownItem>
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
        <Button color="primary" onClick={handleSubmit} fullWidth>
          {mode ==='create'? 'Save': 'Edit'}
        </Button>
      </div>
    </div>
  );
}
