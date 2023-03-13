import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FichaPartidasTorneosDetailComponent } from './ficha-partidas-torneos-detail.component';

describe('FichaPartidasTorneos Management Detail Component', () => {
  let comp: FichaPartidasTorneosDetailComponent;
  let fixture: ComponentFixture<FichaPartidasTorneosDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FichaPartidasTorneosDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ fichaPartidasTorneos: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(FichaPartidasTorneosDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FichaPartidasTorneosDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load fichaPartidasTorneos on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.fichaPartidasTorneos).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
