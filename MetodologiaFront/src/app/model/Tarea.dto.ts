import { Estado } from './Estado';

export interface TareaDTO {
  id: number | null;
  nombre: string;
  descripcion: string;
  fechaEntrega: string;
  fechaInicio: string;
  estado: Estado;
}
