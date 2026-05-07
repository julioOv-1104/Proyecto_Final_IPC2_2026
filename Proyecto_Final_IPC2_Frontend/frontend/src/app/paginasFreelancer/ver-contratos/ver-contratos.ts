import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MenuFreelancer } from '../../menus/menu-freelancer/menu-freelancer';
import { FreelancerService } from '../../servicios/freelancer.service';
import { ContratosModel } from '../../modelos/contratos-model';

@Component({
  selector: 'app-ver-contratos',
  imports: [FormsModule, CommonModule, MenuFreelancer],
  templateUrl: './ver-contratos.html',
  styleUrl: './ver-contratos.css',
})
export class VerContratos {

  constructor(private freelancerService: FreelancerService) { }

  contratos: ContratosModel[] = [];
  mensajeError: string | null = null;
  mensajeError2: string | null = null;
  id_usuario: number = 0;
  archivo_url: string = '';
  descripcion: string = '';
  id_contrato: number = 0;
  id_proyecto: number = 0;
  activo: boolean = false;
  fecha: Date = new Date;

  ngOnInit() {
    const id = sessionStorage.getItem('usuario_id');
    if (id) {
      this.id_usuario = parseInt(id);
    }

    this.obtenerContratos();

  }

  obtenerContratos() {

    this.freelancerService.obtenerContratosFreelancer(this.id_usuario).subscribe({

      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError2 = response.mensaje;
          return;
        }

        console.log('Contratos cargados con exito');
        this.contratos = response;
        return;

      }



    });

  }

  entregar() { 

    if(!this.descripcion || !this.archivo_url){

      this.mensajeError = ('Por favor complete los campos obligatorios');
      return;
    }

    this.freelancerService.enviarEntrega(this.id_contrato, this.id_proyecto, 
      this.descripcion, this.archivo_url).subscribe({

      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          return;
        }

        console.log('Entrega enviada con exito');
        this.mensajeError = response.mensaje;
        return;

      }



    });

  }

  mostrarFormulario(id_proyecto: number, fecha: Date, id_contrato: number) {

    this.fecha = fecha;
    this.id_contrato = id_contrato;
    this.id_proyecto = id_proyecto;
    this.activo = true;

  }
}
