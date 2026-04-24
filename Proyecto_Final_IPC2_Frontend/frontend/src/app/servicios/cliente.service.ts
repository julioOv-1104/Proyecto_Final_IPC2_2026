import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root',
})
export class ClienteService {

  constructor(private http: HttpClient) { }

  Url = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ClienteServlet';

  registrarInfo(id_usuario: number, descripcion: string, sector: string, sitio_web: string) {
    return this.http.post(this.Url, {
      id_usuario,
      descripcion,
      sector,
      sitio_web
    });
  }

}
