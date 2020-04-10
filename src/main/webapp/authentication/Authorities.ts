export type Authority  = 'ADMIN' | 'USER';

export const Authorities: { [k in Authority]: string } = {
  ADMIN: 'administrator',
  USER: 'user'
};
