import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { RegistroClasesComponent } from './list/registro-clases.component';
import { RegistroClasesDetailComponent } from './detail/registro-clases-detail.component';
import { RegistroClasesUpdateComponent } from './update/registro-clases-update.component';
import RegistroClasesResolve from './route/registro-clases-routing-resolve.service';

const registroClasesRoute: Routes = [
  {
    path: '',
    component: RegistroClasesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RegistroClasesDetailComponent,
    resolve: {
      registroClases: RegistroClasesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RegistroClasesUpdateComponent,
    resolve: {
      registroClases: RegistroClasesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RegistroClasesUpdateComponent,
    resolve: {
      registroClases: RegistroClasesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default registroClasesRoute;
