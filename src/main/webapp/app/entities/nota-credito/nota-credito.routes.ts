import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { NotaCreditoComponent } from './list/nota-credito.component';
import { NotaCreditoDetailComponent } from './detail/nota-credito-detail.component';
import { NotaCreditoUpdateComponent } from './update/nota-credito-update.component';
import NotaCreditoResolve from './route/nota-credito-routing-resolve.service';

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
      notaCredito: NotaCreditoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NotaCreditoUpdateComponent,
    resolve: {
      notaCredito: NotaCreditoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NotaCreditoUpdateComponent,
    resolve: {
      notaCredito: NotaCreditoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default notaCreditoRoute;
