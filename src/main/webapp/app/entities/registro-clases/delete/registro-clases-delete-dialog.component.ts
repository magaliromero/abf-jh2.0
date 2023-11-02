import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IRegistroClases } from '../registro-clases.model';
import { RegistroClasesService } from '../service/registro-clases.service';

@Component({
  standalone: true,
  templateUrl: './registro-clases-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class RegistroClasesDeleteDialogComponent {
  registroClases?: IRegistroClases;

  constructor(
    protected registroClasesService: RegistroClasesService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.registroClasesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
