import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FichaPartidasTorneosComponent } from './list/ficha-partidas-torneos.component';
import { FichaPartidasTorneosDetailComponent } from './detail/ficha-partidas-torneos-detail.component';
import { FichaPartidasTorneosUpdateComponent } from './update/ficha-partidas-torneos-update.component';
import { FichaPartidasTorneosDeleteDialogComponent } from './delete/ficha-partidas-torneos-delete-dialog.component';
import { FichaPartidasTorneosRoutingModule } from './route/ficha-partidas-torneos-routing.module';

@NgModule({
  imports: [SharedModule, FichaPartidasTorneosRoutingModule],
  declarations: [
    FichaPartidasTorneosComponent,
    FichaPartidasTorneosDetailComponent,
    FichaPartidasTorneosUpdateComponent,
    FichaPartidasTorneosDeleteDialogComponent,
  ],
})
export class FichaPartidasTorneosModule {}
