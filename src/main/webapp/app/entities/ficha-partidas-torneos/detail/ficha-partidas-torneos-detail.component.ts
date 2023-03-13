import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFichaPartidasTorneos } from '../ficha-partidas-torneos.model';

@Component({
  selector: 'jhi-ficha-partidas-torneos-detail',
  templateUrl: './ficha-partidas-torneos-detail.component.html',
})
export class FichaPartidasTorneosDetailComponent implements OnInit {
  fichaPartidasTorneos: IFichaPartidasTorneos | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fichaPartidasTorneos }) => {
      this.fichaPartidasTorneos = fichaPartidasTorneos;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
