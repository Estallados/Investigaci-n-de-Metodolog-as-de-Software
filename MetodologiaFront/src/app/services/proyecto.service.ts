import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ProyectoDTO } from '../model/Proyecto.dto';

@Injectable({
  providedIn: 'root'
})
export class ProyectoService {

  private apiUrl =
    'https://investigaci-n-de-metodolog-as-de-horr.onrender.com/proyectos';

  constructor(private http: HttpClient) {}

  listarProyectos(): Observable<ProyectoDTO[]> {
    return this.http.get<ProyectoDTO[]>(
      `${this.apiUrl}/ListarProyectos`
    );
  }

  obtenerProyecto(id: number): Observable<ProyectoDTO> {
    return this.http.get<ProyectoDTO>(
      `${this.apiUrl}/obtener/${id}`
    );
  }

  crearProyecto(proyecto: ProyectoDTO): Observable<string> {
    return this.http.post(
      `${this.apiUrl}/crear`,
      proyecto,
      {
        responseType: 'text'
      }
    );
  }

  actualizarProyecto(
    id: number,
    proyecto: ProyectoDTO
  ): Observable<ProyectoDTO> {
    return this.http.put<ProyectoDTO>(
      `${this.apiUrl}/actualizar/${id}`,
      proyecto
    );
  }

  eliminarProyecto(id: number): Observable<void> {
    return this.http.delete<void>(
      `${this.apiUrl}/eliminar/${id}`
    );
  }

  descargarPdf(id: number): Observable<Blob> {
    return this.http.get(
      `${this.apiUrl}/pdf/${id}`,
      {
        responseType: 'blob'
      }
    );
  }
}
