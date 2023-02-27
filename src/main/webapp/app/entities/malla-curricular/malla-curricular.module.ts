import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MallaCurricularComponent } from './list/malla-curricular.component';
import { MallaCurricularDetailComponent } from './detail/malla-curricular-detail.component';
import { MallaCurricularUpdateComponent } from './update/malla-curricular-update.component';
import { MallaCurricularDeleteDialogComponent } from './delete/malla-curricular-delete-dialog.component';
import { MallaCurricularRoutingModule } from './route/malla-curricular-routing.module';

@NgModule({
  imports: [SharedModule, MallaCurricularRoutingModule],
  declarations: [
    MallaCurricularComponent,
    MallaCurricularDetailComponent,
    MallaCurricularUpdateComponent,
    MallaCurricularDeleteDialogComponent,
  ],
})
export class MallaCurricularModule {}
