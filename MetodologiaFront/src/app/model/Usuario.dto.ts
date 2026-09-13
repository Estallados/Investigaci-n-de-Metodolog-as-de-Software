import { Role } from './Role';

export interface UsuarioDTO {
  id?: number;
  nombre: string;
  correo: string;
  contrasenia: string;
  role?: Role;
}
