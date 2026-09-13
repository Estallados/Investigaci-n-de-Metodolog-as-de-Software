import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {FormsModule} from '@angular/forms';

import {ProyectoDTO} from '../../model/Proyecto.dto';
import {ProyectoService} from '../../services/proyecto.service';
import {UsuarioDTO} from '../../model/Usuario.dto';
import {UsuarioService} from '../../services/usuario.service';
import {TareaDTO} from '../../model/Tarea.dto';
import {Estado} from '../../model/Estado';
import {TareaService} from '../../services/tarea.service';

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
    private tareaService: TareaService,
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

  agregarTarea() {

    const nombre =
      (document.getElementById('nombreTarea') as HTMLInputElement).value;
    const descripcion =
      (document.getElementById('descripcionTarea') as HTMLTextAreaElement).value;
    const fechaInicio =
      (document.getElementById('fechaInicio') as HTMLInputElement).value;
    const fechaEntrega =
      (document.getElementById('fechaEntrega') as HTMLInputElement).value;
    if (!nombre.trim()) {
      return;
    }

    const nuevaTarea: TareaDTO = {
      id: null,
      nombre: nombre,
      descripcion: descripcion,
      fechaInicio: fechaInicio,
      fechaEntrega: fechaEntrega,
      estado: Estado.PENDIENTE
    };

    console.log(
      '1. CREANDO TAREA:',
      nuevaTarea
    );

    this.tareaService
      .crearTarea(nuevaTarea)
      .subscribe({

        next: (respuesta) => {

          console.log(
            '2. TAREA CREADA:',
            respuesta
          );

          this.tareaService
            .obtenerTareaPorNombre(nombre)
            .subscribe({

              next: (tareaCreada) => {

                console.log(
                  '3. TAREA OBTENIDA:',
                  tareaCreada
                );

                if (tareaCreada.id == null) {
                  console.error(
                    'La tarea fue creada pero no se obtuvo su ID'
                  );
                  return;
                }

                this.proyecto.tareas = [
                  ...this.proyecto.tareas,
                  tareaCreada
                ];

                const pr: ProyectoDTO = {
                  id: this.proyecto.id,
                  nombre: this.proyecto.nombre,
                  descripcion: this.proyecto.descripcion,
                  tareas: this.proyecto.tareas,
                  usuarios: this.proyecto.usuarios
                };
                console.log(
                  '4. PROYECTO A ACTUALIZAR:',
                  pr
                );

                this.proyectoService
                  .actualizarProyecto(
                    this.proyecto.id,
                    pr
                  )
                  .subscribe({

                    next: (respuesta) => {

                      console.log(
                        '5. PROYECTO ACTUALIZADO:',
                        respuesta
                      );

                      this.cerrarModalTarea();

                      this.cdr.detectChanges();
                    },

                    error: (error) => {
                      console.error(
                        'ERROR ACTUALIZANDO PROYECTO:',
                        error
                      );

                      console.error(
                        'DETALLE:',
                        error.error
                      );

                      this.cdr.detectChanges();
                    }
                  });
              },

              error: (error) => {
                console.error(
                  'ERROR OBTENIENDO TAREA CREADA:',
                  error
                );
                console.error(
                  'DETALLE:',
                  error.error
                );
                this.cdr.detectChanges();
              }
            });
        },

        error: (error) => {

          console.error(
            'ERROR CREANDO TAREA:',
            error
          );

          console.error(
            'DETALLE:',
            error.error
          );

          this.cdr.detectChanges();
        }
      });
  }

  agregarUsuario() {
    if (!this.usuarioSeleccionado) {
      return;
    }

    console.log('USUARIO SELECCIONADO:', this.usuarioSeleccionado);

    this.proyecto.usuarios = [...this.proyecto.usuarios, this.usuarioSeleccionado];

    this.cerrarModalIntegrante();

    const pr: ProyectoDTO = {
      id: this.proyecto.id,
      nombre: this.proyecto.nombre,
      descripcion: this.proyecto.descripcion,
      tareas: this.proyecto.tareas,
      usuarios: this.proyecto.usuarios
    };

    this.proyectoService.actualizarProyecto(this.proyecto.id, pr).subscribe({
      next: (respuesta) => {
        console.log('PROYECTO ACTUALIZADO:', respuesta);
        this.cerrarModalTarea();

        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error('ERROR AL ACTUALIZAR PROYECTO:', error);
        this.cdr.detectChanges();
      }
    });
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
  }
}
