import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { InscripcionesDetailComponent } from './inscripciones-detail.component';

describe('Inscripciones Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InscripcionesDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: InscripcionesDetailComponent,
              resolve: { inscripciones: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(InscripcionesDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load inscripciones on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', InscripcionesDetailComponent);

      // THEN
      expect(instance.inscripciones).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
