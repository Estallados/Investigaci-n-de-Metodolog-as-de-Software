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
    this.nombreProyecto = '';
    this.descripcionProyecto = '';
  }

  crearProyecto() {

    console.log('Nombre:', this.nombreProyecto);
    console.log('Descripción:', this.descripcionProyecto);

    // Service

    this.cerrarModal();
  }
}
