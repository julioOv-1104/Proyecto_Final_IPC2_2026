import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HistorialContratosModel } from '../modelos/historial-contratos-model';
import { TopCategoriasModel } from '../modelos/top-categorias-model';
import { PropuestaModel } from '../modelos/propuesta-model';

@Injectable({
  providedIn: 'root',
})
export class ReportesFreelancerService {

  constructor(private http: HttpClient) { }

  historialContratosUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ReportesFreelancerServlet?accion=historialContratos';
  topCategoriasUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ReportesFreelancerServlet?accion=topCategorias';
  HistorialPropuestassUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ReportesFreelancerServlet?accion=propuestasEnviadas';



  obtenerHistorialContratos(id_usuario: number, fecha1: string, fecha2: string):
    Observable<HistorialContratosModel[]> {
    return this.http.post<HistorialContratosModel[]>(this.historialContratosUrl, {
      id_usuario, fecha1, fecha2
    });
  }

  obtenerTopCategorias(id_usuario: number, fecha1: string, fecha2: string):
    Observable<TopCategoriasModel[]> {
    return this.http.post<TopCategoriasModel[]>(this.topCategoriasUrl, {
      id_usuario, fecha1, fecha2
    });
  }

  obtenerPrpuestasEnviadas(id_usuario: number, fecha1: string, fecha2: string):
    Observable<PropuestaModel[]> {
    return this.http.post<PropuestaModel[]>(this.HistorialPropuestassUrl, {
      id_usuario, fecha1, fecha2
    });
  }

}
