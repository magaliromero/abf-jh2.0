import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFichaPartidasTorneos } from '../ficha-partidas-torneos.model';
import { FichaPartidasTorneosService } from '../service/ficha-partidas-torneos.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './ficha-partidas-torneos-delete-dialog.component.html',
})
export class FichaPartidasTorneosDeleteDialogComponent {
  fichaPartidasTorneos?: IFichaPartidasTorneos;

  constructor(protected fichaPartidasTorneosService: FichaPartidasTorneosService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fichaPartidasTorneosService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
