import { ThemeProvider } from "next-themes";
import StoreProvider from "../store/storeProvider";
import { Metadata } from "next";
import "./globals.css";

export const metadata: Metadata = {
  title: "CMED Health",
  description: `CMED Health is a platform that helps you manage your health records and prescriptions.`,
};

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="en">
      <body>
        <ThemeProvider attribute="class" defaultTheme="light">
          <StoreProvider>
            <div>{children}</div>
          </StoreProvider>
        </ThemeProvider>
      </body>
    </html>
  );
}
