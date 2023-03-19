import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TorneosComponent } from './list/torneos.component';
import { TorneosDetailComponent } from './detail/torneos-detail.component';
import { TorneosUpdateComponent } from './update/torneos-update.component';
import { TorneosDeleteDialogComponent } from './delete/torneos-delete-dialog.component';
import { TorneosRoutingModule } from './route/torneos-routing.module';

@NgModule({
  imports: [SharedModule, TorneosRoutingModule],
  declarations: [TorneosComponent, TorneosDetailComponent, TorneosUpdateComponent, TorneosDeleteDialogComponent],
})
export class TorneosModule {}
