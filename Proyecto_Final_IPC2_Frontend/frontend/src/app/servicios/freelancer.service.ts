import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HabilidadModel } from '../modelos/habilidad-model';
import { ProyectoModel } from '../modelos/proyecto-model';
import { PropuestaModel } from '../modelos/propuesta-model';
import { ContratosModel } from '../modelos/contratos-model';
import { EntregaModel } from '../modelos/entrega-model';
import { CalificacionModel } from '../modelos/calificacion-model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class FreelancerService {


  constructor(private http: HttpClient) { }

  Url = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/FreelancerServlet?accion=completar';
  obtenerHabilidadesUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/FreelancerServlet?accion=obtenerHabilidades';
  vincularDesvincularUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/FreelancerServlet?accion=vincularDesvincular';
  solicitarUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/FreelancerServlet?accion=solicitar';
  obtenerProyectosAbiertosUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/FreelancerServlet?accion=obtenerProyectos';
  RegistrarPropuestaUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/FreelancerServlet?accion=registrarPropuesta';
  obtenerPropuestasUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/FreelancerServlet?accion=obtenerPropuestas';
  RetirarPropuestaUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/FreelancerServlet?accion=retirarPropuesta';
  obtenerContratosUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/FreelancerServlet?accion=obtenerContratos';
  enviarEntregaUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/FreelancerServlet?accion=registrarEntrega';
  obtenerEntregasUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/FreelancerServlet?accion=obtenerEntregas';
  obtenerCalificacionesUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/FreelancerServlet?accion=obtenerCalificaciones';







  registrarInfo(id_usuario: number, biografia: string, nivel_experiencia: string, tarifa_hora: number) {
    return this.http.post(this.Url, {
      id_usuario,
      biografia,
      nivel_experiencia,
      tarifa_hora
    });
  }

  obtenerHabilidadesFreelancer(id_usuario: number): Observable<HabilidadModel[]> {
    return this.http.post<HabilidadModel[]>(this.obtenerHabilidadesUrl, { id_usuario });
  }

  vincularDesvincularHabilidad(id_usuario: number, id_habilidad: number, metodo: number) {
    return this.http.post(this.vincularDesvincularUrl, { id_usuario, id_habilidad, metodo });
  }

  solicitarHabilidad(id_usuario: number, descripcion: string, nombre: string) {
    return this.http.post(this.solicitarUrl, { id_usuario, descripcion, nombre });
  }

  obtenerProyectosAbiertos(): Observable<ProyectoModel[]> {
    return this.http.get<ProyectoModel[]>(this.obtenerProyectosAbiertosUrl);
  }

  registrarPropuesta(id_usuario: number, id_proyecto: number, monto: number, plazo_dias: number, descripcion: string) {
    return this.http.post(this.RegistrarPropuestaUrl, {
      id_usuario,
      id_proyecto,
      monto,
      plazo_dias,
      descripcion
    });
  }

  obtenerPropuestasFreelancer(id_usuario: number): Observable<PropuestaModel[]> {
    return this.http.post<PropuestaModel[]>(this.obtenerPropuestasUrl, { id_usuario });
  }

  retirarPropuestad(id_propuesta: number, id_proyecto: number) {
    return this.http.put(this.RetirarPropuestaUrl, { id_propuesta, id_proyecto });
  }

  obtenerContratosFreelancer(id_usuario: number): Observable<ContratosModel[]> {
    return this.http.post<ContratosModel[]>(this.obtenerContratosUrl, { id_usuario });
  }

  enviarEntrega(id_contrato: number, id_proyecto: number, descripcion: string, url: string) {
    return this.http.put(this.enviarEntregaUrl, { id_contrato, id_proyecto, descripcion, url });
  }

  obtenerEntregasFreelancer(id_usuario: number): Observable<EntregaModel[]> {
    return this.http.post<EntregaModel[]>(this.obtenerEntregasUrl, { id_usuario });
  }

  obtenerCalificaciones(id_usuario: number): Observable<CalificacionModel[]> {
    return this.http.put<CalificacionModel[]>(this.obtenerCalificacionesUrl, { id_usuario });
  }

}
