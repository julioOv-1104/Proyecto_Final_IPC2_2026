import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MenuCliente } from '../../menus/menu-cliente/menu-cliente';
import { ProyectoModel } from '../../modelos/proyecto-model';
import { AdminService } from '../../servicios/admin.service';
import { ClienteService } from '../../servicios/cliente.service';
import { Entregas } from './entregas/entregas';

@Component({
  selector: 'app-gestion-entregas',
  imports: [FormsModule, CommonModule, MenuCliente, Entregas],
  templateUrl: './gestion-entregas.html',
  styleUrl: './gestion-entregas.css',
})
export class GestionEntregas {

    constructor(private adminService: AdminService, private clienteService: ClienteService) { }

  mensajeError: string | null = null;
  id_proyecto: number = 0;
  id_usuario: number = 0;

  proyectos: ProyectoModel[] = [];

  activo: boolean = false;

  consultarPropuestas() {
    sessionStorage.setItem('id_proyecto', JSON.stringify(this.id_proyecto));
    this.activo = true;
  }

  ocultar(){
    this.activo = false;
  }

  ngOnInit() {
    const id = sessionStorage.getItem('usuario_id');
    if (id) {
      this.id_usuario = parseInt(id);
    }

    this.obtenerProyectos();

  }


  obtenerProyectos() {

    this.clienteService.obtenerProyectosConEntregas(this.id_usuario).subscribe(data => {

      this.proyectos = data;

    });

  }

}
