import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NotaCreditoDetalleComponent } from './list/nota-credito-detalle.component';
import { NotaCreditoDetalleDetailComponent } from './detail/nota-credito-detalle-detail.component';
import { NotaCreditoDetalleUpdateComponent } from './update/nota-credito-detalle-update.component';
import { NotaCreditoDetalleDeleteDialogComponent } from './delete/nota-credito-detalle-delete-dialog.component';
import { NotaCreditoDetalleRoutingModule } from './route/nota-credito-detalle-routing.module';

@NgModule({
  imports: [SharedModule, NotaCreditoDetalleRoutingModule],
  declarations: [
    NotaCreditoDetalleComponent,
    NotaCreditoDetalleDetailComponent,
    NotaCreditoDetalleUpdateComponent,
    NotaCreditoDetalleDeleteDialogComponent,
  ],
})
export class NotaCreditoDetalleModule {}
