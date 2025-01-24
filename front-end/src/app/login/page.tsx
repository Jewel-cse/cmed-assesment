"use client";

import React, { useState } from "react";
import { Input, Button } from "@nextui-org/react";

export default function AuthPage() {
  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    console.log("submitting",formData);
  };

  return (
    <div className="flex items-center justify-end min-h-screen bg-gradient-to-r from-secondary dark:to-black to-secondary px-4">
      <div className="container mx-auto bg-white dark:bg-black rounded-2xl shadow-xl py-8 px-6 w-full max-w-sm">
        <div className="flex justify-center mb-6">
          <button
            className={`text-lg font-semibold hover:text-primary text-gray-400"}`}
          >
            Sign In
          </button>
        </div>
        <form onSubmit={handleSubmit}>
          <h2 className="text-2xl font-semibold text-center mb-4">Welcome Back!</h2>
          <Input
            className="mb-4 shadow-lg rounded-lg"
            name="username"
            placeholder="User Name"
            type="text"
            value={formData.username}
            onChange={handleChange}
            fullWidth
          />
          <Input
            className="mb-4 shadow-lg rounded-lg"
            name="password"
            placeholder="Password"
            type="password"
            value={formData.password}
            onChange={handleChange}
            fullWidth
          />
          <Button
            type="submit"
            className="w-full mb-4 bg-primary"
            color="primary"
            size="lg"
          >
            Sign In
          </Button>
        </form>
      </div>
    </div>
  );
}
