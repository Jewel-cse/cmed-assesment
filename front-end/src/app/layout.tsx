import { ThemeProvider } from "next-themes";
import StoreProvider from "../store/storeProvider";
import { Metadata } from "next";
import "./globals.css";

export const metadata: Metadata = {
  title: "Business Portfolio",
  description: `This is our initial business portfolio.
    Our business includes software development using cutting-edge technology
    like Blockchain, Artificial Intelligence, Nodejs, Spring Boot, React, Next.js.
    We develop various software like DApps, e-commerce, and enterprise solutions.`,
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
