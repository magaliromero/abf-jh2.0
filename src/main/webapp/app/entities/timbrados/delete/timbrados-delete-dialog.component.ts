import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITimbrados } from '../timbrados.model';
import { TimbradosService } from '../service/timbrados.service';

@Component({
  standalone: true,
  templateUrl: './timbrados-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TimbradosDeleteDialogComponent {
  timbrados?: ITimbrados;

  constructor(
    protected timbradosService: TimbradosService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.timbradosService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
