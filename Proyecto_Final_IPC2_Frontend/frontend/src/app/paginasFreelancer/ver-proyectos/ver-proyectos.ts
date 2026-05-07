import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MenuFreelancer } from '../../menus/menu-freelancer/menu-freelancer';
import { FreelancerService } from '../../servicios/freelancer.service';
import { ProyectoModel } from '../../modelos/proyecto-model';
import { HabilidadesDeProyectosAbiertos } from './habilidades-de-proyectos-abiertos/habilidades-de-proyectos-abiertos';

@Component({
  selector: 'app-ver-proyectos',
  imports: [FormsModule, CommonModule, MenuFreelancer, HabilidadesDeProyectosAbiertos],
  templateUrl: './ver-proyectos.html',
  styleUrl: './ver-proyectos.css',
})
export class VerProyectos {

  proyectos: ProyectoModel[] = [];
  id_proyecto: number = 0;
  id_usuario: number = 0;
  activo: boolean = false;
  mensajeError: string | null = null;
  monto: number = 0;
  plazo_dias: number = 0;
  descripcion: string = '';
  id_proyecto_selec: number = 0;

  constructor(private freelancerService: FreelancerService) { }

  ngOnInit() {
    const id = sessionStorage.getItem('usuario_id');
    if (id) {
      this.id_usuario = parseInt(id);
    }

    this.obtenerProyectos();

  }

  obtenerProyectos() {

    this.freelancerService.obtenerProyectosAbiertos().subscribe(data => {

      this.proyectos = data;

    });

  }

  mostrarHabilidades(id_proyecto: number) {
    this.id_proyecto = id_proyecto;

    console.log('id_proyecto: ' + this.id_proyecto);
    this.activo = true;

  }

  ocultar() {
    this.activo = false;

  }

  publicar() {


    if (this.id_proyecto_selec <= 0 || this.monto <= 0 || !this.descripcion || this.plazo_dias <= 0) {
      this.mensajeError = 'Por favor, complete todos los campos correctamente.';
      return;
    }



    this.freelancerService.registrarPropuesta(this.id_usuario, this.id_proyecto_selec, this.monto, this.plazo_dias,
      this.descripcion).subscribe({
        next: (response: any) => {

          // si recibe un error
          if (response.status === 'error') {
            this.mensajeError = response.mensaje;
            return;
          }

          console.log('Prpuesta enviada con exito');
          this.mensajeError = response.mensaje;
          return;

        }


      });

  }

}
