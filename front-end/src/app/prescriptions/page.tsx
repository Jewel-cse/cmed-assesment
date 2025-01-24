'use client'

import { useState } from "react";
import { useGetAllPrescriptionsQuery, useGetPrescriptionQuery } from "../../store/ApiSlices/prescriptionsApiSlice";
import { PrescriptionDto } from "../../type/prescription";

export default function PrescriptionPage() {
  const [id, setId] = useState<string | undefined>();

  const { data: prescriptions, isLoading } = useGetAllPrescriptionsQuery();
  const { data: prescription, isLoading: isLoadingPrescription } = useGetPrescriptionQuery(id ?? '', { skip: !id });

  if (isLoading || isLoadingPrescription) {
    return <div>Loading...</div>;
  }

  if (prescriptions) {
    console.log(prescriptions);
  }

  return (
    <div className="container mx-auto item-center justify-center sm:flex-col">
      <div>
        <h1>Prescriptions</h1>
      </div>
      <div>
        {prescriptions?.body.length! > 0 ? (
          prescriptions?.body.map((prescription: PrescriptionDto) => (
            <div key={prescription.publicId} className="flex justify-between p-4 border-b">
              <div>{prescription.prescriptionDate}</div>
              <div>{prescription.patientName}</div>
              <div>{prescription.patientAge}</div>
              <div>{prescription.patientGender}</div>
              <div>{prescription.diagnosis}</div>
              <div>{prescription.medicines}</div>
              <div>{prescription.nextVisitDate}</div>
              <div>
                <button onClick={() => setId(prescription.publicId)}>View</button>
              </div>
            </div>
          ))
        ) : (
          <div>No prescriptions available</div>
        )}
      </div>
    </div>
  );
}
