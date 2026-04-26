import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class FreelancerService {

  
  constructor(private http: HttpClient) { }

  Url = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/FreelancerServlet';

  registrarInfo(id_usuario: number, biografia: string, nivel_experiencia: string, tarifa_hora: number) {
    return this.http.post(this.Url, {
      id_usuario,
      biografia,
      nivel_experiencia,
      tarifa_hora
    });
  }
  
}
