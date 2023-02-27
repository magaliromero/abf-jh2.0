import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MallaCurricularComponent } from '../list/malla-curricular.component';
import { MallaCurricularDetailComponent } from '../detail/malla-curricular-detail.component';
import { MallaCurricularUpdateComponent } from '../update/malla-curricular-update.component';
import { MallaCurricularRoutingResolveService } from './malla-curricular-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const mallaCurricularRoute: Routes = [
  {
    path: '',
    component: MallaCurricularComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MallaCurricularDetailComponent,
    resolve: {
      mallaCurricular: MallaCurricularRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MallaCurricularUpdateComponent,
    resolve: {
      mallaCurricular: MallaCurricularRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MallaCurricularUpdateComponent,
    resolve: {
      mallaCurricular: MallaCurricularRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(mallaCurricularRoute)],
  exports: [RouterModule],
})
export class MallaCurricularRoutingModule {}
