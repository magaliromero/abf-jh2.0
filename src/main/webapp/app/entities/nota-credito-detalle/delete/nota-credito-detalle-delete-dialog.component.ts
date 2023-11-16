import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INotaCreditoDetalle } from '../nota-credito-detalle.model';
import { NotaCreditoDetalleService } from '../service/nota-credito-detalle.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './nota-credito-detalle-delete-dialog.component.html',
})
export class NotaCreditoDetalleDeleteDialogComponent {
  notaCreditoDetalle?: INotaCreditoDetalle;

  constructor(protected notaCreditoDetalleService: NotaCreditoDetalleService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.notaCreditoDetalleService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
