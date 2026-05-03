import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ProyectoModel } from '../modelos/proyecto-model';
import { PropuestaModel } from '../modelos/propuesta-model';
import { EntregaModel } from '../modelos/entrega-model';
import { HabilidadModel } from '../modelos/habilidad-model';
import { FreelancerCompletado } from '../modelos/freelancer-completado';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root',
})
export class ClienteService {

  constructor(private http: HttpClient) { }

  Url = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ClienteServlet?accion=completar';
  recargarUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ClienteServlet?accion=recargar';
  solicitarUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ClienteServlet?accion=solicitar';
  publicarProyectoUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ClienteServlet?accion=publicar';
  obtenerProyectosSinContratoUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ClienteServlet?accion=proyectosSinContrato';
  obtenerProyectosConEntregasUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ClienteServlet?accion=proyectosConEntregas';
  editarProyectoUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ClienteServlet?accion=editar';
  cancelarProyectoUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ClienteServlet?accion=cancelarProyecto';
  obtenerPropuestasUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ClienteServlet?accion=obtenerPropuestas';
  aceptarPropuestaUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ClienteServlet?accion=aceptarPropuesta';
  rechazarPropuestaUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ClienteServlet?accion=rechazarPropuesta';
  obtenerEntregasUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ClienteServlet?accion=obtenerEntregas';
  rechazarEntregaUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ClienteServlet?accion=rechazarEntrega';
  aceptarEntregaUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ClienteServlet?accion=aceptarEntrega';
  cancelarContratoUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ClienteServlet?accion=cancelarContrato';
  obtenerFreelancersUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ClienteServlet?accion=obtenerFreelancersCalificables';
  calificarUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ClienteServlet?accion=calificar';
  obtenerHabilidadesDeProyectoUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ClienteServlet?accion=obtenerHabilidadesDeProyecto';
  vincularDesvincularUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ClienteServlet?accion=vincular';






  registrarInfo(id_usuario: number, descripcion: string, sector: string, sitio_web: string) {
    return this.http.post(this.Url, {
      id_usuario,
      descripcion,
      sector,
      sitio_web
    });
  }

  recargarSaldo(id_usuario: number, recarga: number) {
    return this.http.put(this.recargarUrl, { id_usuario, recarga });
  }

  solicitarCategoria(id_usuario: number, descripcion: string, nombre: string) {
    return this.http.post(this.solicitarUrl, { id_usuario, descripcion, nombre });
  }

  publicarProyecto(id_usuario: number, id_categoria: number, titulo: string, descripcion: string,
    presupuesto_max: number, fecha_limite: string) {
    return this.http.post(this.publicarProyectoUrl, {
      id_usuario, id_categoria, titulo, descripcion, presupuesto_max,
      fecha_limite
    });
  }

  editarProyecto(id_proyecto: number, id_categoria: number, titulo: string, descripcion: string,
    presupuesto_max: number, fecha_limite: string) {
    return this.http.post(this.editarProyectoUrl, {
      id_proyecto, id_categoria, titulo, descripcion, presupuesto_max,
      fecha_limite
    });
  }

  obtenerProyectosSinContrato(id_usuario: number): Observable<ProyectoModel[]> {
    return this.http.post<ProyectoModel[]>(this.obtenerProyectosSinContratoUrl, { id_usuario });
  }

  obtenerProyectosConEntregas(id_usuario: number): Observable<ProyectoModel[]> {
    return this.http.post<ProyectoModel[]>(this.obtenerProyectosConEntregasUrl, { id_usuario });
  }

  cancelarProyecto(id_proyecto: number) {
    return this.http.put(this.cancelarProyectoUrl, { id_proyecto });
  }

  obtenerPropuestas(id_proyecto: number): Observable<PropuestaModel[]> {
    return this.http.post<PropuestaModel[]>(this.obtenerPropuestasUrl, { id_proyecto });
  }

  aceptarPropuesta(id_propuesta: number, id_usuario: number) {
    return this.http.put(this.aceptarPropuestaUrl, { id_propuesta, id_usuario });
  }

  rechazarPropuesta(id_propuesta: number, motivo: string) {
    return this.http.put(this.rechazarPropuestaUrl, { id_propuesta, motivo });
  }

  obtenerEntregas(id_proyecto: number): Observable<EntregaModel[]> {
    return this.http.post<EntregaModel[]>(this.obtenerEntregasUrl, { id_proyecto });
  }

  rechazarEntrega(id_proyecto: number, id_entrega: number, motivo: string) {
    return this.http.put(this.rechazarEntregaUrl, { id_proyecto, id_entrega, motivo });
  }

  aceptarEntrega(id_proyecto: number, id_entrega: number, id_contrato: number) {
    return this.http.put(this.aceptarEntregaUrl, { id_proyecto, id_entrega, id_contrato });
  }

  cancelarContrato(id_proyecto: number, id_contrato: number, motivo: string) {
    return this.http.put(this.cancelarContratoUrl, { id_proyecto, id_contrato, motivo });
  }

  obtenerFreelancers(id_cliente: number): Observable<FreelancerCompletado[]> {
    return this.http.post<FreelancerCompletado[]>(this.obtenerFreelancersUrl, { id_cliente });
  }

  calificar(id_contrato: number, id_cliente: number, id_freelancer: number, puntuacion: number,
    comentario: string) {
    return this.http.put(this.calificarUrl, {
      id_contrato, id_cliente,
      id_freelancer, puntuacion, comentario
    });
  }

  obtenerHabilidadesDeProyecto(id_proyecto: number): Observable<HabilidadModel[]> {
    return this.http.post<HabilidadModel[]>(this.obtenerHabilidadesDeProyectoUrl, { id_proyecto });
  }

  vincularDesvincularHabilidad(id_proyecto: number, id_habilidad: number, metodo: number) {
    return this.http.post(this.vincularDesvincularUrl, { id_proyecto, id_habilidad, metodo });
  }


}
