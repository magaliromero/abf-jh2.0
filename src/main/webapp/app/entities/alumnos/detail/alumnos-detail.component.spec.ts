import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { AlumnosDetailComponent } from './alumnos-detail.component';

describe('Alumnos Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AlumnosDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: AlumnosDetailComponent,
              resolve: { alumnos: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AlumnosDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load alumnos on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AlumnosDetailComponent);

      // THEN
      expect(instance.alumnos).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
