import { Routes } from '@angular/router';
import { Registrarse } from './paginas/registrarse/registrarse';
import { LoginForm } from './paginas/login-form/login-form';
import { CompletarCliente } from './paginas/completar-cliente/completar-cliente';
import { CompletarFreelancer } from './paginas/completar-freelancer/completar-freelancer';
import { CrearAdmins } from './paginas/crear-admins/crear-admins';
import { Gestion } from './paginas/gestion/gestion';

export const routes: Routes = [
    {path: '', redirectTo: 'registrarse', pathMatch: 'full'},
    {path: 'registrarse', component: Registrarse},
    {path: 'login', component: LoginForm},
    {path: 'completar-cliente', component: CompletarCliente},
    {path: 'completar-freelancer', component: CompletarFreelancer},
    {path: 'gestion', component: Gestion},
    {path: 'crear-admins', component: CrearAdmins}
];
