import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { FreelancerService } from '../../servicios/freelancer.service';

@Component({
  selector: 'app-completar-freelancer',
  imports: [FormsModule, CommonModule],
  templateUrl: './completar-freelancer.html',
  styleUrl: './completar-freelancer.css',
})
export class CompletarFreelancer {

  constructor(private freelancerService: FreelancerService) {}

  id_usuario: number = 0;
  biografia: string = '';
  nivel_experiencia: string = '';
  tarifa_hora:number = 0;
  mensajeError: string | null = null

    ngOnInit() {
    const id = sessionStorage.getItem('usuario_id');
    if (id) {
      this.id_usuario = parseInt(id, 10);
    }
  }

  registrarInfo(){

if (!this.biografia || !this.nivel_experiencia || !this.tarifa_hora) {
  this.mensajeError = 'Por favor, complete todos los campos obligatorios.';
  return;
}

this.freelancerService.registrarInfo(this.id_usuario, this.biografia, this.nivel_experiencia, this.tarifa_hora).subscribe({
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
