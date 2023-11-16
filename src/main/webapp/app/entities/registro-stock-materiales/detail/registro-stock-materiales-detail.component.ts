import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRegistroStockMateriales } from '../registro-stock-materiales.model';

@Component({
  selector: 'jhi-registro-stock-materiales-detail',
  templateUrl: './registro-stock-materiales-detail.component.html',
})
export class RegistroStockMaterialesDetailComponent implements OnInit {
  registroStockMateriales: IRegistroStockMateriales | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ registroStockMateriales }) => {
      this.registroStockMateriales = registroStockMateriales;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
