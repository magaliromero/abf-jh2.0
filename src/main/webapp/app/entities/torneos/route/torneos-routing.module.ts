import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TorneosComponent } from '../list/torneos.component';
import { TorneosDetailComponent } from '../detail/torneos-detail.component';
import { TorneosUpdateComponent } from '../update/torneos-update.component';
import { TorneosRoutingResolveService } from './torneos-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const torneosRoute: Routes = [
  {
    path: '',
    component: TorneosComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TorneosDetailComponent,
    resolve: {
      torneos: TorneosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TorneosUpdateComponent,
    resolve: {
      torneos: TorneosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TorneosUpdateComponent,
    resolve: {
      torneos: TorneosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(torneosRoute)],
  exports: [RouterModule],
})
export class TorneosRoutingModule {}
