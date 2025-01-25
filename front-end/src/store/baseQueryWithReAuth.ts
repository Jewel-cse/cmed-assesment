import { BaseQueryFn, FetchArgs, fetchBaseQuery, FetchBaseQueryError } from '@reduxjs/toolkit/query';

export const baseUrl = 'http://localhost:8080/api/v1';

const baseQuery = fetchBaseQuery({
  baseUrl,
  prepareHeaders: (headers, { getState }) => {
    const u_inf = sessionStorage.getItem("_u_inf");
    if(u_inf){
      const {accessToken} = JSON.parse(u_inf);
      console.log('access token ::::  ',accessToken)
      if (accessToken) {
        headers.set('Authorization', `Bearer ${accessToken}`);
      }
    }
    return headers;
  },
});

const baseQueryWithReauth: BaseQueryFn<string | FetchArgs, unknown, FetchBaseQueryError> = async (args, api, extraOptions) => {
  const result = await baseQuery(args, api, extraOptions);
  return result;
};

export default baseQueryWithReauth;
