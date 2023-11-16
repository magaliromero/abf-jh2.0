import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RegistroStockMaterialesComponent } from './list/registro-stock-materiales.component';
import { RegistroStockMaterialesDetailComponent } from './detail/registro-stock-materiales-detail.component';
import { RegistroStockMaterialesUpdateComponent } from './update/registro-stock-materiales-update.component';
import { RegistroStockMaterialesDeleteDialogComponent } from './delete/registro-stock-materiales-delete-dialog.component';
import { RegistroStockMaterialesRoutingModule } from './route/registro-stock-materiales-routing.module';

@NgModule({
  imports: [SharedModule, RegistroStockMaterialesRoutingModule],
  declarations: [
    RegistroStockMaterialesComponent,
    RegistroStockMaterialesDetailComponent,
    RegistroStockMaterialesUpdateComponent,
    RegistroStockMaterialesDeleteDialogComponent,
  ],
})
export class RegistroStockMaterialesModule {}
