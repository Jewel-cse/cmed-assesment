import { ThemeProvider } from "next-themes";
import StoreProvider from "../store/storeProvider";
import { Metadata } from "next";
import "./globals.css";
import { NotificationProvider } from "../store/errorNotifyProvider";
import { Toaster } from "react-hot-toast";

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
            <NotificationProvider>
              <Toaster
                position="top-center"
                reverseOrder={false}
              />
              {children}</NotificationProvider>
          </StoreProvider>
        </ThemeProvider>
      </body>
    </html> 
  );
}
