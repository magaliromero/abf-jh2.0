import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { EvaluacionesDetalleDetailComponent } from './evaluaciones-detalle-detail.component';

describe('EvaluacionesDetalle Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EvaluacionesDetalleDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: EvaluacionesDetalleDetailComponent,
              resolve: { evaluacionesDetalle: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EvaluacionesDetalleDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load evaluacionesDetalle on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EvaluacionesDetalleDetailComponent);

      // THEN
      expect(instance.evaluacionesDetalle).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
