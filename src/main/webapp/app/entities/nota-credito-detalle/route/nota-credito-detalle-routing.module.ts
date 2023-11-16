import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NotaCreditoDetalleComponent } from '../list/nota-credito-detalle.component';
import { NotaCreditoDetalleDetailComponent } from '../detail/nota-credito-detalle-detail.component';
import { NotaCreditoDetalleUpdateComponent } from '../update/nota-credito-detalle-update.component';
import { NotaCreditoDetalleRoutingResolveService } from './nota-credito-detalle-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

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
      notaCreditoDetalle: NotaCreditoDetalleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NotaCreditoDetalleUpdateComponent,
    resolve: {
      notaCreditoDetalle: NotaCreditoDetalleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NotaCreditoDetalleUpdateComponent,
    resolve: {
      notaCreditoDetalle: NotaCreditoDetalleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(notaCreditoDetalleRoute)],
  exports: [RouterModule],
})
export class NotaCreditoDetalleRoutingModule {}
