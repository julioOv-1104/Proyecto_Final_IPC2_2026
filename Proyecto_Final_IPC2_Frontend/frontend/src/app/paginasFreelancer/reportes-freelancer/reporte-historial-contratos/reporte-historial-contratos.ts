import { Component } from '@angular/core';
import { Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ReportesFreelancerService } from '../../../servicios/reportes-freelancer.service';
import { HistorialContratosModel } from '../../../modelos/historial-contratos-model';

@Component({
  selector: 'app-reporte-historial-contratos',
  imports: [FormsModule, CommonModule],
  templateUrl: './reporte-historial-contratos.html',
  styleUrl: './reporte-historial-contratos.css',
})
export class ReporteHistorialContratos {

  constructor(private reportesService: ReportesFreelancerService) { }

  @Input() fechaInicio!: string;
  @Input() fechaFin!: string;
  @Input() id_usuario!: number;
  mensajeError: string | null = null;
  contratos: HistorialContratosModel[] = [];

  ngOnInit() {
    this.obtenerReporte();
  }

  obtenerReporte() {

    if (!this.fechaInicio || !this.fechaFin) {
      this.mensajeError = "Por favor ingrese ambas fechas para generar este reporte";
      return;
    }

    this.reportesService.obtenerHistorialContratos(this.id_usuario, this.fechaInicio, this.fechaFin)
      .subscribe({
        next: (response: any) => {

          // si recibe un error
          if (response.status === 'error') {
            this.mensajeError = response.mensaje;
            return;
          }

          this.contratos = response;
          return;

        }


      });

  }

}
