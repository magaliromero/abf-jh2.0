import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { MaterialesDetailComponent } from './materiales-detail.component';

describe('Materiales Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MaterialesDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: MaterialesDetailComponent,
              resolve: { materiales: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(MaterialesDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load materiales on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', MaterialesDetailComponent);

      // THEN
      expect(instance.materiales).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
