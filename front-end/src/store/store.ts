import { configureStore, combineReducers } from '@reduxjs/toolkit';
import authApiSlice from './ApiSlices/authApiSlice';
import authReducer from './reducers/authReducer';
import prescriptionApiSlice from './ApiSlices/prescriptionsApiSlice';

const rootReducer = combineReducers({
    [prescriptionApiSlice.reducerPath]: prescriptionApiSlice.reducer,
    [authApiSlice.reducerPath]: authApiSlice.reducer,
    auth: authReducer
});

export const store = configureStore({
    reducer: rootReducer,
    middleware: (getDefaultMiddleware) =>
        getDefaultMiddleware().concat(prescriptionApiSlice.middleware, authApiSlice.middleware),
});

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch

