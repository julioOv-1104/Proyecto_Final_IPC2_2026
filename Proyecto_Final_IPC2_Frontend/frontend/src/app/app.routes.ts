import { Routes } from '@angular/router';
import { Registrarse } from './paginas/registrarse/registrarse';
import { LoginForm } from './paginas/login-form/login-form';
import { CompletarCliente } from './paginas/completar-cliente/completar-cliente';
import { CompletarFreelancer } from './paginas/completar-freelancer/completar-freelancer';
import { CrearAdmins } from './paginas/crear-admins/crear-admins';
import { Gestion } from './paginas/gestion/gestion';
import { CrearCategoria } from './paginas/crear-categoria/crear-categoria';
import { CrearHabilidad } from './paginas/crear-habilidad/crear-habilidad';
import { EditarCategoria } from './paginas/editar-categoria/editar-categoria';
import { EditarHabilidad } from './paginas/editar-habilidad/editar-habilidad';
import { VerSolicitudes } from './paginas/ver-solicitudes/ver-solicitudes';

export const routes: Routes = [
    {path: '', redirectTo: 'registrarse', pathMatch: 'full'},
    {path: 'registrarse', component: Registrarse},
    {path: 'login', component: LoginForm},
    {path: 'completar-cliente', component: CompletarCliente},
    {path: 'completar-freelancer', component: CompletarFreelancer},
    {path: 'gestion', component: Gestion},
    {path: 'crear-categoria', component: CrearCategoria},
    {path: 'crear-habilidad', component: CrearHabilidad},
    {path: 'editar-categoria', component: EditarCategoria},
    {path: 'editar-habilidad', component: EditarHabilidad},
    {path: 'ver-solicitudes', component: VerSolicitudes},
    {path: 'crear-admins', component: CrearAdmins}
];
