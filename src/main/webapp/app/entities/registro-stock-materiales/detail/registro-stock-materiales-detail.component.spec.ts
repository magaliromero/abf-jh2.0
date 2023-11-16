import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RegistroStockMaterialesDetailComponent } from './registro-stock-materiales-detail.component';

describe('RegistroStockMateriales Management Detail Component', () => {
  let comp: RegistroStockMaterialesDetailComponent;
  let fixture: ComponentFixture<RegistroStockMaterialesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RegistroStockMaterialesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ registroStockMateriales: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RegistroStockMaterialesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RegistroStockMaterialesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load registroStockMateriales on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.registroStockMateriales).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
