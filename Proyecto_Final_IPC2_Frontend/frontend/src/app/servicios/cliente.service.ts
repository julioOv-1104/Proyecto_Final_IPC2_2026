import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ProyectoModel } from '../modelos/proyecto-model';
import { PropuestaModel } from '../modelos/propuesta-model';
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
  editarProyectoUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ClienteServlet?accion=editar';
  cancelarProyectoUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ClienteServlet?accion=cancelarProyecto';
  obtenerPropuestasUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ClienteServlet?accion=obtenerPropuestas';





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
    presupuesto_max:number, fecha_limite: string) {
    return this.http.post(this.publicarProyectoUrl, { id_usuario, id_categoria, titulo, descripcion, presupuesto_max,
      fecha_limite});
  }

  editarProyecto(id_proyecto: number, id_categoria: number, titulo: string, descripcion: string, 
    presupuesto_max:number, fecha_limite: string) {
    return this.http.post(this.editarProyectoUrl, { id_proyecto, id_categoria, titulo, descripcion, presupuesto_max,
      fecha_limite});
  }

   obtenerProyectosSinContrato(id_usuario: number): Observable<ProyectoModel[]> {
      return this.http.post<ProyectoModel[]>(this.obtenerProyectosSinContratoUrl,{id_usuario});
    }

    cancelarProyecto(id_proyecto: number) {
    return this.http.put(this.cancelarProyectoUrl, { id_proyecto });
  }

  obtenerPropuestas(id_proyecto: number): Observable<PropuestaModel[]> {
      return this.http.post<PropuestaModel[]>(this.obtenerPropuestasUrl,{id_proyecto});
    }

}
