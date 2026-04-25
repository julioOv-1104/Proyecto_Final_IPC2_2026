import { Component } from '@angular/core';
import { UsuarioModel } from '../../modelos/usuario-model';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UsuarioService } from '../../servicios/usuario.service';
import { MenuAdmin } from '../../menus/menu-admin/menu-admin';

@Component({
  selector: 'app-crear-admins',
  imports: [FormsModule, CommonModule, MenuAdmin],
  templateUrl: './crear-admins.html',
  styleUrl: './crear-admins.css',
})
export class CrearAdmins {

  constructor(private usuarioService: UsuarioService) { }

  id_usuario: number = 0;//el id se obtiene del backend
  nombre_completo: string = '';
  username: string = '';
  password: string = '';
  correo: string = '';
  telefono: string = '';
  direccion: string = '';
  cui: string = '';
  fecha_nacimiento: Date | null = null;
  rol: number = 1;//Rol = 1 para admins
  mensajeError: string | null = null;

  registrarAdmin() {

    if (!this.nombre_completo || !this.username || !this.password || !this.correo || !this.telefono ||
      !this.direccion || !this.cui || !this.fecha_nacimiento) {
      this.mensajeError = 'Por favor, complete todos los campos.';
      return;
    }


    const nuevoUsuario = {

      nombre_completo: this.nombre_completo,
      username: this.username,
      password: this.password,
      correo: this.correo,
      telefono: this.telefono,
      direccion: this.direccion,
      cui: this.cui,
      fecha_nacimiento: this.fecha_nacimiento,
      rol: this.rol
    };

    this.usuarioService.registrar(nuevoUsuario).subscribe({
      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          return;
        }

        const usuario = response as UsuarioModel;
        console.log('Registro exitoso, usuario creado:', usuario);

        this.id_usuario = usuario.id_usuario;

        console.log('id del usuario nuevo: ', this.id_usuario);

      }


    });

    this.vincularAdmin();

  }

  vincularAdmin(){
    this.usuarioService.registrarAdmin(this.id_usuario).subscribe({
      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          return;
        }
        console.log('Administrador registrado en la Base de Datos');

        this.mensajeError = 'Administrador registrado exitosamente.';

      }


    });
  }

}
