import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { NotaCreditoDetalleDetailComponent } from './nota-credito-detalle-detail.component';

describe('NotaCreditoDetalle Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NotaCreditoDetalleDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: NotaCreditoDetalleDetailComponent,
              resolve: { notaCreditoDetalle: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(NotaCreditoDetalleDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load notaCreditoDetalle on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', NotaCreditoDetalleDetailComponent);

      // THEN
      expect(instance.notaCreditoDetalle).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
