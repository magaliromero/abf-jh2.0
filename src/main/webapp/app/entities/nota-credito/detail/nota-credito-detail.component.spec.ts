import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NotaCreditoDetailComponent } from './nota-credito-detail.component';

describe('NotaCredito Management Detail Component', () => {
  let comp: NotaCreditoDetailComponent;
  let fixture: ComponentFixture<NotaCreditoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NotaCreditoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ notaCredito: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NotaCreditoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NotaCreditoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load notaCredito on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.notaCredito).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
