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
import { ReportesAdmin } from './paginas/reportes-admin/reportes-admin';
import { RecargarSaldo } from './paginasCliente/recargar-saldo/recargar-saldo';
import { SolicitarCategoria } from './paginasCliente/solicitar-categoria/solicitar-categoria';
import { PublicarProyecto } from './paginasCliente/publicar-proyecto/publicar-proyecto';
import { EditarProyecto } from './paginasCliente/editar-proyecto/editar-proyecto';
import { GestionPropuestas } from './paginasCliente/gestion-propuestas/gestion-propuestas';
import { GestionEntregas } from './paginasCliente/gestion-entregas/gestion-entregas';
import { Calificacion } from './paginasCliente/calificacion/calificacion';
import { ReportesCliente } from './paginasCliente/reportes-cliente/reportes-cliente';
import { VinculaProyectoHabilidad } from './paginasCliente/vincula-proyecto-habilidad/vincula-proyecto-habilidad';




export const routes: Routes = [
    { path: '', redirectTo: 'registrarse', pathMatch: 'full' },
    { path: 'registrarse', component: Registrarse },
    { path: 'login', component: LoginForm },
    { path: 'completar-cliente', component: CompletarCliente },
    { path: 'completar-freelancer', component: CompletarFreelancer },
    { path: 'gestion', component: Gestion },
    { path: 'crear-categoria', component: CrearCategoria },
    { path: 'crear-habilidad', component: CrearHabilidad },
    { path: 'editar-categoria', component: EditarCategoria },
    { path: 'editar-habilidad', component: EditarHabilidad },
    { path: 'ver-solicitudes', component: VerSolicitudes },
    { path: 'reportes-admin', component: ReportesAdmin },
    { path: 'recargar-saldo', component: RecargarSaldo },
    { path: 'solicitar-categoria', component: SolicitarCategoria },
    {path: 'publicar-proyecto', component: PublicarProyecto},
    {path: 'editar-proyecto', component: EditarProyecto},
    { path: 'gestion-propuestas', component: GestionPropuestas },
    { path: 'gestion-entregas', component: GestionEntregas },
    { path: 'calificacion', component: Calificacion },
    { path: 'reportes-cliente', component: ReportesCliente },
    { path: 'vincula-habilidad', component: VinculaProyectoHabilidad },
    { path: 'crear-admins', component: CrearAdmins }
];
