import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PagosDetailComponent } from './pagos-detail.component';

describe('Pagos Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PagosDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: PagosDetailComponent,
              resolve: { pagos: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PagosDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load pagos on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PagosDetailComponent);

      // THEN
      expect(instance.pagos).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
