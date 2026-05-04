import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-menu-freelancer',
  imports: [RouterLink],
  templateUrl: './menu-freelancer.html',
  styleUrl: './menu-freelancer.css',
})
export class MenuFreelancer {

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
