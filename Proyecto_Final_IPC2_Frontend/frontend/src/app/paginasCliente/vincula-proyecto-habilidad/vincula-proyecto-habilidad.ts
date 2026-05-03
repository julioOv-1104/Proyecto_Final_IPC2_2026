import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MenuCliente } from '../../menus/menu-cliente/menu-cliente';
import { ProyectoModel } from '../../modelos/proyecto-model';
import { AdminService } from '../../servicios/admin.service';
import { ClienteService } from '../../servicios/cliente.service';
import { HabilidadesDeProyecto } from './habilidades-de-proyecto/habilidades-de-proyecto';

@Component({
  selector: 'app-vincula-proyecto-habilidad',
  imports: [FormsModule, CommonModule, MenuCliente, HabilidadesDeProyecto],
  templateUrl: './vincula-proyecto-habilidad.html',
  styleUrl: './vincula-proyecto-habilidad.css',
})
export class VinculaProyectoHabilidad {

  constructor(private adminService: AdminService, private clienteService: ClienteService) { }

  proyectos: ProyectoModel[] = [];
   id_usuario: number = 0;
   id_proyecto: number = 0;
   activo: boolean = false;

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

  mostrarHabilidades(id_proyecto: number){
    this.id_proyecto = id_proyecto;

    console.log('id_proyecto: '+this.id_proyecto );
    this.activo = true;

  }

  ocultar(){
    this.activo = false;

  }

}
