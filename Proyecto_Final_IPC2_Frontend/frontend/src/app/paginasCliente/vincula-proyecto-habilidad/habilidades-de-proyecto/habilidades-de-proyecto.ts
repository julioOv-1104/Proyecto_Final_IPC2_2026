import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClienteService } from '../../../servicios/cliente.service';
import { HabilidadModel } from '../../../modelos/habilidad-model';
import { AdminService } from '../../../servicios/admin.service';

@Component({
  selector: 'app-habilidades-de-proyecto',
  imports: [FormsModule, CommonModule],
  templateUrl: './habilidades-de-proyecto.html',
  styleUrl: './habilidades-de-proyecto.css',
})
export class HabilidadesDeProyecto {

  constructor(private clienteService: ClienteService, private adminService: AdminService) { }

  @Input() id_proyecto!: number;
  habilidades: HabilidadModel[] = [];
  habilidadesDisponibles: HabilidadModel[] = [];

  ngOnInit() {
    console.log('iniciando... ');
    this.obtenerHabilidades();
    this.obtenerHabilidadesDisponibles();

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

  vincularDesvincular(id_proyecto: number, id_habilidad: number, metodo: number) {
    //se envia 0 en el metodo porque para desvincular y 1 para vincular
    this.clienteService.vincularDesvincularHabilidad(id_proyecto, id_habilidad, metodo).subscribe({
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
