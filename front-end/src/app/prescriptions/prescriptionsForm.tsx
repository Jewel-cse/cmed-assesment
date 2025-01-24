'use client';
import React, { useState } from 'react';
import { Input, Button, Modal, Spacer, ModalHeader } from '@nextui-org/react';
import { PrescriptionDto } from '../../type/prescription';

interface PrescriptionFormProps {
  initialData?: PrescriptionDto;
  mode: 'CREATE' | 'EDIT';
  onSubmit?: (data: PrescriptionDto) => void;
  onClose: () => void;
}

const PrescriptionForm: React.FC<PrescriptionFormProps> = ({
  initialData,
  mode,
  onSubmit,
  onClose,
}) => {
  const [formData, setFormData] = useState<PrescriptionDto>(
    initialData!
  );
  const [errors, setErrors] = useState<{ [key: string]: string }>({});
  const [isConfirmModalOpen, setConfirmModalOpen] = useState(false);

  const handleChange = (name: keyof PrescriptionDto, value: string | number) => {
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const validateForm = () => {
    // const newErrors: { [key: string]: string } = {};
    // if (!formData.prescriptionDate) {
    //   newErrors.prescriptionDate = 'Prescription date is mandatory';
    // }
    // if (!formData.patientName) {
    //   newErrors.patientName = 'Patient name is mandatory';
    // } else if (formData.patientName.length > 100) {
    //   newErrors.patientName = 'Patient name must be at most 100 characters';
    // }
    // if (formData.patientAge === null || formData.patientAge < 0 || formData.patientAge > 150) {
    //   newErrors.patientAge = 'Patient age must be between 0 and 150';
    // }
    // if (!formData.patientGender) {
    //   newErrors.patientGender = 'Patient gender is mandatory';
    // }
    // setErrors(newErrors);
    // return Object.keys(newErrors).length === 0;
    return true;
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (validateForm() && onSubmit) {
      onSubmit(formData);
    }
  };
  console.log('formData', formData);
  console.log('mode :: ',mode)

  return (
    <>
      <h1>mode is : {mode}</h1>
      <form onSubmit={handleSubmit} >
        <h2 className="text-2xl font-semibold text-center mb-4" >
          {mode === 'CREATE' ? 'Create Prescription' : 'Edit Prescription'
          }
        </h2>
        < Spacer y={1} />
        <Input
          fullWidth
          label="Prescription Date"
          type="date"
          value={formData.prescriptionDate || ''}
          onChange={(e) => handleChange('prescriptionDate', e.target.value)}
        />
        <Spacer y={1} />
        < Input
          fullWidth
          label="Patient Name"
          value={formData.patientName}
          onChange={(e) => handleChange('patientName', e.target.value)}
        />
        <Spacer y={1} />
        < Input
          fullWidth
          label="Patient Age"
          type="number"
          value={formData.patientAge?.toString() || ''}
          onChange={(e) => handleChange('patientAge', parseInt(e.target.value, 10))}
        />
        <Spacer y={1} />
        < Input
          fullWidth
          label="Patient Gender"
          value={formData.patientGender || ''}
          onChange={(e) => handleChange('patientGender', e.target.value)}
          placeholder="Male, Female, or Other"
          
        />
        <Spacer y={1} />
        < Input
          fullWidth
          label="Diagnosis"
          value={formData.diagnosis || ''}
          onChange={(e) => handleChange('diagnosis', e.target.value)}
        />
        < Spacer y={1} />
        <Input
          fullWidth
          label="Medicines"
          value={formData.medicines || ''}
          onChange={(e) => handleChange('medicines', e.target.value)}
        />
        < Spacer y={1} />
        <Input
          fullWidth
          label="Next Visit Date"
          type="date"
          value={formData.nextVisitDate || ''}
          onChange={(e) => handleChange('nextVisitDate', e.target.value)}
        />
        < Spacer y={1.5} />
        <div className="flex justify-end gap-4" >
          <Button onPress={onClose} >
            Cancel
          </Button>
          < Button type="submit" >
            {mode === 'CREATE' ? 'Create' : 'Update'}
          </Button>
        </div>
      </form>
    </>
  );
};

export default PrescriptionForm;
