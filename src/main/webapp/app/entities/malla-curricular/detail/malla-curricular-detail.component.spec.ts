import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MallaCurricularDetailComponent } from './malla-curricular-detail.component';

describe('MallaCurricular Management Detail Component', () => {
  let comp: MallaCurricularDetailComponent;
  let fixture: ComponentFixture<MallaCurricularDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MallaCurricularDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ mallaCurricular: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MallaCurricularDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MallaCurricularDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load mallaCurricular on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.mallaCurricular).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
