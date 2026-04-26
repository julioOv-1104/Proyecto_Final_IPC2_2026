import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ComisionModel } from '../modelos/comision-model';
import { CategoriaModel } from '../modelos/categoria-model';
import { HabilidadModel } from '../modelos/habilidad-model';
import { SolicitudModel } from '../modelos/solicitud-model';

@Injectable({
  providedIn: 'root',
})
export class AdminService {

  constructor(private http: HttpClient) { }

  obtenerComisionUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/AdminServlet?accion=comisionActual';
  cambiarComisionUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/AdminServlet?accion=cambiarComision';
  crearCategoriaUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/AdminServlet?accion=crearCategoria';
  crearHabilidadUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/AdminServlet?accion=crearHabilidad';
  obtenerCategoriaUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/AdminServlet?accion=categorias';
  obtenerHabilidadesUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/AdminServlet?accion=habilidades';
  editarCategoriaUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/AdminServlet?accion=editarCategoria';
  editarHabilidadUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/AdminServlet?accion=editarHabilidad';
  activarDesactivarCategoriaUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/AdminServlet?accion=activarDesactivarCategoria';
  activarDesactivarHabilidadUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/AdminServlet?accion=activarDesactivarHabilidad';
  obtenerSolicitudCategoriaUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/AdminServlet?accion=solicitudesCategorias';
  obtenerSolicitudHabilidadUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/AdminServlet?accion=solicitudesHabilidades';
  gestionarSolicitudCategoriaUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/AdminServlet?accion=aceptarSolicitudCategoria';
  gestionarSolicitudHabilidadUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/AdminServlet?accion=aceptarSolicitudHabilidad';




  obtenerComision(): Observable<ComisionModel> {
    return this.http.get<ComisionModel>(this.obtenerComisionUrl);
  }

  comprobarInfo(porcentaje: number) {
    return this.http.put(this.cambiarComisionUrl, { porcentaje });
  }

  crearCategoria(nombre: string, descripcion: string) {
    return this.http.put(this.crearCategoriaUrl, { nombre, descripcion });
  }

  crearHabilidad(id_categoria: number, nombre: string, descripcion: string) {
    return this.http.put(this.crearHabilidadUrl, { id_categoria, nombre, descripcion });
  }

  obtenerCategorias(): Observable<CategoriaModel[]> {
    return this.http.get<CategoriaModel[]>(this.obtenerCategoriaUrl);
  }

  obtenerHabilidades(): Observable<HabilidadModel[]> {
    return this.http.get<HabilidadModel[]>(this.obtenerHabilidadesUrl);
  }

  editarCategoria(categoria: Partial<CategoriaModel>) {
    return this.http.post(this.editarCategoriaUrl, categoria);
  }

  editarHabilidad(habilidad: Partial<HabilidadModel>) {
    return this.http.post(this.editarHabilidadUrl, habilidad);
  }

  activarDesactivarCategoria(id_categoria: number) {
    return this.http.post(this.activarDesactivarCategoriaUrl, { id_categoria });
  }

  activarDesactivarHabilidad(id_habilidad: number) {
    return this.http.post(this.activarDesactivarHabilidadUrl, { id_habilidad });
  }

  obtenerSolicitudesCategorias(): Observable<SolicitudModel[]> {
    return this.http.get<SolicitudModel[]>(this.obtenerSolicitudCategoriaUrl);
  }

  obtenerSolicitudesHabilidades(): Observable<SolicitudModel[]> {
    return this.http.get<SolicitudModel[]>(this.obtenerSolicitudHabilidadUrl);
  }

  gestionarSolicitudCategoria(id_solicitud: number, estado: string, nombre: string, descripcion: string) {
    return this.http.put(this.gestionarSolicitudCategoriaUrl, { id_solicitud, estado, nombre, descripcion });
  }

  gestionarSolicitudHabilidad(id_solicitud: number, estado: string, nombre: string, descripcion: string) {
    return this.http.put(this.gestionarSolicitudHabilidadUrl, { id_solicitud, estado, nombre, descripcion });
  }

}
