import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ITiposDocumentos } from '../tipos-documentos.model';

@Component({
  standalone: true,
  selector: 'jhi-tipos-documentos-detail',
  templateUrl: './tipos-documentos-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class TiposDocumentosDetailComponent {
  @Input() tiposDocumentos: ITiposDocumentos | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
