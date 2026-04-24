import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class FreelancerService {

  
  constructor(private http: HttpClient) { }

  Url = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/FreelancerServlet';

  registrarInfo(id_usuario: number, descripcion: string, experiencia: string, tarifa: number) {
    return this.http.post(this.Url, {
      id_usuario,
      descripcion,
      experiencia,
      tarifa
    });
  }
  
}
