'use client'

import PrescriptionPage from "./prescriptions/page";


export default function AppPage() {

  if (typeof window === "undefined") {
    return null;
  }
  const accessToken = sessionStorage.getItem("accessToken");
  if (!accessToken) {
    window.location.href = "/login";
    return null;
  }
  
  return (
    <div className="container mx-auto flex item-center justify-center bg-red-200 p-2">
      <PrescriptionPage/>
    </div>
  );
}
