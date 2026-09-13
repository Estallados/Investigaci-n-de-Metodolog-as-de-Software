import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { UsuarioService } from '../services/usuario.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {

  correo: string = '';
  contrasenia: string = '';

  mensaje: string = '';

  constructor(
    private usuarioService: UsuarioService,
    private router: Router
  ) {
  }

  iniciarSesion(): void {

    if (this.correo.trim() === '' || this.contrasenia.trim() === '') {
      this.mensaje = 'Debe completar todos los campos';
      return;
    }

    this.usuarioService
      .login(this.correo, this.contrasenia)
      .subscribe({

        next: (respuesta: number) => {

          if (respuesta === 0) {

            this.mensaje = '';

            console.log('Inicio de sesión correcto');

            this.router.navigate(['/panel']);

          } else if (respuesta === 1) {

            this.mensaje = 'El correo no existe';

          } else if (respuesta === 2) {

            this.mensaje = 'La contraseña es incorrecta';

          } else {

            this.mensaje = 'Ocurrió un error al iniciar sesión';

          }

        },

        error: (error) => {

          console.error(
            'Error al iniciar sesión:',
            error
          );

          this.mensaje = 'No se pudo conectar con el servidor';

        }

      });

  }

}
