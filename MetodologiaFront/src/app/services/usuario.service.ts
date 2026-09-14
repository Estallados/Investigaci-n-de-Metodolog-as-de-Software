import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { UsuarioDTO } from '../model/Usuario.dto';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  usuarioActual: UsuarioDTO | null = null;
  private apiUrl =
    'https://investigaci-n-de-metodolog-as-de-horr.onrender.com/usuarios';

  constructor(private http: HttpClient) {
  }


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



  crearUsuario(usuario: UsuarioDTO): Observable<string> {

    return this.http.post(
      `${this.apiUrl}/crear`,
      usuario,
      {
        responseType: 'text'
      }
    );
  }



  actualizarUsuario(
    id: number,
    usuario: UsuarioDTO
  ): Observable<string> {

    return this.http.put(
      `${this.apiUrl}/actualizar/${id}`,
      usuario,
      {
        responseType: 'text'
      }
    );
  }



  eliminarUsuario(id: number): Observable<string> {

    return this.http.delete(
      `${this.apiUrl}/eliminar/${id}`,
      {
        responseType: 'text'
      }
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

    return new Observable<number>(observer => {

      this.http.post<number>(
        `${this.apiUrl}/login`,
        datos
      ).subscribe({

        next: (resultado) => {

          // Login correcto
          if (resultado === 0) {

            this.obtenerUsuarioPorCorreo(correo).subscribe({

              next: (usuario) => {

                if (usuario.id === undefined) {
                  observer.error('El usuario obtenido no tiene ID');
                  return;
                }

                this.obtenerUsuario(usuario.id).subscribe({

                  next: (usuarioCompleto) => {

                    this.usuarioActual = usuarioCompleto;

                    console.log(
                      'USUARIO ACTUAL:',
                      this.usuarioActual
                    );

                    observer.next(resultado);
                    observer.complete();
                  },

                  error: (error) => {
                    observer.error(error);
                  }

                });

              },

              error: (error) => {
                observer.error(error);
              }

            });

          } else {

            // Login incorrecto
            observer.next(resultado);
            observer.complete();

          }

        },

        error: (error) => {
          observer.error(error);
        }

      });

    });
  }

}
