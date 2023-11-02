import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CursosDetailComponent } from './cursos-detail.component';

describe('Cursos Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CursosDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CursosDetailComponent,
              resolve: { cursos: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CursosDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load cursos on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CursosDetailComponent);

      // THEN
      expect(instance.cursos).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
