import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UsuarioModel } from '../modelos/usuario-model';

@Injectable({
  providedIn: 'root',
})
export class UsuarioService {

  constructor(private http: HttpClient) { }

  Url = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/UsuarioServlet';
  loginUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/UsuarioServlet?accion=login';
  comprobarUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/UsuarioServlet?accion=comprobar';
  registrarAdminUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/UsuarioServlet?accion=admin';
  estadoUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/UsuarioServlet?accion=estado';
  obtenerClientesUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/ClienteServlet';
  obtenerFreelancersUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/FreelancerServlet';
  obtenerSaldoUrl = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/UsuarioServlet?accion=obtenerSaldo';



  registrar(usuario: Partial<UsuarioModel>): Observable<UsuarioModel> {
    return this.http.put<UsuarioModel>(this.Url, usuario);
  }

  login(usuario: Partial<UsuarioModel>): Observable<UsuarioModel> {
    return this.http.post<UsuarioModel>(this.loginUrl, usuario);
  }

  comprobarInfo(username: string, tabla: string) {
    return this.http.post(this.comprobarUrl, { username, tabla });
  }

  registrarAdmin(id_usuario: number) {
    return this.http.post(this.registrarAdminUrl, { id_usuario });
  }

  obtenerClientes(): Observable<UsuarioModel[]> {
    return this.http.get<UsuarioModel[]>(this.obtenerClientesUrl);
  }

  obtenerFreelancers(): Observable<UsuarioModel[]> {
    return this.http.get<UsuarioModel[]>(this.obtenerFreelancersUrl);
  }

  activarDesactivar(username: string) {
    return this.http.post(this.estadoUrl, { username });
  }

  obtenerSaldo(id_usuario: number):  Observable<number> {
    return this.http.post<number>(this.obtenerSaldoUrl, {id_usuario});
  }


}
