import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { TiposDocumentosDetailComponent } from './tipos-documentos-detail.component';

describe('TiposDocumentos Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TiposDocumentosDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: TiposDocumentosDetailComponent,
              resolve: { tiposDocumentos: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TiposDocumentosDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load tiposDocumentos on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TiposDocumentosDetailComponent);

      // THEN
      expect(instance.tiposDocumentos).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
