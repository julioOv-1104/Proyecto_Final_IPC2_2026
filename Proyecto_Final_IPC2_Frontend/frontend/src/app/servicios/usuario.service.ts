import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UsuarioModel } from '../modelos/usuario-model';

@Injectable({
  providedIn: 'root',
})
export class UsuarioService {

  constructor(private http: HttpClient) {}

  Url = 'http://localhost:8080/Proyecto_Final_IPC2_Backend/UsuarioServlet';

  registrar(usuario: Partial<UsuarioModel>): Observable<UsuarioModel> {
    return this.http.put<UsuarioModel>(this.Url, usuario);
  }

  login(usuario: Partial<UsuarioModel>): Observable<UsuarioModel> {
    return this.http.post<UsuarioModel>(this.Url, usuario);
  }
  
}
