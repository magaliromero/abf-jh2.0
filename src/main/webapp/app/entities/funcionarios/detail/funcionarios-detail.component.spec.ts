import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { FuncionariosDetailComponent } from './funcionarios-detail.component';

describe('Funcionarios Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FuncionariosDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: FuncionariosDetailComponent,
              resolve: { funcionarios: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(FuncionariosDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load funcionarios on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', FuncionariosDetailComponent);

      // THEN
      expect(instance.funcionarios).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
