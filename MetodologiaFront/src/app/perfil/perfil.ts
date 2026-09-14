import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

import { UsuarioService } from '../services/usuario.service';
import { UsuarioDTO } from '../model/Usuario.dto';

@Component({
  selector: 'app-perfil',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './perfil.html'
})
export class Perfil implements OnInit {

  usuario: UsuarioDTO | null = null;

  constructor(
    private usuarioService: UsuarioService,
    private router: Router
  ) {
  }

  ngOnInit(): void {

    this.usuario = this.usuarioService.usuarioActual;

    console.log('Usuario en perfil:', this.usuario);


    if (this.usuario === null) {
      this.router.navigate(['/login']);
    }
  }
}
