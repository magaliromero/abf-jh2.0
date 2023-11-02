import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { EvaluacionesDetailComponent } from './evaluaciones-detail.component';

describe('Evaluaciones Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EvaluacionesDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: EvaluacionesDetailComponent,
              resolve: { evaluaciones: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EvaluacionesDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load evaluaciones on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EvaluacionesDetailComponent);

      // THEN
      expect(instance.evaluaciones).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
