import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MenuAdmin } from '../../menus/menu-admin/menu-admin';
import { ReporteTopFreelancers } from './reporte-top-freelancers/reporte-top-freelancers';
import { ReporteHistorialPorcentaje } from './reporte-historial-porcentaje/reporte-historial-porcentaje';
import { ReporteTopCategorias } from './reporte-top-categorias/reporte-top-categorias';
import { ReporteTotalIngresos } from './reporte-total-ingresos/reporte-total-ingresos';

@Component({
  selector: 'app-reportes-admin',
  imports: [FormsModule, CommonModule, MenuAdmin, ReporteHistorialPorcentaje, ReporteTopFreelancers,
    ReporteTopCategorias, ReporteTotalIngresos],
  templateUrl: './reportes-admin.html',
  styleUrl: './reportes-admin.css',
})
export class ReportesAdmin {

  tipoReporte = "";//almacena el tipo de reporte seleccionado por el usuario
  fechaInicio!: string;
  fechaFin!: string;

  mostrarReporte(tipo: string) {
    this.tipoReporte = tipo;
  }

}
