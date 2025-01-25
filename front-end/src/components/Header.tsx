"use client";
import Link from "next/link";
import ThemeSwitcher from "./ThemeSwitcher";
import {Tab, Tabs } from "@nextui-org/react";
import Image from "next/image";


export default function Header() {
  return (
    <div className="flex sticky top-0 z-10 flex-col">
      <div className="w-full absolute z-10 ">
        <nav className=" flex flex-wrap items-center justify-between shadow-md dark:shadow-gray-500 mx-auto">
          <Link className="font-bold text-3xl hover:text-primary px-8" href="/">
          <Image 
          src="/cmed-logo.png" 
          width={100} 
          height={80} 
          alt="cmed-logo" 
        />
          </Link>
          <div className="flex font-semibold text-sm">
            <Tabs
              aria-label="Options"
              variant="underlined"
              classNames={{
                tabList: "gap-6 w-full relative rounded-none p-0 border-b border-divider",
                cursor: "w-full bg-primary",
                tab: "max-w-fit px-0 h-12",
                tabContent: "group-data-[selected=true]:text-primary"
              }}
            >
              <Tab
                key="prescriptions"
                title={
                  <div className="flex items-center space-x-2">
                    <span>Prescriptions</span>
                  </div>
                }
              />
              <Tab
                key="reports"
                title={
                  <div className="flex items-center space-x-2">
                    <span>Reports</span>
                  </div>
                }
              />
            </Tabs>
          </div>
          <div className="space-x-4 flex">
            <div>
              <Link href={"/login"} >login</Link>
            </div>
            <ThemeSwitcher />
          </div>
        </nav>
      </div>
    </div>
  );
}
