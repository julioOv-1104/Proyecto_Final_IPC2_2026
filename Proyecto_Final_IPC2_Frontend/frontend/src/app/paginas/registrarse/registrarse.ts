import { Component } from '@angular/core';
import { UsuarioModel } from '../../modelos/usuario-model';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UsuarioService } from '../../servicios/usuario.service';

@Component({
  selector: 'app-registrarse',
  imports: [FormsModule, CommonModule],
  templateUrl: './registrarse.html',
  styleUrl: './registrarse.css',
})
export class Registrarse {

  constructor(private usuarioService: UsuarioService) { }

  nombre_completo: string = '';
  username: string = '';
  password: string = '';
  correo: string = '';
  telefono: string = '';
  direccion: string = '';
  cui: string = '';
  fecha_nacimiento: Date | null = null;
  rol: number = 0;
  mensajeError: string | null = null;


  registrar() {

    if (!this.nombre_completo || !this.username || !this.password || !this.correo || !this.telefono || 
      !this.direccion || !this.cui || !this.fecha_nacimiento || this.rol === 0) {
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

        this.mensajeError = 'Registro exitoso, ahora puede iniciar sesión.';
        console.log('usuario registrado:', usuario);

      }


    });

  }

}
