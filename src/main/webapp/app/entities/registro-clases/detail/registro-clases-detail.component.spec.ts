import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { RegistroClasesDetailComponent } from './registro-clases-detail.component';

describe('RegistroClases Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RegistroClasesDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: RegistroClasesDetailComponent,
              resolve: { registroClases: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(RegistroClasesDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load registroClases on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', RegistroClasesDetailComponent);

      // THEN
      expect(instance.registroClases).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
