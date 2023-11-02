import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEvaluacionesDetalle } from '../evaluaciones-detalle.model';
import { EvaluacionesDetalleService } from '../service/evaluaciones-detalle.service';

@Component({
  standalone: true,
  templateUrl: './evaluaciones-detalle-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EvaluacionesDetalleDeleteDialogComponent {
  evaluacionesDetalle?: IEvaluacionesDetalle;

  constructor(
    protected evaluacionesDetalleService: EvaluacionesDetalleService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.evaluacionesDetalleService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
