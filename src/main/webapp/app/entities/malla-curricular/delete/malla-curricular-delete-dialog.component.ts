import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMallaCurricular } from '../malla-curricular.model';
import { MallaCurricularService } from '../service/malla-curricular.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './malla-curricular-delete-dialog.component.html',
})
export class MallaCurricularDeleteDialogComponent {
  mallaCurricular?: IMallaCurricular;

  constructor(protected mallaCurricularService: MallaCurricularService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mallaCurricularService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
