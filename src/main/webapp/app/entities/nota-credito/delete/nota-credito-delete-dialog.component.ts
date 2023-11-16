import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INotaCredito } from '../nota-credito.model';
import { NotaCreditoService } from '../service/nota-credito.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './nota-credito-delete-dialog.component.html',
})
export class NotaCreditoDeleteDialogComponent {
  notaCredito?: INotaCredito;

  constructor(protected notaCreditoService: NotaCreditoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.notaCreditoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
