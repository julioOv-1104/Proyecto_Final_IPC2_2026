import { Component } from '@angular/core';
import { Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HistorialProyectosCliente } from '../../../modelos/historial-proyectos-cliente';
import { ReportesClienteService } from '../../../servicios/reportes-cliente.service';

@Component({
  selector: 'app-reporte-historial-proyectos',
  imports: [FormsModule, CommonModule],
  templateUrl: './reporte-historial-proyectos.html',
  styleUrl: './reporte-historial-proyectos.css',
})
export class ReporteHistorialProyectos {

  
    constructor(private reportesClienteService: ReportesClienteService) { }
  
    @Input() fechaInicio!: string;
    @Input() fechaFin!: string;
    @Input() id_usuario!: number;
    mensajeError: string | null = null;
  
    historial: HistorialProyectosCliente[] = [];

    ngOnInit() {
    this.obtenerReporte();
  }

  obtenerReporte() {

    console.log('id_usuario:' +this.id_usuario);

     if (!this.fechaInicio || !this.fechaFin) {
      this.mensajeError = "Por favor ingrese ambas fechas para generar este reporte";
      return;
    }

    this.reportesClienteService.obtenerHistorialProyectos(this.id_usuario, this.fechaInicio, this.fechaFin)
    .subscribe({
      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          return;
        }

        this.historial = response;
        return;

      }


    });

  }

}
