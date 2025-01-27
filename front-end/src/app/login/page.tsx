"use client";

import React, { useState } from "react";
import { Input, Button } from "@nextui-org/react";
import { useLoginMutation } from "../../store/ApiSlices/authApiSlice";
import { LoaderIcon } from "lucide-react";
import { useRouter } from "next/navigation";
import { setToken } from "../../store/reducers/authReducer";
import { useDispatch } from "react-redux";

export default function AuthPage() {
  const router = useRouter()
  const dispatch = useDispatch();
  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });

  const [login,{isLoading:isLogin,isSuccess,isError}] = useLoginMutation();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = async () => {
    try{
      const res = await login(formData).unwrap();
      console.log('res',res);
      //local storage e token save korte hobe
      sessionStorage.setItem('_u_inf',JSON.stringify(res.body));
      dispatch(setToken(res.body.accessToken))
      
      //redirect to pescriptions page
      router.push('/prescriptions')

    }catch(error){
      alert('some thing went wrong');
    }
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
        <form>
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
            type="button"
            onPress={handleSubmit}
            className="w-full mb-4 bg-primary"
            color="primary"
            size="lg"
            disabled={formData.password=='' || formData.username==''}
          >
            {isLogin && <LoaderIcon className="ml-2 w-4 h-4"/>}
            Sign In
          </Button>
        </form>
      </div>
    </div>
  );
}
