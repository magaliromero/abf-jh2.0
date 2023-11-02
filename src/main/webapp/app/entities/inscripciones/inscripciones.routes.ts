import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { InscripcionesComponent } from './list/inscripciones.component';
import { InscripcionesDetailComponent } from './detail/inscripciones-detail.component';
import { InscripcionesUpdateComponent } from './update/inscripciones-update.component';
import InscripcionesResolve from './route/inscripciones-routing-resolve.service';

const inscripcionesRoute: Routes = [
  {
    path: '',
    component: InscripcionesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InscripcionesDetailComponent,
    resolve: {
      inscripciones: InscripcionesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InscripcionesUpdateComponent,
    resolve: {
      inscripciones: InscripcionesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InscripcionesUpdateComponent,
    resolve: {
      inscripciones: InscripcionesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default inscripcionesRoute;
