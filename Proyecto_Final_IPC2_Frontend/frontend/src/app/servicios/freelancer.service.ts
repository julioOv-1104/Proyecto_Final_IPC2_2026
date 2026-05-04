import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HabilidadModel } from '../modelos/habilidad-model';
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

}
