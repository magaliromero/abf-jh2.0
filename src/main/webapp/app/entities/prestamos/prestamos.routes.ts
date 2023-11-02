import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PrestamosComponent } from './list/prestamos.component';
import { PrestamosDetailComponent } from './detail/prestamos-detail.component';
import { PrestamosUpdateComponent } from './update/prestamos-update.component';
import PrestamosResolve from './route/prestamos-routing-resolve.service';

const prestamosRoute: Routes = [
  {
    path: '',
    component: PrestamosComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PrestamosDetailComponent,
    resolve: {
      prestamos: PrestamosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PrestamosUpdateComponent,
    resolve: {
      prestamos: PrestamosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PrestamosUpdateComponent,
    resolve: {
      prestamos: PrestamosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default prestamosRoute;
