import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Input } from '@angular/core';
import { EntregaModel } from '../../../modelos/entrega-model';
import { ClienteService } from '../../../servicios/cliente.service';

@Component({
  selector: 'app-entregas',
  imports: [FormsModule, CommonModule],
  templateUrl: './entregas.html',
  styleUrl: './entregas.css',
})
export class Entregas {

  constructor(private clienteService: ClienteService) { }
  
    entregas: EntregaModel[] = [];
    mensajeError: string | null = null;
    mensajeError2: string | null = null
    id_entrega: number = 0;
    id_contrato: number = 0;
    @Input() id_usuario: number = 0;
    @Input() id_proyecto: number = 0;
    motivo: string = '';
    motivo_cancelacion: string = '';


    ngOnInit() {
    this.obtenerEntregas();

  }


    obtenerEntregas() {

    this.clienteService.obtenerEntregas(this.id_proyecto).subscribe({

      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          return;
        }

        console.log('Entregas cargadas con exito');
        this.entregas = response;
        return;

      }



    });
  }

    aceptarEntrega(id_entrega:number){

    }

    rechazar(){

      if(!this.id_entrega || this.id_entrega <=0){
        this.mensajeError = 'Ingrese un id de entrega valido';
        return;
      }

      this.clienteService.rechazarEntrega(this.id_proyecto, this.id_entrega, this.motivo).subscribe({

      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          return;
        }

        console.log('Entrega rechazada con exito');
        this.mensajeError = response.mensaje;
        this.obtenerEntregas();
        return;

      }



    });

    }

    cancelarContrato(){}

}
