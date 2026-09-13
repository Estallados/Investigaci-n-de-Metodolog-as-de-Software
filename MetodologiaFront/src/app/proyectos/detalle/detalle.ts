import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {FormsModule} from '@angular/forms';

import {ProyectoDTO} from '../../model/Proyecto.dto';
import {ProyectoService} from '../../services/proyecto.service';
import {UsuarioDTO} from '../../model/Usuario.dto';
import {UsuarioService} from '../../services/usuario.service';

@Component({
  selector: 'app-detalle',
  standalone: true,
  imports: [RouterLink, FormsModule],
  templateUrl: './Detalle.html'
})
export class Detalle implements OnInit {

  proyecto!: ProyectoDTO;

  tab = 'tareas';

  mostrarModalTarea = false;
  mostrarModalIntegrante = false;

  usuarios: UsuarioDTO[] = [];
  usuariosFiltrados: UsuarioDTO[] = [];

  buscarUsuario = '';
  usuarioSeleccionado: UsuarioDTO | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private proyectoService: ProyectoService,
    private usuarioService: UsuarioService,
    private cdr: ChangeDetectorRef
  ) {
  }

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.cargarProyecto(id);
    this.cargarUsuarios();
  }

  cargarProyecto(id: number) {

    this.proyectoService.obtenerProyecto(id).subscribe({
      next: (respuesta) => {
        console.log('PROYECTO:', respuesta);
        this.proyecto = respuesta;
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error('ERROR AL CARGAR PROYECTO:', error);
      }
    });
  }

  cargarUsuarios() {

    this.usuarioService.listarUsuarios().subscribe({

      next: (respuesta) => {

        this.usuarios = respuesta;

        this.usuariosFiltrados = respuesta;

      },
      error: (error) => {
        console.error('ERROR AL CARGAR USUARIOS:', error);
      }

    });
  }

  filtrarUsuarios() {

    const texto = this.buscarUsuario.toLowerCase();

    this.usuariosFiltrados = this.usuarios.filter(usuario =>

      (usuario.nombre || '').toLowerCase().includes(texto) ||

      (usuario.correo || '').toLowerCase().includes(texto)
    );
  }

  seleccionarUsuario(usuario: UsuarioDTO) {

    this.usuarioSeleccionado = usuario;

  }

  agregarUsuario() {

    if (!this.usuarioSeleccionado) {
      return;
    }

    console.log('USUARIO SELECCIONADO:', this.usuarioSeleccionado);

    this.cerrarModalIntegrante();

  }

  cambiarTab(tab: string) {
    this.tab = tab;
  }

  volver() {
    this.router.navigate(['/proyectos']);
  }

  abrirModalTarea() {
    this.mostrarModalTarea = true;
  }

  cerrarModalTarea() {
    this.mostrarModalTarea = false;
  }

  abrirModalIntegrante() {
    this.mostrarModalIntegrante = true;
    this.buscarUsuario = '';
    this.usuarioSeleccionado = null;
    this.usuariosFiltrados = this.usuarios;
  }

  cerrarModalIntegrante() {
    this.mostrarModalIntegrante = false;
    this.buscarUsuario = '';
    this.usuarioSeleccionado = null;
  }
}
