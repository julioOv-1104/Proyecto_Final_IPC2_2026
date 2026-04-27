import { Component } from '@angular/core';
import { Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { TopCategoriasModel } from '../../../modelos/top-categorias-model';
import { ReportesAdminService } from '../../../servicios/reportes-admin.service';

@Component({
  selector: 'app-reporte-top-categorias',
  imports: [FormsModule, CommonModule],
  templateUrl: './reporte-top-categorias.html',
  styleUrl: './reporte-top-categorias.css',
})
export class ReporteTopCategorias {

  constructor(private reportesAdminService: ReportesAdminService) { }

  @Input() fechaInicio!: string;
  @Input() fechaFin!: string;
  mensajeError: string | null = null;
  categorias: TopCategoriasModel[] = [];

  ngOnInit() {
    this.obtenerReporte();
  }

  obtenerReporte() {

    if (!this.fechaInicio || !this.fechaFin) {
      this.mensajeError = "Por favor ingrese ambas fechas para generar este reporte";
      return;
    }

    this.reportesAdminService.obtesnerTopCategorias(this.fechaInicio, this.fechaFin).subscribe({
      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          return;
        }

        console.log('top de categorias cargados con exito');
        this.categorias = response;
        return;

      }


    });

  }

}
