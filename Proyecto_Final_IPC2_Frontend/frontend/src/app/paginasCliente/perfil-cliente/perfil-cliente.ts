import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MenuCliente } from '../../menus/menu-cliente/menu-cliente';
import { UsuarioService } from '../../servicios/usuario.service';
import { UsuarioModel } from '../../modelos/usuario-model';

@Component({
  selector: 'app-perfil-cliente',
  imports: [FormsModule, CommonModule, MenuCliente],
  templateUrl: './perfil-cliente.html',
  styleUrl: './perfil-cliente.css',
})
export class PerfilCliente {

  constructor(private usuarioService: UsuarioService) { }

  id_usuario: number = 0;
  mensajeError: string | null = null;
  usuario: UsuarioModel[] = [];


  ngOnInit() {
    const id = sessionStorage.getItem('usuario_id');
    if (id) {
      this.id_usuario = parseInt(id);
    }

    this.obtenerPerfil();



  }

  obtenerPerfil() {

    this.usuarioService.obtenerPerfil(this.id_usuario).subscribe({

      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          return;
        }

        console.log('perfil cargado con exito');
        this.usuario = response;
        return;

      }



    });

  }

}
