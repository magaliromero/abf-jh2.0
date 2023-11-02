import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEvaluaciones } from '../evaluaciones.model';
import { EvaluacionesService } from '../service/evaluaciones.service';

@Component({
  standalone: true,
  templateUrl: './evaluaciones-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EvaluacionesDeleteDialogComponent {
  evaluaciones?: IEvaluaciones;

  constructor(
    protected evaluacionesService: EvaluacionesService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.evaluacionesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
