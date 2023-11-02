import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { SucursalesDetailComponent } from './sucursales-detail.component';

describe('Sucursales Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SucursalesDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: SucursalesDetailComponent,
              resolve: { sucursales: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SucursalesDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load sucursales on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SucursalesDetailComponent);

      // THEN
      expect(instance.sucursales).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
