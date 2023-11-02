import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { FacturaDetalleComponent } from './list/factura-detalle.component';
import { FacturaDetalleDetailComponent } from './detail/factura-detalle-detail.component';
import { FacturaDetalleUpdateComponent } from './update/factura-detalle-update.component';
import FacturaDetalleResolve from './route/factura-detalle-routing-resolve.service';

const facturaDetalleRoute: Routes = [
  {
    path: '',
    component: FacturaDetalleComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FacturaDetalleDetailComponent,
    resolve: {
      facturaDetalle: FacturaDetalleResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FacturaDetalleUpdateComponent,
    resolve: {
      facturaDetalle: FacturaDetalleResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FacturaDetalleUpdateComponent,
    resolve: {
      facturaDetalle: FacturaDetalleResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default facturaDetalleRoute;
