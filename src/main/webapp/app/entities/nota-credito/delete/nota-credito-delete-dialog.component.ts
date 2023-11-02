import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { INotaCredito } from '../nota-credito.model';
import { NotaCreditoService } from '../service/nota-credito.service';

@Component({
  standalone: true,
  templateUrl: './nota-credito-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class NotaCreditoDeleteDialogComponent {
  notaCredito?: INotaCredito;

  constructor(
    protected notaCreditoService: NotaCreditoService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.notaCreditoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
