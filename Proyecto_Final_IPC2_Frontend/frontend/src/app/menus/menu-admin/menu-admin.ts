import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-menu-admin',
  imports: [RouterLink],
  templateUrl: './menu-admin.html',
  styleUrl: './menu-admin.css',
})
export class MenuAdmin {

   constructor(private router: Router) {} //inyuectar el router

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
