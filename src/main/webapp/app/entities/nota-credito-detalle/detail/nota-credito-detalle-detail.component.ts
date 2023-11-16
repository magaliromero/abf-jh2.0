import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INotaCreditoDetalle } from '../nota-credito-detalle.model';

@Component({
  selector: 'jhi-nota-credito-detalle-detail',
  templateUrl: './nota-credito-detalle-detail.component.html',
})
export class NotaCreditoDetalleDetailComponent implements OnInit {
  notaCreditoDetalle: INotaCreditoDetalle | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notaCreditoDetalle }) => {
      this.notaCreditoDetalle = notaCreditoDetalle;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
