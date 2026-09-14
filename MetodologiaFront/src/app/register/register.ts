import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

import { UsuarioService } from '../services/usuario.service';
import { UsuarioDTO } from '../model/Usuario.dto';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    FormsModule,
    RouterLink
  ],
  templateUrl: './register.html'
})
export class Register {

  nombre: string = '';
  correo: string = '';
  contrasenia: string = '';
  confirmarContrasenia: string = '';

  aceptaTerminos: boolean = false;

  mensaje: string = '';

  constructor(
    private usuarioService: UsuarioService
  ) {
  }

  registrar(): void {

    // Limpiar mensaje anterior
    this.mensaje = '';

    // Validar campos vacíos
    if (
      this.nombre.trim() === '' ||
      this.correo.trim() === '' ||
      this.contrasenia.trim() === '' ||
      this.confirmarContrasenia.trim() === ''
    ) {
      this.mensaje = 'Debe completar todos los campos';
      return;
    }

    // Validar contraseñas
    if (this.contrasenia !== this.confirmarContrasenia) {
      this.mensaje = 'Las contraseñas no coinciden';
      return;
    }

    // Validar términos
    if (!this.aceptaTerminos) {
      this.mensaje = 'Debe aceptar los términos y condiciones';
      return;
    }

    // Crear usuario
    const usuario: UsuarioDTO = {
      nombre: this.nombre,
      correo: this.correo,
      contrasenia: this.contrasenia
    };

    // Enviar al backend
    this.usuarioService
      .crearUsuario(usuario)
      .subscribe({

        next: (respuesta: string) => {

          console.log('USUARIO CREADO:', respuesta);

          // FORZAR REDIRECCIÓN AL LOGIN
          window.location.href = '/login';
        },

        error: (error) => {

          console.error(
            'Error al registrar usuario:',
            error
          );

          if (typeof error.error === 'string') {
            this.mensaje = error.error;
          } else {
            this.mensaje = 'No se pudo registrar el usuario';
          }
        }

      });
  }
}
