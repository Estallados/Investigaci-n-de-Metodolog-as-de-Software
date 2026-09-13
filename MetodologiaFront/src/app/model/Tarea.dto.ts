import { Estado } from './Estado';

export interface TareaDTO {
  id: number;
  nombre: string;
  descripcion: string;
  fechaEntrega: string;
  fechaInicio: string;
  estado: Estado;
}
