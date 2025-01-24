import { ReactNode } from "react";
import Header from "../../components/Header";
import Footer from "../../components/Footer";
import { Metadata } from "next";

export const metadata: Metadata = {
  icons:'/cmed-logo.png',
  title: "CMED Health",
  description: `CMED Health is a platform that helps you manage your health records and prescriptions.`,
};

export default function HomeLayout({ children }: { children: ReactNode }) {

  return (
    <div className="min-h-screen flex flex-col bg-background">
      <Header />
      <main className="flex-1 mt-12">{children}</main>
      <Footer />
    </div>
  );
}
