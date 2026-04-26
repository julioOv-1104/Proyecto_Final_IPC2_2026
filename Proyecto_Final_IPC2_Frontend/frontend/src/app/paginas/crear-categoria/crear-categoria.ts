import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MenuAdmin } from '../../menus/menu-admin/menu-admin';
import { AdminService } from '../../servicios/admin.service';

@Component({
  selector: 'app-crear-categoria',
  imports: [FormsModule, CommonModule, MenuAdmin],
  templateUrl: './crear-categoria.html',
  styleUrl: './crear-categoria.css',
})
export class CrearCategoria {

  constructor(private adminService: AdminService) { }

  mensajeError: string | null = null;
  descripcion: string = '';
  nombre: string = '';

  crearCategoria() {

    if (!this.nombre || !this.descripcion) {
      this.mensajeError = 'Por favor, complete todos los campos.';
      return;
    }

    this.adminService.crearCategoria(this.nombre, this.descripcion).subscribe({
      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          return;
        }

        console.log('Categotia creada con exito');
        this.mensajeError = response.mensaje;
        return;

      }


    });

  }


}
