import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITorneos } from '../torneos.model';

@Component({
  selector: 'jhi-torneos-detail',
  templateUrl: './torneos-detail.component.html',
})
export class TorneosDetailComponent implements OnInit {
  torneos: ITorneos | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ torneos }) => {
      this.torneos = torneos;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
