import { Component } from '@angular/core';
import { MenuCliente } from '../../menus/menu-cliente/menu-cliente';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ClienteService } from '../../servicios/cliente.service';
import { UsuarioService } from '../../servicios/usuario.service';

@Component({
  selector: 'app-recargar-saldo',
  imports: [FormsModule, CommonModule, MenuCliente],
  templateUrl: './recargar-saldo.html',
  styleUrl: './recargar-saldo.css',
})
export class RecargarSaldo {

  constructor(private clienteService: ClienteService, private usuarioService: UsuarioService) { }

  mensajeError: string | null = null;
  monto: number = 0;
  id_usuario: number = 0;
  saldo: number = 0;

  ngOnInit() {
    const id = sessionStorage.getItem('usuario_id');
    if (id) {
      this.id_usuario = parseInt(id);
    }

    this.obtenerSaldo();

  }

  obtenerSaldo(){

    this.usuarioService.obtenerSaldo(this.id_usuario).subscribe({
      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          return;
        }

        console.log('Saldo de usuario: Q' + this.saldo);
        this.saldo = response;
        return;

      }


    });
    
  }

  recargar() {

    if (this.monto <= 0) {
      this.mensajeError = 'Por favor, ingrese un monto valido';
      return;
    }

    this.clienteService.recargarSaldo(this.id_usuario, this.monto).subscribe({
      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          return;
        }

        console.log('Saldo de Q' + this.monto + ' con exito');
        this.mensajeError = response.mensaje;
        this.obtenerSaldo();
        return;

      }


    });

  }

}
