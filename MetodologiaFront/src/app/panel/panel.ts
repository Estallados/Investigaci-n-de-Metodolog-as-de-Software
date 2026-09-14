import {Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import {ProyectoDTO} from '../model/Proyecto.dto';
import {ProyectoService} from '../services/proyecto.service';
import {UsuarioService} from '../services/usuario.service';
import {Estado} from '../model/Estado';

@Component({
  imports: [RouterLink, FormsModule],
  selector: 'app-panel',
  templateUrl: './panel.html',
  standalone: true
})
export class Panel implements OnInit {
  proyectos: ProyectoDTO[] = [];

  constructor(
    private proyectoService: ProyectoService,
    public usuarioService: UsuarioService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.cargarProyectos();
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

        if (tarea.estado === Estado.COMPLETADO) {
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

        if (tarea.estado === Estado.EN_CURSO) {
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

        if (tarea.estado === Estado.PENDIENTE) {
          total++;
        }

      }
    }

    return total;
  }

  calcularPorcentajeTareas(): number {

    const total = this.calcularTareasTotales();

    if (total === 0) {
      return 0;
    }

    const completadas =
      this.calcularTareasCompletadas();

    return Math.round(
      (completadas / total) * 100
    );
  }

  cargarProyectos() {
    this.proyectoService.listarProyectos().subscribe({
      next: (respuesta) => {

        console.log('1. RESPUESTA RECIBIDA');

        const usuarioActual = this.usuarioService.usuarioActual;

        if (!usuarioActual || usuarioActual.id === undefined) {
          console.error('No hay usuario actual');
          this.proyectos = [];
          return;
        }

        this.proyectos = [];

        for (const proyecto of respuesta) {

          const usuarioEstaEnProyecto =
            proyecto.usuarios.some(
              usuario => usuario.id === usuarioActual.id
            );

          if (usuarioEstaEnProyecto) {
            this.proyectos.push(proyecto);
          }
        }

        this.cdr.detectChanges();

        console.log('PROYECTOS DEL USUARIO:', this.proyectos);
        console.log(
          'CANTIDAD:',
          this.proyectos.length
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

  mostrarModal = false;


  nombreProyecto = '';
  descripcionProyecto = '';

  abrirModal() {
    this.mostrarModal = true;
  }

  cerrarModal() {
    this.mostrarModal = false;
  }

  crearProyecto() {
    this.cerrarModal();

    console.log('Nombre:', this.nombreProyecto);
    console.log('Descripción:', this.descripcionProyecto);

    const nuevoProyecto: ProyectoDTO = {
      id: 0,
      nombre: this.nombreProyecto,
      descripcion: this.descripcionProyecto,
      tareas: [],
      usuarios: []
    };

    this.proyectoService.crearProyecto(nuevoProyecto).subscribe({
      next: (proyectoCreado) => {
        console.log('Proyecto creado:', proyectoCreado);
        // Recargar los proyectos desde el backend
        this.cargarProyectos();
      },
      error: (error) => {
        console.error('ERROR AL CREAR PROYECTO:', error);
      }
    });
  }

  calcularProgreso(id: number): number {

    const proyecto = this.proyectos.find(p => p.id === id);
    if (!proyecto || proyecto.tareas.length === 0) {
      return 0;
    }

    const completadas = proyecto.tareas.filter(
      tarea => tarea.estado === 'COMPLETADO'
    ).length;

    return Math.round(
      (completadas / proyecto.tareas.length) * 100
    );
  }
}
