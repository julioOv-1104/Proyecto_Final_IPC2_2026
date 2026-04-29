import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MenuCliente } from '../../menus/menu-cliente/menu-cliente';
import { ClienteService } from '../../servicios/cliente.service';


@Component({
  selector: 'app-solicitar-categoria',
  imports: [FormsModule, CommonModule, MenuCliente],
  templateUrl: './solicitar-categoria.html',
  styleUrl: './solicitar-categoria.css',
})
export class SolicitarCategoria {

  constructor(private clienteService: ClienteService) { }

  mensajeError: string | null = null;
  descripcion: string = '';
  nombre: string = '';
  id_usuario: number = 0;

  

   ngOnInit() {
    const id = sessionStorage.getItem('usuario_id');
    if (id) {
      this.id_usuario = parseInt(id);
    }


  }

  solicitar() {

    if (!this.nombre || !this.descripcion) {
      this.mensajeError = 'Por favor, complete todos los campos.';
      return;
    }

    this.clienteService.solicitarCategoria(this.id_usuario, this.nombre, this.descripcion).subscribe({
      next: (response: any) => {

        // si recibe un error
        if (response.status === 'error') {
          this.mensajeError = response.mensaje;
          return;
        }

        console.log('Solicitud de categotia creada con exito');
        this.mensajeError = response.mensaje;
        return;

      }


    });


  }

}
