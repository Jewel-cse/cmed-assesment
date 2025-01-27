import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface AuthState {
  token: any;
}

const initialState: AuthState = {
  token: null,
};

const authReducer = createSlice({
  name: "auth",
  initialState,
  reducers: {
    setToken(state, action: PayloadAction<string | null>) {
      state.token = action.payload;
      if (action.payload) {
        sessionStorage.setItem("token", action.payload);
      } else {
        sessionStorage.removeItem("token");
      }
    },
    removeToken(state) {
      state.token = null;
      sessionStorage.removeItem("token");
    }
  }
});

// Export actions and reducer
export const { setToken, removeToken } = authReducer.actions;
export default authReducer.reducer;
