import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Input } from '@angular/core';
import { MenuCliente } from '../../menus/menu-cliente/menu-cliente';
import { CategoriaModel } from '../../modelos/categoria-model';
import { ProyectoModel } from '../../modelos/proyecto-model';
import { AdminService } from '../../servicios/admin.service';
import { ClienteService } from '../../servicios/cliente.service';

@Component({
  selector: 'app-editar-proyecto',
  imports: [FormsModule, CommonModule, MenuCliente],
  templateUrl: './editar-proyecto.html',
  styleUrl: './editar-proyecto.css',
})
export class EditarProyecto {

  constructor(private adminService: AdminService, private clienteService: ClienteService) { }

  mensajeError: string | null = null;
  mensajeError2: string | null = null;
  id_proyecto: number = 0;
  id_categoria: number = 0;
  titulo: string = '';
  motivo: string = '';
  descripcion: string = '';
  presupuesto_max: number = 0;
  @Input() fecha_limite!: string;
  id_usuario: number = 0;

  categorias: CategoriaModel[] = [];
  proyectos: ProyectoModel[] = [];


  ngOnInit() {
    const id = sessionStorage.getItem('usuario_id');
    if (id) {
      this.id_usuario = parseInt(id);
    }

    this.obtenerCategorias();
    this.obtenerProyectos();

  }

  obtenerCategorias() {

    this.adminService.obtenerCategorias().subscribe(data => {

      this.categorias = data;

    });

  }

  obtenerProyectos() {

    this.clienteService.obtenerProyectosSinContrato(this.id_usuario).subscribe(data => {

      this.proyectos = data;

    });

  }

  editar() {

    if (this.id_proyecto <= 0 || this.id_categoria <= 0 || !this.titulo || !this.descripcion || this.presupuesto_max <= 0
      || !this.fecha_limite) {
      this.mensajeError = 'Por favor, complete todos los campos correctamente.';
      return;
    }



    this.clienteService.editarProyecto(this.id_proyecto, this.id_categoria, this.titulo, this.descripcion,
      this.presupuesto_max, this.fecha_limite).subscribe({
        next: (response: any) => {

          // si recibe un error
          if (response.status === 'error') {
            this.mensajeError = response.mensaje;
            return;
          }

          console.log('Proyecto editado con exito');
          this.mensajeError = response.mensaje;
          this.obtenerProyectos();
          return;

        }


      });


  }

  cancelar() {

    if (this.id_proyecto <= 0 ) {
      this.mensajeError = 'Por favor, ingrese el id del proyecto para cancelarlo.';
      return;
    }


    this.clienteService.cancelarProyecto(this.id_proyecto).subscribe({
        next: (response: any) => {

          // si recibe un error
          if (response.status === 'error') {
            this.mensajeError = response.mensaje;
            return;
          }

          console.log('Proyecto cancelado con exito');
          this.mensajeError = response.mensaje;
          this.obtenerProyectos();
          return;

        }


      });

  }



}
