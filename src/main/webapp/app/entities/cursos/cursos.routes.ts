import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CursosComponent } from './list/cursos.component';
import { CursosDetailComponent } from './detail/cursos-detail.component';
import { CursosUpdateComponent } from './update/cursos-update.component';
import CursosResolve from './route/cursos-routing-resolve.service';

const cursosRoute: Routes = [
  {
    path: '',
    component: CursosComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CursosDetailComponent,
    resolve: {
      cursos: CursosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CursosUpdateComponent,
    resolve: {
      cursos: CursosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CursosUpdateComponent,
    resolve: {
      cursos: CursosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cursosRoute;
