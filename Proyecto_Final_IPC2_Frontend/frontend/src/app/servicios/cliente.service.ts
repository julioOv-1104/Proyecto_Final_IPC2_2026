import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root',
})
export class ClienteService {

  constructor(private http: HttpClient) { }

  Url = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ClienteServlet?accion=completar';
  recargarUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ClienteServlet?accion=recargar';
  solicitarUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ClienteServlet?accion=solicitar';

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

}
