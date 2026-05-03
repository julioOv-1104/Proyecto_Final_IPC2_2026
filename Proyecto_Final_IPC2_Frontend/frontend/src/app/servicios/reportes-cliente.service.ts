import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HistorialProyectosCliente } from '../modelos/historial-proyectos-cliente';
import { HistorialRecargasModel } from '../modelos/historial-recargas-model';
import { HistorialGastosModel } from '../modelos/historial-gastos-model';

@Injectable({
  providedIn: 'root',
})
export class ReportesClienteService {

  constructor(private http: HttpClient) { }

  HistorialProyectosUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ReportesClienteServlet?accion=historialProyectos';
  historialRecargasUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ReportesClienteServlet';
  historialGastosUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ReportesClienteServlet?accion=gastos';



  obtenerHistorialProyectos(id_usuario: number, fecha1: string, fecha2: string): 
  Observable<HistorialProyectosCliente[]> {
    return this.http.post<HistorialProyectosCliente[]>(this.HistorialProyectosUrl, {
      id_usuario,fecha1, fecha2 });
  }

  obtenerRecargas(id_usuario: number): Observable<HistorialRecargasModel[]> {
    return this.http.put<HistorialRecargasModel[]>(this.historialRecargasUrl, { id_usuario });
  }

  obtenerHistorialGastos(id_usuario: number, fecha1: string, fecha2: string): 
  Observable<HistorialGastosModel[]> {
    return this.http.post<HistorialGastosModel[]>(this.historialGastosUrl, {
      id_usuario,fecha1, fecha2});
  }

}
