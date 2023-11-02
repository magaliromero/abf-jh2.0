import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TemasComponent } from './list/temas.component';
import { TemasDetailComponent } from './detail/temas-detail.component';
import { TemasUpdateComponent } from './update/temas-update.component';
import TemasResolve from './route/temas-routing-resolve.service';

const temasRoute: Routes = [
  {
    path: '',
    component: TemasComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TemasDetailComponent,
    resolve: {
      temas: TemasResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TemasUpdateComponent,
    resolve: {
      temas: TemasResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TemasUpdateComponent,
    resolve: {
      temas: TemasResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default temasRoute;
