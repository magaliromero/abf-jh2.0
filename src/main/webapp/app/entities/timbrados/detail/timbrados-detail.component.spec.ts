import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { TimbradosDetailComponent } from './timbrados-detail.component';

describe('Timbrados Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TimbradosDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: TimbradosDetailComponent,
              resolve: { timbrados: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TimbradosDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load timbrados on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TimbradosDetailComponent);

      // THEN
      expect(instance.timbrados).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
