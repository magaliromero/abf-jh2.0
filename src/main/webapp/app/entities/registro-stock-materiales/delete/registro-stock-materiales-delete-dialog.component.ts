import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRegistroStockMateriales } from '../registro-stock-materiales.model';
import { RegistroStockMaterialesService } from '../service/registro-stock-materiales.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './registro-stock-materiales-delete-dialog.component.html',
})
export class RegistroStockMaterialesDeleteDialogComponent {
  registroStockMateriales?: IRegistroStockMateriales;

  constructor(protected registroStockMaterialesService: RegistroStockMaterialesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.registroStockMaterialesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
