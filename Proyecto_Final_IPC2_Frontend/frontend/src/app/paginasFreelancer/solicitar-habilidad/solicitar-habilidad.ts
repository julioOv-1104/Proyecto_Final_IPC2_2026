import { Component } from '@angular/core';
import { MenuFreelancer } from '../../menus/menu-freelancer/menu-freelancer';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { FreelancerService } from '../../servicios/freelancer.service';

@Component({
  selector: 'app-solicitar-habilidad',
  imports: [FormsModule, CommonModule,MenuFreelancer],
  templateUrl: './solicitar-habilidad.html',
  styleUrl: './solicitar-habilidad.css',
})
export class SolicitarHabilidad {

    constructor(private freelancerService: FreelancerService) { }

  mensajeError: string | null = null;
  descripcion: string = '';
  nombre: string = '';
  id_usuario: number = 0;

  

   ngOnInit() {
    const id = sessionStorage.getItem('usuario_id');
    if (id) {
      this.id_usuario = parseInt(id);
    }


  }

  solicitar(){

    if (!this.nombre || !this.descripcion) {
      this.mensajeError = 'Por favor, complete todos los campos.';
      return;
    }

    this.freelancerService.solicitarHabilidad(this.id_usuario, this.nombre, this.descripcion).subscribe({
      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          return;
        }

        console.log('Solicitud de habilidad creada con exito');
        this.mensajeError = response.mensaje;
        return;

      }


    });

  }

}
