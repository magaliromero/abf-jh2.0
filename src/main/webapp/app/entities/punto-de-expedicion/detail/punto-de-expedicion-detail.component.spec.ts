import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PuntoDeExpedicionDetailComponent } from './punto-de-expedicion-detail.component';

describe('PuntoDeExpedicion Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PuntoDeExpedicionDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: PuntoDeExpedicionDetailComponent,
              resolve: { puntoDeExpedicion: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PuntoDeExpedicionDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load puntoDeExpedicion on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PuntoDeExpedicionDetailComponent);

      // THEN
      expect(instance.puntoDeExpedicion).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
