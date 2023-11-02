import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { EvaluacionesDetalleComponent } from './list/evaluaciones-detalle.component';
import { EvaluacionesDetalleDetailComponent } from './detail/evaluaciones-detalle-detail.component';
import { EvaluacionesDetalleUpdateComponent } from './update/evaluaciones-detalle-update.component';
import EvaluacionesDetalleResolve from './route/evaluaciones-detalle-routing-resolve.service';

const evaluacionesDetalleRoute: Routes = [
  {
    path: '',
    component: EvaluacionesDetalleComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EvaluacionesDetalleDetailComponent,
    resolve: {
      evaluacionesDetalle: EvaluacionesDetalleResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EvaluacionesDetalleUpdateComponent,
    resolve: {
      evaluacionesDetalle: EvaluacionesDetalleResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EvaluacionesDetalleUpdateComponent,
    resolve: {
      evaluacionesDetalle: EvaluacionesDetalleResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default evaluacionesDetalleRoute;
