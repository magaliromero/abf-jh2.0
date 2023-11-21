import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NotaCreditoComponent } from '../list/nota-credito.component';
import { NotaCreditoDetailComponent } from '../detail/nota-credito-detail.component';
import { NotaCreditoUpdateComponent } from '../update/nota-credito-update.component';
import { NotaCreditoRoutingResolveService } from './nota-credito-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';
import { FacturasService } from 'app/entities/facturas/service/facturas.service';
import { FacturasRoutingResolveService } from 'app/entities/facturas/route/facturas-routing-resolve.service';

const notaCreditoRoute: Routes = [
  {
    path: '',
    component: NotaCreditoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NotaCreditoDetailComponent,
    resolve: {
      notaCredito: NotaCreditoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NotaCreditoUpdateComponent,
    resolve: {
      notaCredito: NotaCreditoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NotaCreditoUpdateComponent,
    resolve: {
      notaCredito: NotaCreditoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/factura',
    component: NotaCreditoUpdateComponent,
    resolve: {
      facturas: FacturasRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(notaCreditoRoute)],
  exports: [RouterModule],
})
export class NotaCreditoRoutingModule {}
