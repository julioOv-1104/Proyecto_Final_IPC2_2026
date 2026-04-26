import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MenuAdmin } from '../../menus/menu-admin/menu-admin';
import { AdminService } from '../../servicios/admin.service';
import { HabilidadModel } from '../../modelos/habilidad-model';
import { CategoriaModel } from '../../modelos/categoria-model';

@Component({
  selector: 'app-editar-habilidad',
  imports: [FormsModule, CommonModule, MenuAdmin],
  templateUrl: './editar-habilidad.html',
  styleUrl: './editar-habilidad.css',
})
export class EditarHabilidad {

  constructor(private adminService: AdminService) { }

  habilidades: HabilidadModel[] = [];
  categorias: CategoriaModel[] = [];
  mensajeError: string | null = null;
  descripcion: string = '';
  nombre: string = '';
  id_categoria: number = 0;
  id_habilidad: number = 0;

  ngOnInit(): void {
    this.obtenerHabilidades();
    this.obtenerCategorias();

  }

  obtenerHabilidades() {

    this.adminService.obtenerHabilidades().subscribe(data => {

      this.habilidades = data;

    });

  }

  obtenerCategorias() {

    this.adminService.obtenerCategorias().subscribe(data => {

      this.categorias = data;

    });

  }

  desactivarActivarHabilidad(id_habilidad: number) {

    this.adminService.activarDesactivarHabilidad(id_habilidad).subscribe(() => {
      // Actualizar la lista de habilidades
      this.obtenerHabilidades();
    });


    console.log('Se cambio el estado de la habilidad:', id_habilidad);

  }

  editarHabilidad() {

    if (!this.nombre || !this.descripcion || this.id_categoria <= 0 || this.id_categoria <= 0) {
      this.mensajeError = 'Por favor, complete todos los campos correctamente.';
      return;
    }

    const habilidad: Partial<HabilidadModel> = {
      id_categoria: this.id_categoria,
      id_habilidad: this.id_habilidad,
      nombre: this.nombre,
      descripcion: this.descripcion
    };

    this.adminService.editarHabilidad(habilidad).subscribe({

      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          return;
        }

        console.log('Habilidad editada con exito');
        this.mensajeError = response.mensaje;
        this.obtenerHabilidades();
        return;

      }



    });

  }

}
