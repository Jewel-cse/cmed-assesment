import { ReactNode } from "react";
import Header from "../../components/Header";
import Footer from "../../components/Footer";
import { Metadata } from "next";

export const metadata: Metadata = {
  title: "Home - Business Portfolio",
  description: `This is our home page where we showcase our business portfolio.`,
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
