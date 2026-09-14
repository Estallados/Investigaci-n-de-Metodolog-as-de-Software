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


  // LISTAR USUARIOS
  listarUsuarios(): Observable<UsuarioDTO[]> {

    return this.http.get<UsuarioDTO[]>(
      `${this.apiUrl}/ListarUsuarios`
    );
  }


  // OBTENER USUARIO POR ID
  obtenerUsuario(id: number): Observable<UsuarioDTO> {

    return this.http.get<UsuarioDTO>(
      `${this.apiUrl}/obtener/${id}`
    );
  }


  // OBTENER USUARIO POR CORREO
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


  // CREAR USUARIO
  crearUsuario(usuario: UsuarioDTO): Observable<string> {

    return this.http.post(
      `${this.apiUrl}/crear`,
      usuario,
      {
        responseType: 'text'
      }
    );
  }


  // ACTUALIZAR USUARIO
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


  // ELIMINAR USUARIO
  eliminarUsuario(id: number): Observable<string> {

    return this.http.delete(
      `${this.apiUrl}/eliminar/${id}`,
      {
        responseType: 'text'
      }
    );
  }


  // LOGIN
  login(
    correo: string,
    contrasenia: string
  ): Observable<number> {

    const datos = {
      correo: correo,
      contrasenia: contrasenia
    };

    return new Observable<number>((observer) => {

      this.http.post<number>(
        `${this.apiUrl}/login`,
        datos
      ).subscribe({

        next: (resultado: number) => {

          // 0 = login correcto
          if (resultado === 0) {

            this.obtenerUsuarioPorCorreo(correo)
              .subscribe({

                next: (usuario: UsuarioDTO) => {

                  if (usuario.id === undefined) {

                    observer.error(
                      'El usuario obtenido no tiene ID'
                    );

                    return;
                  }

                  this.obtenerUsuario(usuario.id)
                    .subscribe({

                      next: (usuarioCompleto: UsuarioDTO) => {

                        this.usuarioActual = usuarioCompleto;

                        console.log(
                          'USUARIO ACTUAL:',
                          this.usuarioActual
                        );

                        observer.next(resultado);
                        observer.complete();
                      },

                      error: (error) => {

                        console.error(
                          'Error obteniendo usuario completo:',
                          error
                        );

                        observer.error(error);
                      }

                    });
                },

                error: (error) => {

                  console.error(
                    'Error obteniendo usuario por correo:',
                    error
                  );

                  observer.error(error);
                }

              });

          } else {

            // 1 = correo incorrecto
            // 2 = contraseña incorrecta
            observer.next(resultado);
            observer.complete();
          }
        },

        error: (error) => {

          console.error(
            'Error en el login:',
            error
          );

          observer.error(error);
        }

      });

    });
  }

}
