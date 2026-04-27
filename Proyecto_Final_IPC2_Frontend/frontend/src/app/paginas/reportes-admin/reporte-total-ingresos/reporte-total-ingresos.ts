import { Component } from '@angular/core';
import { Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { TotalIngresosModel } from '../../../modelos/total-ingresos-model';
import { ReportesAdminService } from '../../../servicios/reportes-admin.service';

@Component({
  selector: 'app-reporte-total-ingresos',
  imports: [FormsModule, CommonModule],
  templateUrl: './reporte-total-ingresos.html',
  styleUrl: './reporte-total-ingresos.css',
})
export class ReporteTotalIngresos {

  constructor(private reportesAdminService: ReportesAdminService) { }

  @Input() fechaInicio!: string;
  @Input() fechaFin!: string;
  mensajeError: string | null = null;
  ingresos: TotalIngresosModel | null = null;

  ngOnInit() {
    this.obtenerReporte();
  }

  obtenerReporte() {

    if (!this.fechaInicio || !this.fechaFin) {
      this.mensajeError = "Por favor ingrese ambas fechas para generar este reporte";
      return;
    }

    this.reportesAdminService.obtenerTotalIngresos(this.fechaInicio, this.fechaFin).subscribe({
      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          return;
        }

        console.log('total ingresos cargados con exito');
        this.ingresos = response;
        return;

      }


    });


  }

}
