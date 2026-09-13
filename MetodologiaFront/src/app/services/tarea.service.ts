import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { TareaDTO } from '../model/Tarea.dto';

@Injectable({
  providedIn: 'root'
})
export class TareaService {

  private apiUrl =
    'https://investigaci-n-de-metodolog-as-de-horr.onrender.com/tareas';

  constructor(private http: HttpClient) {}

  listarTareas(): Observable<TareaDTO[]> {
    return this.http.get<TareaDTO[]>(
      `${this.apiUrl}/ListarTareas`
    );
  }

  obtenerTarea(id: number): Observable<TareaDTO> {
    return this.http.get<TareaDTO>(
      `${this.apiUrl}/obtener/${id}`
    );
  }

  obtenerTareaPorNombre(nombre: string): Observable<TareaDTO> {
    return this.http.get<TareaDTO>(
      `${this.apiUrl}/obtenernombre`,
      {
        params: {
          nombre: nombre
        }
      }
    );
  }

  crearTarea(tarea: TareaDTO): Observable<TareaDTO> {
    return this.http.post<TareaDTO>(
      `${this.apiUrl}/crear`,
      tarea
    );
  }

  actualizarTarea(
    id: number,
    tarea: TareaDTO
  ): Observable<TareaDTO> {

    return this.http.put<TareaDTO>(
      `${this.apiUrl}/actualizar/${id}`,
      tarea
    );
  }

  eliminarTarea(id: number): Observable<void> {
    return this.http.delete<void>(
      `${this.apiUrl}/eliminar/${id}`
    );
  }
}
