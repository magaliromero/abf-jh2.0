import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PuntoDeExpedicionComponent } from './list/punto-de-expedicion.component';
import { PuntoDeExpedicionDetailComponent } from './detail/punto-de-expedicion-detail.component';
import { PuntoDeExpedicionUpdateComponent } from './update/punto-de-expedicion-update.component';
import PuntoDeExpedicionResolve from './route/punto-de-expedicion-routing-resolve.service';

const puntoDeExpedicionRoute: Routes = [
  {
    path: '',
    component: PuntoDeExpedicionComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PuntoDeExpedicionDetailComponent,
    resolve: {
      puntoDeExpedicion: PuntoDeExpedicionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PuntoDeExpedicionUpdateComponent,
    resolve: {
      puntoDeExpedicion: PuntoDeExpedicionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PuntoDeExpedicionUpdateComponent,
    resolve: {
      puntoDeExpedicion: PuntoDeExpedicionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default puntoDeExpedicionRoute;
