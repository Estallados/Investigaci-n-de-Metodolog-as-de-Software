import { TareaDTO } from './Tarea.dto';
import { UsuarioDTO } from './Usuario.dto';

export interface ProyectoDTO {
  id: number;
  nombre: string;
  descripcion: string;
  tareas: TareaDTO[];
  usuarios: UsuarioDTO[];
}
