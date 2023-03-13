import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TorneosDetailComponent } from './torneos-detail.component';

describe('Torneos Management Detail Component', () => {
  let comp: TorneosDetailComponent;
  let fixture: ComponentFixture<TorneosDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TorneosDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ torneos: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TorneosDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TorneosDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load torneos on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.torneos).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
