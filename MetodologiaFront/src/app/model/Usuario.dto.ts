import { Role } from './Role';

export interface UsuarioDto {
  id: number;
  nombre: string;
  correo: string;
  contrasenia: string;
  role: Role;
}
