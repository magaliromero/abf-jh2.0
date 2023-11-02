import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { INotaCreditoDetalle } from '../nota-credito-detalle.model';

@Component({
  standalone: true,
  selector: 'jhi-nota-credito-detalle-detail',
  templateUrl: './nota-credito-detalle-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class NotaCreditoDetalleDetailComponent {
  @Input() notaCreditoDetalle: INotaCreditoDetalle | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
