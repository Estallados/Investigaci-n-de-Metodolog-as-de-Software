import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

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
  registroExitoso: boolean = false;

  constructor(
    private usuarioService: UsuarioService,
    private router: Router
  ) {
  }

  registrar(): void {

    if (
      this.nombre.trim() === '' ||
      this.correo.trim() === '' ||
      this.contrasenia.trim() === '' ||
      this.confirmarContrasenia.trim() === ''
    ) {
      this.mensaje = 'Debe completar todos los campos';
      this.registroExitoso = false;
      return;
    }

    if (this.contrasenia !== this.confirmarContrasenia) {
      this.mensaje = 'Las contraseñas no coinciden';
      this.registroExitoso = false;
      return;
    }

    if (!this.aceptaTerminos) {
      this.mensaje = 'Debe aceptar los términos y condiciones';
      this.registroExitoso = false;
      return;
    }

    const usuario: UsuarioDTO = {
      nombre: this.nombre,
      correo: this.correo,
      contrasenia: this.contrasenia
    };

    this.usuarioService
      .crearUsuario(usuario)
      .subscribe({

        next: (respuesta: string) => {

          console.log('Registro correcto:', respuesta);

          this.mensaje = respuesta;
          this.registroExitoso = true;

          this.router.navigate(['/login']);
        },

        error: (error) => {

          console.error('Error al registrar usuario:', error);

          this.registroExitoso = false;

          if (typeof error.error === 'string') {
            this.mensaje = error.error;
          } else {
            this.mensaje = 'No se pudo registrar el usuario';
          }
        }

      });
  }
}
