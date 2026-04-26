import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MenuAdmin } from '../../menus/menu-admin/menu-admin';
import { AdminService } from '../../servicios/admin.service';
import { CategoriaModel } from '../../modelos/categoria-model';

@Component({
  selector: 'app-crear-habilidad',
  imports: [FormsModule, CommonModule, MenuAdmin],
  templateUrl: './crear-habilidad.html',
  styleUrl: './crear-habilidad.css',
})
export class CrearHabilidad {

  constructor(private adminService: AdminService) { }

  categorias: CategoriaModel[]=[];
  mensajeError: string | null = null;
  descripcion: string = '';
  nombre: string = '';
  id_categoria: number = 0;


ngOnInit(): void {
  this.obtenerCategorias();

}

obtenerCategorias() {

    this.adminService.obtenerCategorias().subscribe(data => {

      this.categorias = data;

    });

  }


  crearHabilidad() {

    if (!this.nombre || !this.descripcion || this.id_categoria <=0) {
      this.mensajeError = 'Por favor, complete todos los campos.';
      return;
    }

    this.adminService.crearHabilidad(this.id_categoria, this.nombre, this.descripcion).subscribe({
      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          return;
        }

        console.log('Habilidad creada con exito');
        this.mensajeError = response.mensaje;
        return;

      }


    });

  }

}
