import {
  Component,
  OnInit,
  ChangeDetectorRef
} from '@angular/core';

import {
  Router,
  RouterLink
} from '@angular/router';

import { UsuarioService } from '../services/usuario.service';
import { ProyectoService } from '../services/proyecto.service';

import { UsuarioDTO } from '../model/Usuario.dto';
import { ProyectoDTO } from '../model/Proyecto.dto';
import { Estado } from '../model/Estado';

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

  proyectos: ProyectoDTO[] = [];

  constructor(
    private usuarioService: UsuarioService,
    private proyectoService: ProyectoService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {

    this.usuario =
      this.usuarioService.usuarioActual;

    console.log(
      'Usuario en perfil:',
      this.usuario
    );

    if (!this.usuario) {

      this.router.navigate(['/login']);

      return;
    }

    this.cargarProyectos();
  }


  cargarProyectos(): void {

    this.proyectoService
      .listarProyectos()
      .subscribe({

        next: (respuesta) => {

          console.log(
            'RESPUESTA PROYECTOS PERFIL:',
            respuesta
          );

          const usuarioActual =
            this.usuarioService.usuarioActual;

          if (
            !usuarioActual ||
            usuarioActual.id === undefined
          ) {

            console.error(
              'No hay usuario actual'
            );

            this.proyectos = [];

            return;
          }


          this.proyectos = [];


          for (const proyecto of respuesta) {

            const usuarioEstaEnProyecto =
              proyecto.usuarios.some(
                usuario =>
                  usuario.id === usuarioActual.id
              );


            if (usuarioEstaEnProyecto) {

              this.proyectos.push(
                proyecto
              );

            }

          }


          this.cdr.detectChanges();


          console.log(
            'PROYECTOS DEL PERFIL:',
            this.proyectos
          );

          console.log(
            'CANTIDAD PROYECTOS:',
            this.proyectos.length
          );

          console.log(
            'TAREAS TOTALES:',
            this.calcularTareasTotales()
          );

          console.log(
            'COMPLETADAS:',
            this.calcularTareasCompletadas()
          );

          console.log(
            'PENDIENTES:',
            this.calcularTareasPendientes()
          );

        },


        error: (error) => {

          console.error(
            'ERROR AL CARGAR PROYECTOS:',
            error
          );

        }

      });

  }


  calcularTareasTotales(): number {

    let total = 0;

    for (const proyecto of this.proyectos) {

      total += proyecto.tareas.length;

    }

    return total;
  }


  calcularTareasCompletadas(): number {

    let completadas = 0;

    for (const proyecto of this.proyectos) {

      for (const tarea of proyecto.tareas) {

        if (
          tarea.estado === Estado.COMPLETADO
        ) {

          completadas++;

        }

      }

    }

    return completadas;
  }


  calcularTareasEnCurso(): number {

    let total = 0;

    for (const proyecto of this.proyectos) {

      for (const tarea of proyecto.tareas) {

        if (
          tarea.estado === Estado.EN_CURSO
        ) {

          total++;

        }

      }

    }

    return total;
  }


  calcularTareasPendientes(): number {

    let total = 0;

    for (const proyecto of this.proyectos) {

      for (const tarea of proyecto.tareas) {

        if (
          tarea.estado === Estado.PENDIENTE
        ) {

          total++;

        }

      }

    }

    return total;
  }


  calcularPorcentajeTareas(): number {

    const total =
      this.calcularTareasTotales();

    if (total === 0) {

      return 0;

    }

    const completadas =
      this.calcularTareasCompletadas();

    return Math.round(
      (completadas / total) * 100
    );

  }

}
