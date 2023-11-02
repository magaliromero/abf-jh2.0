import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPuntoDeExpedicion } from '../punto-de-expedicion.model';
import { PuntoDeExpedicionService } from '../service/punto-de-expedicion.service';

@Component({
  standalone: true,
  templateUrl: './punto-de-expedicion-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PuntoDeExpedicionDeleteDialogComponent {
  puntoDeExpedicion?: IPuntoDeExpedicion;

  constructor(
    protected puntoDeExpedicionService: PuntoDeExpedicionService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.puntoDeExpedicionService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
