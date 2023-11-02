import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IPuntoDeExpedicion } from '../punto-de-expedicion.model';

@Component({
  standalone: true,
  selector: 'jhi-punto-de-expedicion-detail',
  templateUrl: './punto-de-expedicion-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class PuntoDeExpedicionDetailComponent {
  @Input() puntoDeExpedicion: IPuntoDeExpedicion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
