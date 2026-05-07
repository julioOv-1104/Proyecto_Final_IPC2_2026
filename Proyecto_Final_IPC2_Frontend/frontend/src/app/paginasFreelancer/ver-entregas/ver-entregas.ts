import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MenuFreelancer } from '../../menus/menu-freelancer/menu-freelancer';
import { FreelancerService } from '../../servicios/freelancer.service';
import { EntregaModel } from '../../modelos/entrega-model';

@Component({
  selector: 'app-ver-entregas',
  imports: [FormsModule, CommonModule, MenuFreelancer],
  templateUrl: './ver-entregas.html',
  styleUrl: './ver-entregas.css',
})
export class VerEntregas {

  constructor(private freelancerService: FreelancerService) { }

  entregas: EntregaModel[] = [];
  mensajeError: string | null = null;
  id_usuario: number = 0;

  
  ngOnInit() {
    const id = sessionStorage.getItem('usuario_id');
    if (id) {
      this.id_usuario = parseInt(id);
    }

    this.obtenerContratos();

  }

  obtenerContratos() {

    this.freelancerService.obtenerEntregasFreelancer(this.id_usuario).subscribe({

      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          return;
        }

        console.log('Contratos cargados con exito');
        this.entregas = response;
        return;

      }



    });

  }

}
