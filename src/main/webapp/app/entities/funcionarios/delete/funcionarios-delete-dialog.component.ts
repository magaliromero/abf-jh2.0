import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IFuncionarios } from '../funcionarios.model';
import { FuncionariosService } from '../service/funcionarios.service';

@Component({
  standalone: true,
  templateUrl: './funcionarios-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class FuncionariosDeleteDialogComponent {
  funcionarios?: IFuncionarios;

  constructor(
    protected funcionariosService: FuncionariosService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.funcionariosService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
