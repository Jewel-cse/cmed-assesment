export function cn(...inputs: (string | undefined | null | boolean)[]): string {
    return inputs.filter(Boolean).join(" ");
  }

export  const prepareQueryString = (
    searchParams: any,
    extraParams?: string,
  ): string => {
    const params = new URLSearchParams();
  
    Object.entries(searchParams).forEach(([key, value]) => {
      if (value || value === 0) {
        params.append(key, String(value));
      }
    });
  
    if (params.toString().length !== 0) {
      return extraParams
        ? extraParams + params.toString()
        : '&sortBy=id&direction=desc&' + params.toString();
    }
    return '';
  };
  