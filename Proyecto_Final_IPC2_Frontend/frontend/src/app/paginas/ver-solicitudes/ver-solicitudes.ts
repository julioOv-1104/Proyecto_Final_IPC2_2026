import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MenuAdmin } from '../../menus/menu-admin/menu-admin';
import { SolicitudModel } from '../../modelos/solicitud-model';
import { AdminService } from '../../servicios/admin.service';

@Component({
  selector: 'app-ver-solicitudes',
  imports: [FormsModule, CommonModule, MenuAdmin],
  templateUrl: './ver-solicitudes.html',
  styleUrl: './ver-solicitudes.css',
})
export class VerSolicitudes {

  constructor(private adminService: AdminService) { }

  solicitudesCategorias: SolicitudModel[] = [];
  solicitudesHabilidades: SolicitudModel[] = [];

  aceptarCategoria(id_solicitud: number, nombre: string, descripcion: string) {

    this.adminService.gestionarSolicitudCategoria(id_solicitud, 'ACEPTADA', nombre, descripcion).subscribe({

      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          console.error('Error al aceptar categoria:', response.mensaje);
          return;
        }

        console.log('Categoria aceptada con exito: ', response.mensaje);
        this.obtenerSolicitudesCategorias();
        return;

      }



    });

  }

  rechazarCategoria(id_solicitud: number, nombre: string, descripcion: string) {

    this.adminService.gestionarSolicitudCategoria(id_solicitud, 'RECHAZADA', nombre, descripcion).subscribe({

      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          console.error('Error al rechazar categoria:', response.mensaje);
          return;
        }

        console.log('Categoria rechazada con exito: ', response.mensaje);
        this.obtenerSolicitudesCategorias();
        return;

      }



    });

  }

  ngOnInit() {

    this.obtenerSolicitudesCategorias();
    this.obtenerSolicitudesHabilidades();

  }

  obtenerSolicitudesCategorias() {

    this.adminService.obtenerSolicitudesCategorias().subscribe(data => {

      this.solicitudesCategorias = data;

    });

  }

  obtenerSolicitudesHabilidades() {

    this.adminService.obtenerSolicitudesHabilidades().subscribe(data => {

      this.solicitudesHabilidades = data;

    });

  }



  aceptarHabilidad(id_solicitud: number, nombre: string, descripcion: string) {

    this.adminService.gestionarSolicitudHabilidad(id_solicitud, 'ACEPTADA', nombre, descripcion).subscribe({

      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          console.error('Error al aceptar habilidad:', response.mensaje);
          return;
        }

        console.log('Habilidad aceptada con exito: ', response.mensaje);
        this.obtenerSolicitudesHabilidades();
        return;

      }



    });

  }

  rechazarHabilidad(id_solicitud: number, nombre: string, descripcion: string) {

    this.adminService.gestionarSolicitudHabilidad(id_solicitud, 'RECHAZADA', nombre, descripcion).subscribe({

      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          console.error('Error al rechazar habilidad:', response.mensaje);
          return;
        }

        console.log('Habilidad rechazada con exito: ', response.mensaje);
        this.obtenerSolicitudesHabilidades();
        return;

      }



    });

  }




}
