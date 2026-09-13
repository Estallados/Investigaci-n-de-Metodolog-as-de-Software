import { TareaDto } from './Tarea.dto';
import { UsuarioDto } from './Usuario.dto';

export interface ProyectoDto {
  id: number;
  nombre: string;
  descripcion: string;
  tareas: TareaDto[];
  usuarios: UsuarioDto[];
}
