import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UsuarioModel } from '../../modelos/usuario-model';
import { UsuarioService } from '../../servicios/usuario.service';
import { AdminService } from '../../servicios/admin.service';
import { MenuAdmin } from '../../menus/menu-admin/menu-admin';

@Component({
  selector: 'app-gestion',
  imports: [FormsModule, CommonModule, MenuAdmin],
  templateUrl: './gestion.html',
  styleUrl: './gestion.css',
})
export class Gestion {

  constructor(private usuarioService: UsuarioService, private adminService: AdminService) { }

  clientes: UsuarioModel[] = [];
  freelancers: UsuarioModel[] = [];
  mensajeError: string | null = null;
  comision_actual: number = 0;
  nueva_comision: number = 0;

  ngOnInit() {
    this.obtenerClientes();
    this.obtenerFreelancers();
    this.obtenerComisionActual();
    console.log('Clientes y freelancers cargados');
  }

  obtenerClientes() {

    this.usuarioService.obtenerClientes().subscribe(data => {

      this.clientes = data;

    });

  }

  obtenerFreelancers() {

    this.usuarioService.obtenerFreelancers().subscribe(data => {

      this.freelancers = data;

    });

  }

  obtenerComisionActual() {

    this.adminService.obtenerComision().subscribe(data => {

      this.comision_actual = data.porcentaje;

    });

  }

  desactivarActivarUsuario(username: string) {

    this.usuarioService.activarDesactivar(username).subscribe(() => {
      // Actualizar la lista de clientes y freelancers después de cambiar el estado
      this.obtenerClientes();
      this.obtenerFreelancers();
    });


    console.log('Se cambio el estado del usuario:', username);

  }

  cambiarComision() {

    if (this.nueva_comision <= 0 || this.nueva_comision > 100) {
      this.mensajeError = 'Ingrese una comision valida';
      return;
    }

    this.adminService.comprobarInfo(this.nueva_comision).subscribe({
      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          return;
        }

        console.log('Cambio de comision con exito');
        this.mensajeError = response.mensaje;
        this.obtenerComisionActual();
        return;

      }


    });

    

  }

}
