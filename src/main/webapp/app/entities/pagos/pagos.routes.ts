import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PagosComponent } from './list/pagos.component';
import { PagosDetailComponent } from './detail/pagos-detail.component';
import { PagosUpdateComponent } from './update/pagos-update.component';
import PagosResolve from './route/pagos-routing-resolve.service';

const pagosRoute: Routes = [
  {
    path: '',
    component: PagosComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PagosDetailComponent,
    resolve: {
      pagos: PagosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PagosUpdateComponent,
    resolve: {
      pagos: PagosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PagosUpdateComponent,
    resolve: {
      pagos: PagosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default pagosRoute;
