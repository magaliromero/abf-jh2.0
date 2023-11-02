import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { TemasDetailComponent } from './temas-detail.component';

describe('Temas Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TemasDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: TemasDetailComponent,
              resolve: { temas: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TemasDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load temas on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TemasDetailComponent);

      // THEN
      expect(instance.temas).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
