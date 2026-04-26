import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MenuAdmin } from '../../menus/menu-admin/menu-admin';
import { AdminService } from '../../servicios/admin.service';
import { CategoriaModel } from '../../modelos/categoria-model';

@Component({
  selector: 'app-editar-categoria',
  imports: [FormsModule, CommonModule, MenuAdmin],
  templateUrl: './editar-categoria.html',
  styleUrl: './editar-categoria.css',
})
export class EditarCategoria {

  constructor(private adminService: AdminService) { }

  categorias: CategoriaModel[] = [];
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

  desactivarActivarCategoria(id_categoria: number) {

    this.adminService.activarDesactivarCategoria(id_categoria).subscribe(() => {
      // Actualizar la lista de categorias
      this.obtenerCategorias();
    });


    console.log('Se cambio el estado de la categoria:', id_categoria);

  }

  editarCategoria() {

    if (!this.nombre || !this.descripcion || this.id_categoria <= 0) {
      this.mensajeError = 'Por favor, complete todos los campos correctamente.';
      return;
    }

    const categoria: Partial<CategoriaModel> = {
      id_categoria: this.id_categoria,
      nombre: this.nombre,
      descripcion: this.descripcion
    };

    this.adminService.editarCategoria(categoria).subscribe({

      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          return;
        }

        console.log('Categoria editada con exito');
        this.mensajeError = response.mensaje;
        this.obtenerCategorias();
        return;

      }



    });
    
  }

}
