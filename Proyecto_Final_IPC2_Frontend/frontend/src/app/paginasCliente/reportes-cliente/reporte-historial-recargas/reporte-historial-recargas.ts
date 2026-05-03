import { Component } from '@angular/core';
import { Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HistorialRecargasModel } from '../../../modelos/historial-recargas-model';
import { ReportesClienteService } from '../../../servicios/reportes-cliente.service';

@Component({
  selector: 'app-reporte-historial-recargas',
  imports: [FormsModule, CommonModule],
  templateUrl: './reporte-historial-recargas.html',
  styleUrl: './reporte-historial-recargas.css',
})
export class ReporteHistorialRecargas {

  constructor(private reportesClienteService: ReportesClienteService) { }
    
      @Input() id_usuario!: number;
      mensajeError: string | null = null;
    
      historial: HistorialRecargasModel[] = [];


      ngOnInit() {
    this.obtenerReporte();
  }

  obtenerReporte() {


    this.reportesClienteService.obtenerRecargas(this.id_usuario)
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
