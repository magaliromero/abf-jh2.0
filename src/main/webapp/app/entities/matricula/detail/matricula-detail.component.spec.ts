import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { MatriculaDetailComponent } from './matricula-detail.component';

describe('Matricula Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MatriculaDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: MatriculaDetailComponent,
              resolve: { matricula: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(MatriculaDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load matricula on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', MatriculaDetailComponent);

      // THEN
      expect(instance.matricula).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
