import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { FreelancerService } from '../../servicios/freelancer.service';
import { HabilidadModel } from '../../modelos/habilidad-model';
import { AdminService } from '../../servicios/admin.service';
import { MenuFreelancer } from '../../menus/menu-freelancer/menu-freelancer';

@Component({
  selector: 'app-gestionar-habilidades',
  imports: [FormsModule, CommonModule, MenuFreelancer],
  templateUrl: './gestionar-habilidades.html',
  styleUrl: './gestionar-habilidades.css',
})
export class GestionarHabilidades {

   constructor(private freelancerService: FreelancerService, private adminService: AdminService) { }

  id_usuario: number = 0;
    habilidadesDisponibles: HabilidadModel[] = [];
    misHabilidades: HabilidadModel[] = [];

  ngOnInit() {
    const id = sessionStorage.getItem('usuario_id');
    if (id) {
      this.id_usuario = parseInt(id);
    }
    this.obtenerHabilidadesDisponibles();
    this.obtenerMisHabilidades();
  }


    obtenerHabilidadesDisponibles() {

    this.adminService.obtenerHabilidades().subscribe({
      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          console.log(response.mensaje);
          return;
        }

        this.habilidadesDisponibles = response;
        console.log(response.mensaje);
        return;

      }


    });

  }

  obtenerMisHabilidades() {

    this.freelancerService.obtenerHabilidadesFreelancer(this.id_usuario).subscribe({
      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          console.log(response.mensaje);
          return;
        }

        this.misHabilidades = response;
        console.log(response.mensaje);
        return;

      }


    });

  }

  vincularDesvincular(id_habilidad: number, metodo: number) {

    console.log('id_habilidad '+id_habilidad);
    //se envia 0 en el metodo porque para desvincular y 1 para vincular
    this.freelancerService.vincularDesvincularHabilidad(this.id_usuario, id_habilidad, metodo).subscribe({
      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          console.log(response.mensaje);
          return;
        }

        this.misHabilidades = response;
        console.log(response.mensaje);
        this.obtenerMisHabilidades();
        return;

      }


    });

  }

}
