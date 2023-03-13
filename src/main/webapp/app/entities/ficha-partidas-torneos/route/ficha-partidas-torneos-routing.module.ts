import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FichaPartidasTorneosComponent } from '../list/ficha-partidas-torneos.component';
import { FichaPartidasTorneosDetailComponent } from '../detail/ficha-partidas-torneos-detail.component';
import { FichaPartidasTorneosUpdateComponent } from '../update/ficha-partidas-torneos-update.component';
import { FichaPartidasTorneosRoutingResolveService } from './ficha-partidas-torneos-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const fichaPartidasTorneosRoute: Routes = [
  {
    path: '',
    component: FichaPartidasTorneosComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FichaPartidasTorneosDetailComponent,
    resolve: {
      fichaPartidasTorneos: FichaPartidasTorneosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FichaPartidasTorneosUpdateComponent,
    resolve: {
      fichaPartidasTorneos: FichaPartidasTorneosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FichaPartidasTorneosUpdateComponent,
    resolve: {
      fichaPartidasTorneos: FichaPartidasTorneosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fichaPartidasTorneosRoute)],
  exports: [RouterModule],
})
export class FichaPartidasTorneosRoutingModule {}
