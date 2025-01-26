/**
 * Interface for Prescription Data Transfer Object.
 *
 * This interface is used to define the structure of prescription data in TypeScript.
 *
 * @author Md Jewel Rana
 * @version 1.0
 * @since 1.0
 */
export interface PrescriptionDto {
  publicId?: string;

  prescriptionDate: string; // ISO string representation of LocalDate (e.g., "2025-01-24")

  patientName: string;

  patientAge?: number;

  patientGender?: string;

  diagnosis?: string;

  medicines?: string;

  nextVisitDate?: string; 
}

export const Gender = {
  MALE: 'MALE',
  FEMALE: 'FEMALE',
  OTHER: 'OTHER',
};

