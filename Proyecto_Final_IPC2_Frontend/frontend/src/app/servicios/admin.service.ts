import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ComisionModel } from '../modelos/comision-model';

@Injectable({
  providedIn: 'root',
})
export class AdminService {

  constructor(private http: HttpClient) { }

  obtenerComisionUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/AdminServlet?accion=comisionActual';
  cambiarComisionUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/AdminServlet?accion=cambiarComision';


  obtenerComision(): Observable<ComisionModel> {
    return this.http.get<ComisionModel>(this.obtenerComisionUrl);
  }

  comprobarInfo(porcentaje: number) {
    return this.http.put(this.cambiarComisionUrl, { porcentaje });
  }

}
