import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITorneos } from '../torneos.model';
import { TorneosService } from '../service/torneos.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './torneos-delete-dialog.component.html',
})
export class TorneosDeleteDialogComponent {
  torneos?: ITorneos;

  constructor(protected torneosService: TorneosService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.torneosService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
