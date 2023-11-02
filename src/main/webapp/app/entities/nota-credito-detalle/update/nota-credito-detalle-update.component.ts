import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { INotaCredito } from 'app/entities/nota-credito/nota-credito.model';
import { NotaCreditoService } from 'app/entities/nota-credito/service/nota-credito.service';
import { IProductos } from 'app/entities/productos/productos.model';
import { ProductosService } from 'app/entities/productos/service/productos.service';
import { NotaCreditoDetalleService } from '../service/nota-credito-detalle.service';
import { INotaCreditoDetalle } from '../nota-credito-detalle.model';
import { NotaCreditoDetalleFormService, NotaCreditoDetalleFormGroup } from './nota-credito-detalle-form.service';

@Component({
  standalone: true,
  selector: 'jhi-nota-credito-detalle-update',
  templateUrl: './nota-credito-detalle-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class NotaCreditoDetalleUpdateComponent implements OnInit {
  isSaving = false;
  notaCreditoDetalle: INotaCreditoDetalle | null = null;

  notaCreditosSharedCollection: INotaCredito[] = [];
  productosSharedCollection: IProductos[] = [];

  editForm: NotaCreditoDetalleFormGroup = this.notaCreditoDetalleFormService.createNotaCreditoDetalleFormGroup();

  constructor(
    protected notaCreditoDetalleService: NotaCreditoDetalleService,
    protected notaCreditoDetalleFormService: NotaCreditoDetalleFormService,
    protected notaCreditoService: NotaCreditoService,
    protected productosService: ProductosService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareNotaCredito = (o1: INotaCredito | null, o2: INotaCredito | null): boolean => this.notaCreditoService.compareNotaCredito(o1, o2);

  compareProductos = (o1: IProductos | null, o2: IProductos | null): boolean => this.productosService.compareProductos(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notaCreditoDetalle }) => {
      this.notaCreditoDetalle = notaCreditoDetalle;
      if (notaCreditoDetalle) {
        this.updateForm(notaCreditoDetalle);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const notaCreditoDetalle = this.notaCreditoDetalleFormService.getNotaCreditoDetalle(this.editForm);
    if (notaCreditoDetalle.id !== null) {
      this.subscribeToSaveResponse(this.notaCreditoDetalleService.update(notaCreditoDetalle));
    } else {
      this.subscribeToSaveResponse(this.notaCreditoDetalleService.create(notaCreditoDetalle));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INotaCreditoDetalle>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(notaCreditoDetalle: INotaCreditoDetalle): void {
    this.notaCreditoDetalle = notaCreditoDetalle;
    this.notaCreditoDetalleFormService.resetForm(this.editForm, notaCreditoDetalle);

    this.notaCreditosSharedCollection = this.notaCreditoService.addNotaCreditoToCollectionIfMissing<INotaCredito>(
      this.notaCreditosSharedCollection,
      notaCreditoDetalle.notaCredito,
    );
    this.productosSharedCollection = this.productosService.addProductosToCollectionIfMissing<IProductos>(
      this.productosSharedCollection,
      notaCreditoDetalle.producto,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.notaCreditoService
      .query()
      .pipe(map((res: HttpResponse<INotaCredito[]>) => res.body ?? []))
      .pipe(
        map((notaCreditos: INotaCredito[]) =>
          this.notaCreditoService.addNotaCreditoToCollectionIfMissing<INotaCredito>(notaCreditos, this.notaCreditoDetalle?.notaCredito),
        ),
      )
      .subscribe((notaCreditos: INotaCredito[]) => (this.notaCreditosSharedCollection = notaCreditos));

    this.productosService
      .query()
      .pipe(map((res: HttpResponse<IProductos[]>) => res.body ?? []))
      .pipe(
        map((productos: IProductos[]) =>
          this.productosService.addProductosToCollectionIfMissing<IProductos>(productos, this.notaCreditoDetalle?.producto),
        ),
      )
      .subscribe((productos: IProductos[]) => (this.productosSharedCollection = productos));
  }
}
