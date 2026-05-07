import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MenuFreelancer } from '../../menus/menu-freelancer/menu-freelancer';
import { ReporteHistorialContratos } from './reporte-historial-contratos/reporte-historial-contratos';
import { ReporteTopCategoriasFreelancer } from './reporte-top-categorias-freelancer/reporte-top-categorias-freelancer';
import { ReportePropuestasFreelancer } from './reporte-propuestas-freelancer/reporte-propuestas-freelancer';

@Component({
  selector: 'app-reportes-freelancer',
  imports: [FormsModule, CommonModule, MenuFreelancer, ReporteHistorialContratos, 
    ReporteTopCategoriasFreelancer, ReportePropuestasFreelancer],
  templateUrl: './reportes-freelancer.html',
  styleUrl: './reportes-freelancer.css',
})
export class ReportesFreelancer {

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
