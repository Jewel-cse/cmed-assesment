import { BaseQueryFn, FetchArgs, fetchBaseQuery, FetchBaseQueryError } from '@reduxjs/toolkit/query';
import { RootState } from './store';

export const baseUrl = process.env.NEXT_PUBLIC_BASE_URL!;

const baseQuery = fetchBaseQuery({
  baseUrl,
  prepareHeaders: (headers, { getState }) => {
    const accessTokenFromRedux = (getState() as RootState).auth.token;
    const u_inf = sessionStorage.getItem('_u_inf');

    const accessToken = u_inf ? JSON.parse(u_inf).accessToken : accessTokenFromRedux;
    if (accessToken) {
      headers.set('Authorization', `Bearer ${accessToken}`);
    }
    return headers;
  },
});

const baseQueryWithReauth: BaseQueryFn<string | FetchArgs, unknown, FetchBaseQueryError> = async (args, api, extraOptions) => {
  const result = await baseQuery(args, api, extraOptions);
  return result;
};

export default baseQueryWithReauth;
