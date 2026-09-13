import { Estado } from './Estado';

export interface TareaDto {
  id: number;
  nombre: string;
  descripcion: string;
  fechaEntrega: string;
  fechaInicio: string;
  estado: Estado;
}
