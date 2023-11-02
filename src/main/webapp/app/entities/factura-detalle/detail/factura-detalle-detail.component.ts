import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IFacturaDetalle } from '../factura-detalle.model';

@Component({
  standalone: true,
  selector: 'jhi-factura-detalle-detail',
  templateUrl: './factura-detalle-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class FacturaDetalleDetailComponent {
  @Input() facturaDetalle: IFacturaDetalle | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
