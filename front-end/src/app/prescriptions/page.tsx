'use client'

import { skip } from "node:test";
import { useGetAllPrescriptionsQuery, useGetPrescriptionQuery } from "../../store/ApiSlices/prescriptionsApiSlice";
import { useState } from "react";

export default function PrescriptionPage() {

  const[id,setId] = useState(null);

  const{data:prescriptions , isLoading} = useGetAllPrescriptionsQuery();
  const{data:prescription , isLoading:isLoadingPrescription} = useGetPrescriptionQuery(id ?? 12, { skip: id == null }); 
  
  if(isLoading || isLoadingPrescription){
    return <div>Loading...</div>
  }
  console.log(prescriptions);
  console.log(prescription);
  
  return (
    <div className="container mx-auto item-center justify-center sm:flex-col">
    </div>
  );
}
