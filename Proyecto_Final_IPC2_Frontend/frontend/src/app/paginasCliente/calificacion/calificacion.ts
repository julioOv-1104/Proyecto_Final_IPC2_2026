import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MenuCliente } from '../../menus/menu-cliente/menu-cliente';
import { FreelancerCompletado } from '../../modelos/freelancer-completado';
import { ClienteService } from '../../servicios/cliente.service';

@Component({
  selector: 'app-calificacion',
  imports: [FormsModule, CommonModule, MenuCliente],
  templateUrl: './calificacion.html',
  styleUrl: './calificacion.css',
})
export class Calificacion {

  constructor(private clienteService: ClienteService) { }

  freelancers: FreelancerCompletado[] = [];
  mensajeError: string | null = null;
  id_contrato: number = 0;
  id_freelancer: number = 0;
  estrellas: number = 0;
  comentario: string = '';
  id_usuario: number = 0;

  ngOnInit() {
    const id = sessionStorage.getItem('usuario_id');
    if (id) {
      this.id_usuario = parseInt(id);
    }
    this.obtenerFreelancers();

  }

  obtenerFreelancers() {

    this.clienteService.obtenerFreelancers(this.id_usuario).subscribe(data => {

      this.freelancers = data;

    });

  }

  calificar() {

    if (this.id_contrato <= 0 || this.id_freelancer <= 0 || this.estrellas < 1 || this.estrellas > 5
      || !this.comentario) {
      this.mensajeError = 'Por favor, complete todos los campos correctamente.';
      return;
    }

    this.clienteService.calificar(this.id_contrato, this.id_usuario, this.id_freelancer,
      this.estrellas, this.comentario).subscribe({

        next: (response: any) => {

          // si recibe un error
          if (response.status === 'error') {
            this.mensajeError = response.mensaje;
            return;
          }

          console.log('Calificacion registrada con exito');
          this.mensajeError = response.mensaje;
          return;

        }



      });

  }

}
