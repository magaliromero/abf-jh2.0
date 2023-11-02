import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { EvaluacionesComponent } from './list/evaluaciones.component';
import { EvaluacionesDetailComponent } from './detail/evaluaciones-detail.component';
import { EvaluacionesUpdateComponent } from './update/evaluaciones-update.component';
import EvaluacionesResolve from './route/evaluaciones-routing-resolve.service';

const evaluacionesRoute: Routes = [
  {
    path: '',
    component: EvaluacionesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EvaluacionesDetailComponent,
    resolve: {
      evaluaciones: EvaluacionesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EvaluacionesUpdateComponent,
    resolve: {
      evaluaciones: EvaluacionesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EvaluacionesUpdateComponent,
    resolve: {
      evaluaciones: EvaluacionesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default evaluacionesRoute;
