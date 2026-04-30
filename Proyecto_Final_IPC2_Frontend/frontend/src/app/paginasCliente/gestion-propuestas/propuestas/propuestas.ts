import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Input } from '@angular/core';
import { PropuestaModel } from '../../../modelos/propuesta-model';
import { ClienteService } from '../../../servicios/cliente.service';

@Component({
  selector: 'app-propuestas',
  imports: [FormsModule, CommonModule],
  templateUrl: './propuestas.html',
  styleUrl: './propuestas.css',
})
export class Propuestas {

  constructor(private clienteService: ClienteService) { }

  propuestas: PropuestaModel[] = [];
  mensajeError: string | null = null;
  id_propuesta: number = 0;
  @Input() id_usuario: number = 0;
  @Input() id_proyecto: number = 0;
  motivo: string = '';

  ngOnInit() {
    this.obtenerPrpuestas();
    console.log('id proyecto:' + this.id_proyecto);
  }

  aceptarPropuesta(id_propuesta: number) {

    this.clienteService.aceptarPropuesta(id_propuesta, this.id_usuario).subscribe({

      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          return;
        }

        console.log('Propuesta aceptada con exito');
        this.mensajeError = response.mensaje;
        this.obtenerPrpuestas();
        return;

      }



    });


  }

  rechazar() {

    this.clienteService.rechazarPropuesta(this.id_propuesta, this.motivo).subscribe({

      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          return;
        }

        console.log('Propuesta rechazada con exito');
        this.mensajeError = response.mensaje;
        this.obtenerPrpuestas();
        return;

      }



    });



  }

  obtenerPrpuestas() {

    this.clienteService.obtenerPropuestas(this.id_proyecto).subscribe({

      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          return;
        }

        console.log('Propuestas cargadas con exito');
        console.log('Propuesta = ' + response.monto);
        this.propuestas = response;
        return;

      }



    });
  }

}
