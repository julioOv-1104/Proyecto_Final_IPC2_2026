import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClienteService } from '../../../servicios/cliente.service';
import { HabilidadModel } from '../../../modelos/habilidad-model';

@Component({
  selector: 'app-habilidades-de-proyecto',
  imports: [FormsModule, CommonModule],
  templateUrl: './habilidades-de-proyecto.html',
  styleUrl: './habilidades-de-proyecto.css',
})
export class HabilidadesDeProyecto {

  constructor(private clienteService: ClienteService) { }

  @Input() id_proyecto!: number;
  habilidades: HabilidadModel[] = [];

  ngOnInit() {
    console.log('iniciando... ');
    this.obtenerHabilidades();

  }

  obtenerHabilidades() {

    this.clienteService.obtenerHabilidadesDeProyecto(this.id_proyecto).subscribe({
      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          console.log(response.mensaje);
          return;
        }

        this.habilidades = response;
        console.log(response.mensaje);
        return;

      }


    });

  }

  vincularDesvincular(id_proyecto: number, id_habilidad: number) {
    //se envia 0 en el metodo porque es para desvincular
    this.clienteService.vincularDesvincularHabilidad(id_proyecto, id_habilidad, 0).subscribe({
      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          console.log(response.mensaje);
          return;
        }

        this.habilidades = response;
        console.log(response.mensaje);
        this.obtenerHabilidades();
        return;

      }


    });

  }

}
