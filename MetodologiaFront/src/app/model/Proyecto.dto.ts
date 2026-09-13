import {TareaDTO} from './Tarea.dto';
import {UsuarioDTO} from './Usuario.dto';

export class ProyectoDTO {
  id!: number;
  nombre!: string;
  descripcion!: string;
  tareas!: TareaDTO[];
  usuarios!: UsuarioDTO[];

  constructor(datos: Partial<ProyectoDTO>) {
    Object.assign(this, datos);
    this.tareas = datos.tareas || [];
    this.usuarios = datos.usuarios || [];
  }

  get totalTareas(): number {
    return this.tareas.length;
  }

  get totalIntegrantes(): number {
    return this.usuarios.length;
  }

  get progreso(): number {
    if (this.tareas.length === 0) {
      return 0;
    }
    const tareasCompletadas = this.tareas.filter(tarea => tarea.estado === 'COMPLETADO').length;
    return Math.round((tareasCompletadas / this.tareas.length) * 100);
  }

}
