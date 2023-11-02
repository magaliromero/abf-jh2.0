import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { NotaCreditoDetalleComponent } from './list/nota-credito-detalle.component';
import { NotaCreditoDetalleDetailComponent } from './detail/nota-credito-detalle-detail.component';
import { NotaCreditoDetalleUpdateComponent } from './update/nota-credito-detalle-update.component';
import NotaCreditoDetalleResolve from './route/nota-credito-detalle-routing-resolve.service';

const notaCreditoDetalleRoute: Routes = [
  {
    path: '',
    component: NotaCreditoDetalleComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NotaCreditoDetalleDetailComponent,
    resolve: {
      notaCreditoDetalle: NotaCreditoDetalleResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NotaCreditoDetalleUpdateComponent,
    resolve: {
      notaCreditoDetalle: NotaCreditoDetalleResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NotaCreditoDetalleUpdateComponent,
    resolve: {
      notaCreditoDetalle: NotaCreditoDetalleResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default notaCreditoDetalleRoute;
