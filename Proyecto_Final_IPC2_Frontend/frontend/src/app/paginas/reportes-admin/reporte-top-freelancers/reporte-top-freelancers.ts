import { Component } from '@angular/core';
import { Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { TopFreelancersModel } from '../../../modelos/top-freelancers-model';
import { ReportesAdminService } from '../../../servicios/reportes-admin.service';

@Component({
  selector: 'app-reporte-top-freelancers',
  imports: [FormsModule, CommonModule],
  templateUrl: './reporte-top-freelancers.html',
  styleUrl: './reporte-top-freelancers.css',
})
export class ReporteTopFreelancers {

  constructor(private reportesAdminService: ReportesAdminService) { }
  
    @Input() fechaInicio!: string;
    @Input() fechaFin!: string;
  mensajeError: string | null = null;
    freelancers: TopFreelancersModel[] = [];
  
    ngOnInit() {
      this.obtenerReporte();
    }
  
    obtenerReporte() {

      if(!this.fechaInicio || !this.fechaFin){
        this.mensajeError = "Por favor ingrese ambas fechas para generar este reporte";
        return;
      }
  
      this.reportesAdminService.obtesnerTopFreelancers(this.fechaInicio, this.fechaFin).subscribe({
      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          return;
        }

        console.log('top freelancers cargados con exito');
        this.freelancers = response;
        return;

      }


    });
  
    }

}
