import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NotaCreditoComponent } from '../list/nota-credito.component';
import { NotaCreditoDetailComponent } from '../detail/nota-credito-detail.component';
import { NotaCreditoUpdateComponent } from '../update/nota-credito-update.component';
import { NotaCreditoRoutingResolveService } from './nota-credito-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

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
];

@NgModule({
  imports: [RouterModule.forChild(notaCreditoRoute)],
  exports: [RouterModule],
})
export class NotaCreditoRoutingModule {}
