import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {map, Observable} from 'rxjs';

import { ProyectoDTO } from '../model/Proyecto.dto';

@Injectable({
  providedIn: 'root'
})
export class ProyectoService {

  private apiUrl =
    'https://investigaci-n-de-metodolog-as-de-horr.onrender.com/proyectos';

  constructor(private http: HttpClient) {}

  listarProyectos(): Observable<ProyectoDTO[]> {
    return this.http.get<any[]>(
      `${this.apiUrl}/ListarProyectos`
    ).pipe(
      map(proyectos =>
        proyectos.map(proyecto => new ProyectoDTO(proyecto))
      )
    );
  }

  obtenerProyecto(id: number): Observable<ProyectoDTO> {
    return this.http.get<any>(
      `${this.apiUrl}/obtener/${id}`
    ).pipe(
      map(proyecto => new ProyectoDTO(proyecto))
    );
  }

  crearProyecto(proyecto: any): Observable<ProyectoDTO> {
    return this.http.post<any>(
      `${this.apiUrl}/crear`,
      proyecto
    ).pipe(
      map(respuesta => new ProyectoDTO(respuesta))
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
