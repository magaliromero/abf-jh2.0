import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PrestamosDetailComponent } from './prestamos-detail.component';

describe('Prestamos Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PrestamosDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: PrestamosDetailComponent,
              resolve: { prestamos: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PrestamosDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load prestamos on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PrestamosDetailComponent);

      // THEN
      expect(instance.prestamos).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
