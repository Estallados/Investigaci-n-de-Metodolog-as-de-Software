import { Routes } from '@angular/router';
import { Home } from './home/home';
import { Login } from './login/login';
import { Register } from './register/register';
import { Perfil } from './perfil/perfil';
import { Detalle } from './proyectos/detalle/detalle';
import { Panel } from './panel/panel';
import { Metodologias } from './metodologias/metodologias';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',
  },
  {
    path: 'home',
    component: Home,
  },
  {
    path: 'metodologias',
    component: Metodologias,
  },
  {
    path: 'login',
    component: Login,
  },
  {
    path: 'register',
    component: Register,
  },
  {
    path: 'perfil',
    component: Perfil,
  },
  {
    path: 'panel',
    component: Panel,
  },
  {
    path: 'proyectos/:id',
    component: Detalle,
  },
  {
    path: '**',
    redirectTo: 'login',
  },
];
