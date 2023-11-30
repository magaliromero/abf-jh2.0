import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FacturasComponent } from './list/facturas.component';
import { FacturasDetailComponent } from './detail/facturas-detail.component';
import { FacturasUpdateComponent } from './update/facturas-update.component';
import { FacturasDeleteDialogComponent } from './delete/facturas-delete-dialog.component';
import { FacturasRoutingModule } from './route/facturas-routing.module';
import { BalanceComponent } from './balance/balance.component';

@NgModule({
  imports: [SharedModule, FacturasRoutingModule],
  declarations: [FacturasComponent, FacturasDetailComponent, FacturasUpdateComponent, FacturasDeleteDialogComponent, BalanceComponent],
})
export class FacturasModule {}
