import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { UsuarioService } from '../../servicios/usuario.service';
import { UsuarioModel } from '../../modelos/usuario-model';

@Component({
  selector: 'app-login-form',
  imports: [FormsModule, CommonModule],
  templateUrl: './login-form.html',
  styleUrl: './login-form.css',
})
export class LoginForm {

constructor(private usuarioService: UsuarioService, private router: Router) {}

  mensajeError: string | null = null;
  username: string = '';
  password: string = '';


  login(){
    console.log(this.username, this.password);

    this.mensajeError = null; // Limpiar mensaje de error

    if (!this.username || !this.password) {
      this.mensajeError = 'Por favor ingrese sus credenciales.';
      return;
    }

    const usuarioLogin = {
      username: this.username,
      password: this.password
    }

    this.usuarioService.login(usuarioLogin).subscribe({
      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          return;
        }

        // si recibe un usuario
        const usuario = response as UsuarioModel;

        

        sessionStorage.setItem('usuario', JSON.stringify(response));
        sessionStorage.setItem('usuario_id', response.id_usuario);
        console.log('Login correcto, usuario guardado en sesion:', sessionStorage.getItem('usuario'));

        console.log('rol del usuario:', response.rol);

        if(response.activo === false){
          this.mensajeError = 'Usuario inactivo. No puede acceder.';
          return;
        }

        switch (response.rol) {//redirecciona segun el rol del usuario
          case 2 : this.router.navigate(['/completar-cliente']); break;
          case 3 : this.router.navigate(['/completar-freelancer']); break;
          default: this.router.navigate(['']); break;
        }

      }
    });



  }

}
