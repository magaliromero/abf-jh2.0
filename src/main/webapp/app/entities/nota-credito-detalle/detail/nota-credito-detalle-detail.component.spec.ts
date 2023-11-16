import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NotaCreditoDetalleDetailComponent } from './nota-credito-detalle-detail.component';

describe('NotaCreditoDetalle Management Detail Component', () => {
  let comp: NotaCreditoDetalleDetailComponent;
  let fixture: ComponentFixture<NotaCreditoDetalleDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NotaCreditoDetalleDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ notaCreditoDetalle: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NotaCreditoDetalleDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NotaCreditoDetalleDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load notaCreditoDetalle on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.notaCreditoDetalle).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
