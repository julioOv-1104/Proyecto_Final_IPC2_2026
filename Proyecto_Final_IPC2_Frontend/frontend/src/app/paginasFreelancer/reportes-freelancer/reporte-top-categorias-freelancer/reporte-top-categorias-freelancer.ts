import { Component } from '@angular/core';
import { Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ReportesFreelancerService } from '../../../servicios/reportes-freelancer.service';
import { TopCategoriasModel } from '../../../modelos/top-categorias-model';

@Component({
  selector: 'app-reporte-top-categorias-freelancer',
  imports: [FormsModule, CommonModule],
  templateUrl: './reporte-top-categorias-freelancer.html',
  styleUrl: './reporte-top-categorias-freelancer.css',
})
export class ReporteTopCategoriasFreelancer {

  constructor(private reportesService: ReportesFreelancerService) { }

  @Input() fechaInicio: string = '2026-01-01';
  @Input() fechaFin: string = '2026-12-31';
  @Input() id_usuario!: number;
  mensajeError: string | null = null;
  categorias: TopCategoriasModel[] = [];

  ngOnInit() {
    this.obtenerReporte();
  }

  obtenerReporte() {

    this.reportesService.obtenerTopCategorias(this.id_usuario, this.fechaInicio, this.fechaFin)
      .subscribe({
        next: (response: any) => {

          // si recibe un error
          if (response.status === 'error') {
            this.mensajeError = response.mensaje;
            return;
          }

          this.categorias = response;
          return;

        }


      });

  }

}
