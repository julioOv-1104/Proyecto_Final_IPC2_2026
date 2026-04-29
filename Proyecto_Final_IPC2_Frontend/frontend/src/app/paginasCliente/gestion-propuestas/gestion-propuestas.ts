import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MenuCliente } from '../../menus/menu-cliente/menu-cliente';
import { ProyectoModel } from '../../modelos/proyecto-model';
import { AdminService } from '../../servicios/admin.service';
import { ClienteService } from '../../servicios/cliente.service';
import { Propuestas } from './propuestas/propuestas';

@Component({
  selector: 'app-gestion-propuestas',
  imports: [FormsModule, CommonModule, MenuCliente, Propuestas],
  templateUrl: './gestion-propuestas.html',
  styleUrl: './gestion-propuestas.css',
})
export class GestionPropuestas {

  constructor(private adminService: AdminService, private clienteService: ClienteService) { }

  mensajeError: string | null = null;
  id_proyecto: number = 0;
  id_usuario: number = 0;

  proyectos: ProyectoModel[] = [];

  activo: boolean = false;

  consultarPropuestas() {
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

    this.clienteService.obtenerProyectosSinContrato(this.id_usuario).subscribe(data => {

      this.proyectos = data;

    });

  }


}
