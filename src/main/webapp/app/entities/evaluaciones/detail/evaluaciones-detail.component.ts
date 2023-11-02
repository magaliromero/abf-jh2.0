import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IEvaluaciones } from '../evaluaciones.model';

@Component({
  standalone: true,
  selector: 'jhi-evaluaciones-detail',
  templateUrl: './evaluaciones-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class EvaluacionesDetailComponent {
  @Input() evaluaciones: IEvaluaciones | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
