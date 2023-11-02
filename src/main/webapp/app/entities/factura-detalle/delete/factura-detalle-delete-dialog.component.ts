import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IFacturaDetalle } from '../factura-detalle.model';
import { FacturaDetalleService } from '../service/factura-detalle.service';

@Component({
  standalone: true,
  templateUrl: './factura-detalle-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class FacturaDetalleDeleteDialogComponent {
  facturaDetalle?: IFacturaDetalle;

  constructor(
    protected facturaDetalleService: FacturaDetalleService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.facturaDetalleService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
