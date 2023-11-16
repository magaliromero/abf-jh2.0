import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { NotaCreditoFormService, NotaCreditoFormGroup } from './nota-credito-form.service';
import { INotaCredito } from '../nota-credito.model';
import { NotaCreditoService } from '../service/nota-credito.service';
import { IFacturas } from 'app/entities/facturas/facturas.model';
import { FacturasService } from 'app/entities/facturas/service/facturas.service';
import { Motivo } from 'app/entities/enumerations/motivo.model';
import { EstadosFacturas } from 'app/entities/enumerations/estados-facturas.model';

@Component({
  selector: 'jhi-nota-credito-update',
  templateUrl: './nota-credito-update.component.html',
})
export class NotaCreditoUpdateComponent implements OnInit {
  isSaving = false;
  notaCredito: INotaCredito | null = null;
  motivoValues = Object.keys(Motivo);
  estadosFacturasValues = Object.keys(EstadosFacturas);

  facturasSharedCollection: IFacturas[] = [];

  editForm: NotaCreditoFormGroup = this.notaCreditoFormService.createNotaCreditoFormGroup();

  constructor(
    protected notaCreditoService: NotaCreditoService,
    protected notaCreditoFormService: NotaCreditoFormService,
    protected facturasService: FacturasService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareFacturas = (o1: IFacturas | null, o2: IFacturas | null): boolean => this.facturasService.compareFacturas(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notaCredito }) => {
      this.notaCredito = notaCredito;
      if (notaCredito) {
        this.updateForm(notaCredito);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const notaCredito = this.notaCreditoFormService.getNotaCredito(this.editForm);
    if (notaCredito.id !== null) {
      this.subscribeToSaveResponse(this.notaCreditoService.update(notaCredito));
    } else {
      this.subscribeToSaveResponse(this.notaCreditoService.create(notaCredito));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INotaCredito>>): void {
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

  protected updateForm(notaCredito: INotaCredito): void {
    this.notaCredito = notaCredito;
    this.notaCreditoFormService.resetForm(this.editForm, notaCredito);

    this.facturasSharedCollection = this.facturasService.addFacturasToCollectionIfMissing<IFacturas>(
      this.facturasSharedCollection,
      notaCredito.facturas
    );
  }

  protected loadRelationshipsOptions(): void {
    this.facturasService
      .query()
      .pipe(map((res: HttpResponse<IFacturas[]>) => res.body ?? []))
      .pipe(
        map((facturas: IFacturas[]) =>
          this.facturasService.addFacturasToCollectionIfMissing<IFacturas>(facturas, this.notaCredito?.facturas)
        )
      )
      .subscribe((facturas: IFacturas[]) => (this.facturasSharedCollection = facturas));
  }
}
