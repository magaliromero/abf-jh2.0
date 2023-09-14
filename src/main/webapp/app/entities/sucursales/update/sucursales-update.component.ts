import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { SucursalesFormService, SucursalesFormGroup } from './sucursales-form.service';
import { ISucursales } from '../sucursales.model';
import { SucursalesService } from '../service/sucursales.service';
import { ITimbrados } from 'app/entities/timbrados/timbrados.model';
import { TimbradosService } from 'app/entities/timbrados/service/timbrados.service';

@Component({
  selector: 'jhi-sucursales-update',
  templateUrl: './sucursales-update.component.html',
})
export class SucursalesUpdateComponent implements OnInit {
  isSaving = false;
  sucursales: ISucursales | null = null;

  timbradosSharedCollection: ITimbrados[] = [];

  editForm: SucursalesFormGroup = this.sucursalesFormService.createSucursalesFormGroup();

  constructor(
    protected sucursalesService: SucursalesService,
    protected sucursalesFormService: SucursalesFormService,
    protected timbradosService: TimbradosService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareTimbrados = (o1: ITimbrados | null, o2: ITimbrados | null): boolean => this.timbradosService.compareTimbrados(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sucursales }) => {
      this.sucursales = sucursales;
      if (sucursales) {
        this.updateForm(sucursales);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sucursales = this.sucursalesFormService.getSucursales(this.editForm);
    if (sucursales.id !== null) {
      this.subscribeToSaveResponse(this.sucursalesService.update(sucursales));
    } else {
      this.subscribeToSaveResponse(this.sucursalesService.create(sucursales));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISucursales>>): void {
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

  protected updateForm(sucursales: ISucursales): void {
    this.sucursales = sucursales;
    this.sucursalesFormService.resetForm(this.editForm, sucursales);

    this.timbradosSharedCollection = this.timbradosService.addTimbradosToCollectionIfMissing<ITimbrados>(
      this.timbradosSharedCollection,
      sucursales.timbrados
    );
  }

  protected loadRelationshipsOptions(): void {
    this.timbradosService
      .query()
      .pipe(map((res: HttpResponse<ITimbrados[]>) => res.body ?? []))
      .pipe(
        map((timbrados: ITimbrados[]) =>
          this.timbradosService.addTimbradosToCollectionIfMissing<ITimbrados>(timbrados, this.sucursales?.timbrados)
        )
      )
      .subscribe((timbrados: ITimbrados[]) => (this.timbradosSharedCollection = timbrados));
  }
}
