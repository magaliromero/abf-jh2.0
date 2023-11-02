import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { MaterialesComponent } from './list/materiales.component';
import { MaterialesDetailComponent } from './detail/materiales-detail.component';
import { MaterialesUpdateComponent } from './update/materiales-update.component';
import MaterialesResolve from './route/materiales-routing-resolve.service';

const materialesRoute: Routes = [
  {
    path: '',
    component: MaterialesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MaterialesDetailComponent,
    resolve: {
      materiales: MaterialesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MaterialesUpdateComponent,
    resolve: {
      materiales: MaterialesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MaterialesUpdateComponent,
    resolve: {
      materiales: MaterialesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default materialesRoute;
