import { Routes } from '@angular/router';
import { Home } from './home/home';
import { Login } from './login/login';
import { Register } from './register/register';
import { Perfil } from './perfil/perfil';
import { Detalle } from './proyectos/detalle/detalle';
import { Panel } from './panel/panel';
import { Metodologias } from './metodologias/metodologias';
import {Scrum} from './metodologias/scrum/scrum';
import {Kanban} from './metodologias/kanban/kanban';
import {Cascada} from './metodologias/cascada/cascada';
import {Espiral} from './metodologias/espiral/espiral';
import {Prototipos} from './metodologias/prototipos/prototipos';
import {Xp} from './metodologias/xp/xp';

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
    path: 'metodologias/scrum',
    component: Scrum,
  },
  {
    path: 'metodologias/kanban',
    component: Kanban,
  },
  {
    path: 'metodologias/cascada',
    component: Cascada,
  },
  {
    path: 'metodologias/espiral',
    component: Espiral,
  },
  {
    path: 'metodologias/prototipos',
    component: Prototipos,
  },
  {
    path: 'metodologias/xp',
    component: Xp,
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
