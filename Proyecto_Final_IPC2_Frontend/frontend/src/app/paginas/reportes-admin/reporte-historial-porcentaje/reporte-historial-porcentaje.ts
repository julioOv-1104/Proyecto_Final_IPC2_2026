import { Component } from '@angular/core';
import { Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HistorialPorcentajesModel } from '../../../modelos/historial-porcentajes-model';
import { ReportesAdminService } from '../../../servicios/reportes-admin.service';

@Component({
  selector: 'app-reporte-historial-porcentaje',
  imports: [FormsModule, CommonModule],
  templateUrl: './reporte-historial-porcentaje.html',
  styleUrl: './reporte-historial-porcentaje.css',
})
export class ReporteHistorialPorcentaje {

  constructor(private reportesAdminService: ReportesAdminService) { }

  @Input() fechaInicio!: string;
  @Input() fechaFin!: string;

  historial: HistorialPorcentajesModel[] = [];

  ngOnInit() {
    this.obtenerReporte();
  }

  obtenerReporte() {

    this.reportesAdminService.obtenerHistorialPorcentajes().subscribe(data => {

        this.historial = data;

      });

  }

}
