import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IEvaluacionesDetalle } from '../evaluaciones-detalle.model';

@Component({
  standalone: true,
  selector: 'jhi-evaluaciones-detalle-detail',
  templateUrl: './evaluaciones-detalle-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class EvaluacionesDetalleDetailComponent {
  @Input() evaluacionesDetalle: IEvaluacionesDetalle | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
