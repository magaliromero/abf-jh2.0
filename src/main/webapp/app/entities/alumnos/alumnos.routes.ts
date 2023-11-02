import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AlumnosComponent } from './list/alumnos.component';
import { AlumnosDetailComponent } from './detail/alumnos-detail.component';
import { AlumnosUpdateComponent } from './update/alumnos-update.component';
import AlumnosResolve from './route/alumnos-routing-resolve.service';

const alumnosRoute: Routes = [
  {
    path: '',
    component: AlumnosComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AlumnosDetailComponent,
    resolve: {
      alumnos: AlumnosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AlumnosUpdateComponent,
    resolve: {
      alumnos: AlumnosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AlumnosUpdateComponent,
    resolve: {
      alumnos: AlumnosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default alumnosRoute;
