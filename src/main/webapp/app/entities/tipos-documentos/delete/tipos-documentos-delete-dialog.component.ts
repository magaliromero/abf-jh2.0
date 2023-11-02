import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITiposDocumentos } from '../tipos-documentos.model';
import { TiposDocumentosService } from '../service/tipos-documentos.service';

@Component({
  standalone: true,
  templateUrl: './tipos-documentos-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TiposDocumentosDeleteDialogComponent {
  tiposDocumentos?: ITiposDocumentos;

  constructor(
    protected tiposDocumentosService: TiposDocumentosService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tiposDocumentosService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
