import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-menu-cliente',
  imports: [RouterLink],
  templateUrl: './menu-cliente.html',
  styleUrl: './menu-cliente.css',
})
export class MenuCliente {

  constructor(private router: Router) { } //inyuectar el router

  isOpen: boolean = false;

  toggleMenu() {
    this.isOpen = !this.isOpen;
  }

  logout() {
    sessionStorage.removeItem('usuario');
    sessionStorage.removeItem('usuario_id');
    this.router.navigate(['/login']);
  }

}
