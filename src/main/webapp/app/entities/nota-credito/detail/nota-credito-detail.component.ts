import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INotaCredito } from '../nota-credito.model';

@Component({
  selector: 'jhi-nota-credito-detail',
  templateUrl: './nota-credito-detail.component.html',
})
export class NotaCreditoDetailComponent implements OnInit {
  notaCredito: INotaCredito | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notaCredito }) => {
      this.notaCredito = notaCredito;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
