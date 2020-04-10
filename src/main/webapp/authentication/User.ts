import { Authority } from '@/authentication/Authorities';

export interface User {
  id: string;
  mail: string;
  firstname: string;
  lastname: string;
  authorities: Authority[];
  isActive: boolean;
}
