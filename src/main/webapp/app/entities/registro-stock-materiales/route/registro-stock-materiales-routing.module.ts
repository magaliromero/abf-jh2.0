import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RegistroStockMaterialesComponent } from '../list/registro-stock-materiales.component';
import { RegistroStockMaterialesDetailComponent } from '../detail/registro-stock-materiales-detail.component';
import { RegistroStockMaterialesUpdateComponent } from '../update/registro-stock-materiales-update.component';
import { RegistroStockMaterialesRoutingResolveService } from './registro-stock-materiales-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const registroStockMaterialesRoute: Routes = [
  {
    path: '',
    component: RegistroStockMaterialesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RegistroStockMaterialesDetailComponent,
    resolve: {
      registroStockMateriales: RegistroStockMaterialesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RegistroStockMaterialesUpdateComponent,
    resolve: {
      registroStockMateriales: RegistroStockMaterialesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RegistroStockMaterialesUpdateComponent,
    resolve: {
      registroStockMateriales: RegistroStockMaterialesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(registroStockMaterialesRoute)],
  exports: [RouterModule],
})
export class RegistroStockMaterialesRoutingModule {}
