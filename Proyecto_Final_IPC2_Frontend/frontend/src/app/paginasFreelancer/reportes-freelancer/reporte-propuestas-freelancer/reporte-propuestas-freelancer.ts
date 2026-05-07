import { Component } from '@angular/core';
import { Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ReportesFreelancerService } from '../../../servicios/reportes-freelancer.service';
import { PropuestaModel } from '../../../modelos/propuesta-model';

@Component({
  selector: 'app-reporte-propuestas-freelancer',
  imports: [FormsModule, CommonModule],
  templateUrl: './reporte-propuestas-freelancer.html',
  styleUrl: './reporte-propuestas-freelancer.css',
})
export class ReportePropuestasFreelancer {

  constructor(private reportesService: ReportesFreelancerService) { }

  @Input() fechaInicio!: string;
  @Input() fechaFin!: string;
  @Input() id_usuario!: number;
  mensajeError: string | null = null;
  propuestas: PropuestaModel[] = [];

  ngOnInit() {
    this.obtenerReporte();
  }

  obtenerReporte() {

      if (!this.fechaInicio || !this.fechaFin) {
      this.mensajeError = "Por favor ingrese ambas fechas para generar este reporte";
      return;
    }

    this.reportesService.obtenerPrpuestasEnviadas(this.id_usuario, this.fechaInicio, this.fechaFin)
      .subscribe({
        next: (response: any) => {

          // si recibe un error
          if (response.status === 'error') {
            this.mensajeError = response.mensaje;
            return;
          }

          this.propuestas = response;
          return;

        }


      });

  }


}
