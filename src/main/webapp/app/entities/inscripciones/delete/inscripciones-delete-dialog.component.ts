import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IInscripciones } from '../inscripciones.model';
import { InscripcionesService } from '../service/inscripciones.service';

@Component({
  standalone: true,
  templateUrl: './inscripciones-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class InscripcionesDeleteDialogComponent {
  inscripciones?: IInscripciones;

  constructor(
    protected inscripcionesService: InscripcionesService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.inscripcionesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
