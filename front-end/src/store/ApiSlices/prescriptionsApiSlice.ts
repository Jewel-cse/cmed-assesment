import { PrescriptionDto } from "../../type/prescription";
import createGenericApiSlice from "../genericApiSlice";

const prescriptionApiSlice = createGenericApiSlice<PrescriptionDto, PrescriptionDto>({
    reducerPath: 'prescriptionApi',
    baseUrl: 'prescriptions',
    tagType: 'Prescription',
  });
  
  export const {
    useGetAllQuery: useGetAllPrescriptionsQuery,
    useGetByIdQuery: useGetPrescriptionQuery,
    useCreateMutation: useCreatePrescriptionMutation,
    useUpdateMutation: useUpdatePrescriptionMutation,
    useDeleteMutation: useDeletePrescriptionMutation,
  } = prescriptionApiSlice;
  
  export default prescriptionApiSlice;