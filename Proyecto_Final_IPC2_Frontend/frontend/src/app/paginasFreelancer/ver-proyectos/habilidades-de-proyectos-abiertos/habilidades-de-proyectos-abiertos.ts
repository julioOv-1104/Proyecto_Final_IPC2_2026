import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClienteService } from '../../../servicios/cliente.service';
import { HabilidadModel } from '../../../modelos/habilidad-model';

@Component({
  selector: 'app-habilidades-de-proyectos-abiertos',
  imports: [FormsModule, CommonModule],
  templateUrl: './habilidades-de-proyectos-abiertos.html',
  styleUrl: './habilidades-de-proyectos-abiertos.css',
})
export class HabilidadesDeProyectosAbiertos {

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

}
