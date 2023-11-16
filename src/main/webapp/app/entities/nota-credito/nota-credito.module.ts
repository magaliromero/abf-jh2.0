import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NotaCreditoComponent } from './list/nota-credito.component';
import { NotaCreditoDetailComponent } from './detail/nota-credito-detail.component';
import { NotaCreditoUpdateComponent } from './update/nota-credito-update.component';
import { NotaCreditoDeleteDialogComponent } from './delete/nota-credito-delete-dialog.component';
import { NotaCreditoRoutingModule } from './route/nota-credito-routing.module';

@NgModule({
  imports: [SharedModule, NotaCreditoRoutingModule],
  declarations: [NotaCreditoComponent, NotaCreditoDetailComponent, NotaCreditoUpdateComponent, NotaCreditoDeleteDialogComponent],
})
export class NotaCreditoModule {}
