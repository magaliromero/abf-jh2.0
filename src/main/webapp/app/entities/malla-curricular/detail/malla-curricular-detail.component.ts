import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMallaCurricular } from '../malla-curricular.model';

@Component({
  selector: 'jhi-malla-curricular-detail',
  templateUrl: './malla-curricular-detail.component.html',
})
export class MallaCurricularDetailComponent implements OnInit {
  mallaCurricular: IMallaCurricular | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mallaCurricular }) => {
      this.mallaCurricular = mallaCurricular;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
