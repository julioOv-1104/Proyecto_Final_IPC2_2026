import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MenuCliente } from '../../menus/menu-cliente/menu-cliente';
import { ReporteHistorialProyectos } from './reporte-historial-proyectos/reporte-historial-proyectos';
import { ReporteHistorialRecargas } from './reporte-historial-recargas/reporte-historial-recargas';
import { ReporteHistorialGastos } from './reporte-historial-gastos/reporte-historial-gastos';

@Component({
  selector: 'app-reportes-cliente',
  imports: [FormsModule, CommonModule, MenuCliente, ReporteHistorialProyectos, ReporteHistorialRecargas,
    ReporteHistorialGastos
  ],
  templateUrl: './reportes-cliente.html',
  styleUrl: './reportes-cliente.css',
})
export class ReportesCliente {

  tipoReporte = "";//almacena el tipo de reporte seleccionado por el usuario
  fechaInicio!: string;
  fechaFin!: string;
  id_usuario: number = 0;

   ngOnInit() {
    const id = sessionStorage.getItem('usuario_id');
    if (id) {
      this.id_usuario = parseInt(id);
    }

  }

  mostrarReporte(tipo: string) {
    this.tipoReporte = tipo;
  }

}
