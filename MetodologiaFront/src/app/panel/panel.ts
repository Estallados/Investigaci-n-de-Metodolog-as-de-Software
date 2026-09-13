import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  imports: [RouterLink, FormsModule],
  selector: 'app-panel',
  templateUrl: './panel.html',
})
export class Panel {
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
