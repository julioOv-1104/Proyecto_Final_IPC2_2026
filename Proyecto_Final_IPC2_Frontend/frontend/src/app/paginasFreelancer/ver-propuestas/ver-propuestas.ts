import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MenuFreelancer } from '../../menus/menu-freelancer/menu-freelancer';
import { FreelancerService } from '../../servicios/freelancer.service';
import { PropuestaModel } from '../../modelos/propuesta-model';

@Component({
  selector: 'app-ver-propuestas',
  imports: [FormsModule, CommonModule, MenuFreelancer],
  templateUrl: './ver-propuestas.html',
  styleUrl: './ver-propuestas.css',
})
export class VerPropuestas {

  constructor(private freelancerService: FreelancerService) { }

  propuestas: PropuestaModel[] = [];
  mensajeError: string | null = null;
  id_usuario: number = 0;

  ngOnInit() {
    const id = sessionStorage.getItem('usuario_id');
    if (id) {
      this.id_usuario = parseInt(id);
    }

    this.obtenerPrpuestas();
  }

  obtenerPrpuestas() {

    this.freelancerService.obtenerPropuestasFreelancer(this.id_usuario).subscribe({

      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          return;
        }

        console.log('Propuestas cargadas con exito');
        this.propuestas = response;
        return;

      }



    });
  }

  retirar(id_propuesta: number, id_proyecto: number){

    this.freelancerService.retirarPropuestad(id_propuesta, id_proyecto).subscribe({

      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          return;
        }

        console.log('Propuesta retirada con exito');
        this.mensajeError = response.mensaje;
        this.obtenerPrpuestas();
        return;

      }



    });

  }

}
