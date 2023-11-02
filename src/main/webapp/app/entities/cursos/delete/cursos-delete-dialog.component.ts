import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICursos } from '../cursos.model';
import { CursosService } from '../service/cursos.service';

@Component({
  standalone: true,
  templateUrl: './cursos-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CursosDeleteDialogComponent {
  cursos?: ICursos;

  constructor(
    protected cursosService: CursosService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cursosService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
