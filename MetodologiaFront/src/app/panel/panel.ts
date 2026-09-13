import {Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import {ProyectoDTO} from '../model/Proyecto.dto';
import {ProyectoService} from '../services/proyecto.service';

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
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.cargarProyectos();
  }

  cargarProyectos() {
    this.proyectoService.listarProyectos().subscribe({
      next: (respuesta) => {
        console.log('1. RESPUESTA RECIBIDA');

        this.proyectos = respuesta;
        this.cdr.detectChanges();

        console.log(this.proyectos);
        console.log('2. CANTIDAD:', this.proyectos.length);
      },
      error: (error) => {
        console.error('ERROR AL CARGAR PROYECTOS:', error);
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
