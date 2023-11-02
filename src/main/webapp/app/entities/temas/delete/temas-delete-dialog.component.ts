import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITemas } from '../temas.model';
import { TemasService } from '../service/temas.service';

@Component({
  standalone: true,
  templateUrl: './temas-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TemasDeleteDialogComponent {
  temas?: ITemas;

  constructor(
    protected temasService: TemasService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.temasService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
