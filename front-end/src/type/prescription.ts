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
  
    patientAge: number;
  
    patientGender: Gender;
  
    diagnosis?: string; 
  
    medicines?: string; 
  
    nextVisitDate?: string; // ISO string representation of LocalDate (e.g., "2025-01-24")
  }
  
  /**
   * Enum for Gender.
   *
   * Represents the possible gender values for a patient.
   */
  export enum Gender {
    MALE = "MALE",
    FEMALE = "FEMALE",
    OTHER = "OTHER",
  }
  