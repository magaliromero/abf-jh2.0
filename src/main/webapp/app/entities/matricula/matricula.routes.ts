import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { MatriculaComponent } from './list/matricula.component';
import { MatriculaDetailComponent } from './detail/matricula-detail.component';
import { MatriculaUpdateComponent } from './update/matricula-update.component';
import MatriculaResolve from './route/matricula-routing-resolve.service';

const matriculaRoute: Routes = [
  {
    path: '',
    component: MatriculaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MatriculaDetailComponent,
    resolve: {
      matricula: MatriculaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MatriculaUpdateComponent,
    resolve: {
      matricula: MatriculaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MatriculaUpdateComponent,
    resolve: {
      matricula: MatriculaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default matriculaRoute;
