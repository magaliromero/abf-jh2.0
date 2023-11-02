import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TimbradosComponent } from './list/timbrados.component';
import { TimbradosDetailComponent } from './detail/timbrados-detail.component';
import { TimbradosUpdateComponent } from './update/timbrados-update.component';
import TimbradosResolve from './route/timbrados-routing-resolve.service';

const timbradosRoute: Routes = [
  {
    path: '',
    component: TimbradosComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TimbradosDetailComponent,
    resolve: {
      timbrados: TimbradosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TimbradosUpdateComponent,
    resolve: {
      timbrados: TimbradosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TimbradosUpdateComponent,
    resolve: {
      timbrados: TimbradosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default timbradosRoute;
