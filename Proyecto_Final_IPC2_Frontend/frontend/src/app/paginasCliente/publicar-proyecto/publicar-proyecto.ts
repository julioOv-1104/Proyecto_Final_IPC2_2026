import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Input } from '@angular/core';
import { MenuCliente } from '../../menus/menu-cliente/menu-cliente';
import { CategoriaModel } from '../../modelos/categoria-model';
import { AdminService } from '../../servicios/admin.service';
import { ClienteService } from '../../servicios/cliente.service';

@Component({
  selector: 'app-publicar-proyecto',
  imports: [FormsModule, CommonModule, MenuCliente],
  templateUrl: './publicar-proyecto.html',
  styleUrl: './publicar-proyecto.css',
})
export class PublicarProyecto {

   constructor(private adminService: AdminService, private clienteService: ClienteService) { }

  mensajeError: string | null = null;
  id_categoria: number = 0;
  titulo: string = '';
  descripcion: string = '';
  presupuesto_max: number = 0;
  @Input() fecha_limite!: string;
  id_usuario: number = 0;

  categorias: CategoriaModel[] = [];

  ngOnInit() {
    const id = sessionStorage.getItem('usuario_id');
    if (id) {
      this.id_usuario = parseInt(id);
    }

    this.obtenerCategorias();

  }

  obtenerCategorias() {

    this.adminService.obtenerCategorias().subscribe(data => {

      this.categorias = data;

    });

  }

  publicar() { 


    if (this.id_categoria <=0 || !this.titulo || !this.descripcion || this.presupuesto_max <= 0 
      || !this.fecha_limite) {
      this.mensajeError = 'Por favor, complete todos los campos correctamente.';
      return;
    }

    

    this.clienteService.publicarProyecto(this.id_usuario, this.id_categoria, this.titulo,this.descripcion, 
      this.presupuesto_max, this.fecha_limite).subscribe({
      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          return;
        }

        console.log('Proyecto publicado con exito');
        this.mensajeError = response.mensaje;
        return;

      }


    });
    
  }

  

  }


