import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { FuncionariosComponent } from './list/funcionarios.component';
import { FuncionariosDetailComponent } from './detail/funcionarios-detail.component';
import { FuncionariosUpdateComponent } from './update/funcionarios-update.component';
import FuncionariosResolve from './route/funcionarios-routing-resolve.service';

const funcionariosRoute: Routes = [
  {
    path: '',
    component: FuncionariosComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FuncionariosDetailComponent,
    resolve: {
      funcionarios: FuncionariosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FuncionariosUpdateComponent,
    resolve: {
      funcionarios: FuncionariosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FuncionariosUpdateComponent,
    resolve: {
      funcionarios: FuncionariosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default funcionariosRoute;
