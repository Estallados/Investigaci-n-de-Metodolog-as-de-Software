import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { UsuarioDTO } from '../model/Usuario.dto';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private apiUrl =
    'https://investigaci-n-de-metodolog-as-de-horr.onrender.com/usuarios';

  constructor(private http: HttpClient) {}

  listarUsuarios(): Observable<UsuarioDTO[]> {
    return this.http.get<UsuarioDTO[]>(
      `${this.apiUrl}/ListarUsuarios`
    );
  }

  obtenerUsuario(id: number): Observable<UsuarioDTO> {
    return this.http.get<UsuarioDTO>(
      `${this.apiUrl}/obtener/${id}`
    );
  }

  obtenerUsuarioPorCorreo(correo: string): Observable<UsuarioDTO> {
    return this.http.get<UsuarioDTO>(
      `${this.apiUrl}/obtenercorreo`,
      {
        params: {
          correo: correo
        }
      }
    );
  }

  crearUsuario(usuario: UsuarioDTO): Observable<UsuarioDTO> {
    return this.http.post<UsuarioDTO>(
      `${this.apiUrl}/crear`,
      usuario
    );
  }

  actualizarUsuario(
    id: number,
    usuario: UsuarioDTO
  ): Observable<UsuarioDTO> {

    return this.http.put<UsuarioDTO>(
      `${this.apiUrl}/actualizar/${id}`,
      usuario
    );
  }

  eliminarUsuario(id: number): Observable<void> {
    return this.http.delete<void>(
      `${this.apiUrl}/eliminar/${id}`
    );
  }

  login(
    correo: string,
    contrasenia: string
  ): Observable<number> {

    const datos = {
      correo: correo,
      contrasenia: contrasenia
    };

    return this.http.post<number>(
      `${this.apiUrl}/login`,
      datos
    );
  }
}
