import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { NotaCreditoDetailComponent } from './nota-credito-detail.component';

describe('NotaCredito Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NotaCreditoDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: NotaCreditoDetailComponent,
              resolve: { notaCredito: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(NotaCreditoDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load notaCredito on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', NotaCreditoDetailComponent);

      // THEN
      expect(instance.notaCredito).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
