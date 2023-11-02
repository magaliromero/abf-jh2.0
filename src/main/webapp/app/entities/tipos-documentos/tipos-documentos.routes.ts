import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TiposDocumentosComponent } from './list/tipos-documentos.component';
import { TiposDocumentosDetailComponent } from './detail/tipos-documentos-detail.component';
import { TiposDocumentosUpdateComponent } from './update/tipos-documentos-update.component';
import TiposDocumentosResolve from './route/tipos-documentos-routing-resolve.service';

const tiposDocumentosRoute: Routes = [
  {
    path: '',
    component: TiposDocumentosComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TiposDocumentosDetailComponent,
    resolve: {
      tiposDocumentos: TiposDocumentosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TiposDocumentosUpdateComponent,
    resolve: {
      tiposDocumentos: TiposDocumentosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TiposDocumentosUpdateComponent,
    resolve: {
      tiposDocumentos: TiposDocumentosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default tiposDocumentosRoute;
