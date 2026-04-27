import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HistorialPorcentajesModel } from '../modelos/historial-porcentajes-model';
import { TopCategoriasModel } from '../modelos/top-categorias-model';
import { TopFreelancersModel } from '../modelos/top-freelancers-model';
import { TotalIngresosModel } from '../modelos/total-ingresos-model';

@Injectable({
  providedIn: 'root',
})
export class ReportesAdminService {

  constructor(private http: HttpClient) { }

  HistorialPorcentajesUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ReportesAdminServlet';
  TopFreelancersUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ReportesAdminServlet?accion=topFreelancers';
  TopCategoriasUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ReportesAdminServlet?accion=topCategorias';
  TotalIngresosUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ReportesAdminServlet?accion=totalIngresos';




  obtenerHistorialPorcentajes(): Observable<HistorialPorcentajesModel[]> {
    return this.http.get<HistorialPorcentajesModel[]>(this.HistorialPorcentajesUrl);
  }

  obtesnerTopFreelancers(fecha1: string, fecha2: string): Observable<TopFreelancersModel[]> {
    return this.http.post<TopFreelancersModel[]>(this.TopFreelancersUrl, { fecha1, fecha2 });
  }

  obtesnerTopCategorias(fecha1: string, fecha2: string): Observable<TopCategoriasModel[]> {
    return this.http.post<TopCategoriasModel[]>(this.TopCategoriasUrl, { fecha1, fecha2 });
  }

  obtenerTotalIngresos(fecha1: string, fecha2: string): Observable<TotalIngresosModel> {
    return this.http.post<TotalIngresosModel>(this.TotalIngresosUrl, { fecha1, fecha2 });
  }

}
