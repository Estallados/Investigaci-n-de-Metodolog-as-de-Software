import { Routes } from '@angular/router';
import { Home } from './home/home';
import { Login } from './login/login';
import { Metodologias } from './metodologias/metodologias';
import { Register } from './register/register';
import { Perfil } from './perfil/perfil';
import { Proyectos } from './proyectos/proyectos';
import { Detalle } from './proyectos/detalle/detalle';
import { Panel } from './panel/panel';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },
  {
    path: 'home',
    component: Home
  },
  {
    path: 'metodologias',
    component: Metodologias
  },
  {
    path: 'login',
    component: Login
  },
  {
    path: 'register',
    component: Register
  },
  {
    path: 'perfil',
    component: Perfil
  },
  {
    path: 'panel',
    component: Panel
  },
  {
    path: 'proyectos',
    component: Proyectos
  },
  {
    path: 'proyectos/:proyecto',
    component: Detalle
  },
  {
    path: '**',
    redirectTo: 'Login'
  }
];

