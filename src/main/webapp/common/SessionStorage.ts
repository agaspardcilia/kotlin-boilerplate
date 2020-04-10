
const get = (key: string): string | null => window.sessionStorage.getItem(key);

const put = (key: string, value: string): void  => window.sessionStorage.setItem(key, value);

const putObject = (key: string, value: any): void => put(key, JSON.stringify(value));

const getObject = <T>(key: string): T | null => {
  const result = get(key);
  return result ? JSON.parse(result) : null;
};

const remove = (key: string): void => window.sessionStorage.removeItem(key);

export const SessionStorage = {
  get,
  put,
  putObject,
  getObject,
  remove
};


