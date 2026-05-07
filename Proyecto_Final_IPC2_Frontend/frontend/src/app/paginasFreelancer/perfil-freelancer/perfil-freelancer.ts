import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MenuFreelancer } from '../../menus/menu-freelancer/menu-freelancer';
import { FreelancerService } from '../../servicios/freelancer.service';
import { UsuarioService } from '../../servicios/usuario.service';
import { UsuarioModel } from '../../modelos/usuario-model';
import { CalificacionModel } from '../../modelos/calificacion-model';

@Component({
  selector: 'app-perfil-freelancer',
  imports: [FormsModule, CommonModule, MenuFreelancer],
  templateUrl: './perfil-freelancer.html',
  styleUrl: './perfil-freelancer.css',
})
export class PerfilFreelancer {

  constructor(private freelancerService: FreelancerService, private usuarioService: UsuarioService) { }

  id_usuario: number = 0;
  mensajeError: string | null = null;
  mensajeError2: string | null = null;
  usuario: UsuarioModel[] = [];
  calificaciones: CalificacionModel[] = [];
  calificacion_promedio: number = 0;

  ngOnInit() {
    const id = sessionStorage.getItem('usuario_id');
    if (id) {
      this.id_usuario = parseInt(id);
    }

    this.obtenerPerfil();
    this.obtenerCalificaciones();



  }

  obtenerPerfil() {

    this.usuarioService.obtenerPerfil(this.id_usuario).subscribe({

      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          return;
        }

        console.log('perfil cargado con exito');
        this.usuario = response;
        return;

      }



    });

  }

  obtenerCalificaciones() {

    this.freelancerService.obtenerCalificaciones(this.id_usuario).subscribe({

      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          return;
        }

        console.log('calificaciones cargadas con exito');
        this.calificaciones = response;
        this.calificacion_promedio = response[0].calificacion_promedio;

        console.log('calificacion promedio ' + this.calificacion_promedio);
        return;

      }



    });

  }

}
