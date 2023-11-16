import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { RegistroStockMaterialesFormService, RegistroStockMaterialesFormGroup } from './registro-stock-materiales-form.service';
import { IRegistroStockMateriales } from '../registro-stock-materiales.model';
import { RegistroStockMaterialesService } from '../service/registro-stock-materiales.service';
import { IMateriales } from 'app/entities/materiales/materiales.model';
import { MaterialesService } from 'app/entities/materiales/service/materiales.service';

@Component({
  selector: 'jhi-registro-stock-materiales-update',
  templateUrl: './registro-stock-materiales-update.component.html',
})
export class RegistroStockMaterialesUpdateComponent implements OnInit {
  isSaving = false;
  registroStockMateriales: IRegistroStockMateriales | null = null;

  materialesSharedCollection: IMateriales[] = [];

  editForm: RegistroStockMaterialesFormGroup = this.registroStockMaterialesFormService.createRegistroStockMaterialesFormGroup();

  constructor(
    protected registroStockMaterialesService: RegistroStockMaterialesService,
    protected registroStockMaterialesFormService: RegistroStockMaterialesFormService,
    protected materialesService: MaterialesService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareMateriales = (o1: IMateriales | null, o2: IMateriales | null): boolean => this.materialesService.compareMateriales(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ registroStockMateriales }) => {
      this.registroStockMateriales = registroStockMateriales;
      if (registroStockMateriales) {
        this.updateForm(registroStockMateriales);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const registroStockMateriales = this.registroStockMaterialesFormService.getRegistroStockMateriales(this.editForm);
    if (registroStockMateriales.id !== null) {
      this.subscribeToSaveResponse(this.registroStockMaterialesService.update(registroStockMateriales));
    } else {
      this.subscribeToSaveResponse(this.registroStockMaterialesService.create(registroStockMateriales));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRegistroStockMateriales>>): void {
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

  protected updateForm(registroStockMateriales: IRegistroStockMateriales): void {
    this.registroStockMateriales = registroStockMateriales;
    this.registroStockMaterialesFormService.resetForm(this.editForm, registroStockMateriales);

    this.materialesSharedCollection = this.materialesService.addMaterialesToCollectionIfMissing<IMateriales>(
      this.materialesSharedCollection,
      registroStockMateriales.materiales
    );
  }

  protected loadRelationshipsOptions(): void {
    this.materialesService
      .query()
      .pipe(map((res: HttpResponse<IMateriales[]>) => res.body ?? []))
      .pipe(
        map((materiales: IMateriales[]) =>
          this.materialesService.addMaterialesToCollectionIfMissing<IMateriales>(materiales, this.registroStockMateriales?.materiales)
        )
      )
      .subscribe((materiales: IMateriales[]) => (this.materialesSharedCollection = materiales));
  }
}
