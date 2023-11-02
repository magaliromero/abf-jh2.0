import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAlumnos } from '../alumnos.model';
import { AlumnosService } from '../service/alumnos.service';

@Component({
  standalone: true,
  templateUrl: './alumnos-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AlumnosDeleteDialogComponent {
  alumnos?: IAlumnos;

  constructor(
    protected alumnosService: AlumnosService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.alumnosService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
