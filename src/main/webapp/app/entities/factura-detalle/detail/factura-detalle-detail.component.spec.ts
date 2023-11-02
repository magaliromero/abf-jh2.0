import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { FacturaDetalleDetailComponent } from './factura-detalle-detail.component';

describe('FacturaDetalle Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FacturaDetalleDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: FacturaDetalleDetailComponent,
              resolve: { facturaDetalle: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(FacturaDetalleDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load facturaDetalle on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', FacturaDetalleDetailComponent);

      // THEN
      expect(instance.facturaDetalle).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
