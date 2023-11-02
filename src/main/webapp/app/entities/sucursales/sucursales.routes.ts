import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { SucursalesComponent } from './list/sucursales.component';
import { SucursalesDetailComponent } from './detail/sucursales-detail.component';
import { SucursalesUpdateComponent } from './update/sucursales-update.component';
import SucursalesResolve from './route/sucursales-routing-resolve.service';

const sucursalesRoute: Routes = [
  {
    path: '',
    component: SucursalesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SucursalesDetailComponent,
    resolve: {
      sucursales: SucursalesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SucursalesUpdateComponent,
    resolve: {
      sucursales: SucursalesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SucursalesUpdateComponent,
    resolve: {
      sucursales: SucursalesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default sucursalesRoute;
