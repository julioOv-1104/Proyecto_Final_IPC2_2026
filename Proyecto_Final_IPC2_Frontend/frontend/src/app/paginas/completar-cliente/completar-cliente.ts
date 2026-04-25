import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ClienteService } from '../../servicios/cliente.service';

@Component({
  selector: 'app-completar-cliente',
  imports: [FormsModule, CommonModule],
  templateUrl: './completar-cliente.html',
  styleUrl: './completar-cliente.css',
})
export class CompletarCliente {

  constructor(private clienteService: ClienteService) {}

  descripcion: string = '';
  sector: string = '';
  sitio_web: string = '';
  mensajeError: string | null = null;
  id_usuario: number = 0;

  ngOnInit() {
    const id = sessionStorage.getItem('usuario_id');
    if (id) {
      this.id_usuario = parseInt(id);
    }
  }

registrarInfo(){

if (!this.descripcion || !this.sector) {
  this.mensajeError = 'Por favor, complete todos los campos obligatorios.';
  return;
}

this.clienteService.registrarInfo(this.id_usuario, this.descripcion, this.sector, this.sitio_web).subscribe({
  next: (response: any) => {
    
            // si recibe un error
            if (response.status === 'error') {
              this.mensajeError = response.mensaje;
              return;
            }
            
            console.log('Informacion registrada con exito', response);

            this.mensajeError = 'Informacion registrada con exito';
    
          }


    });

}

}
