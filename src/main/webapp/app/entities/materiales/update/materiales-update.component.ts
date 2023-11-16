import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { MaterialesFormService, MaterialesFormGroup } from './materiales-form.service';
import { IMateriales } from '../materiales.model';
import { MaterialesService } from '../service/materiales.service';
import { AlertService } from 'app/core/util/alert.service';

@Component({
  selector: 'jhi-materiales-update',
  templateUrl: './materiales-update.component.html',
})
export class MaterialesUpdateComponent implements OnInit {
  isSaving = false;
  comentario = '';
  diferencia = 0;
  cantInicial = 0;

  materiales: IMateriales | null = null;

  editForm: MaterialesFormGroup = this.materialesFormService.createMaterialesFormGroup();

  constructor(
    protected materialesService: MaterialesService,
    protected materialesFormService: MaterialesFormService,
    protected activatedRoute: ActivatedRoute,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ materiales }) => {
      this.materiales = materiales;
      if (materiales) {
        this.updateForm(materiales);
        this.editForm.controls.cantidadEnPrestamo.disable();
        this.cantInicial = this.editForm.controls.cantidad.value ?? 0;
      }
    });
  }

  previousState(): void {
    window.history.back();
  }
  changeCantidad(): void {
    const newCant = this.editForm.controls.cantidad.value;
    this.diferencia = this.cantInicial - (newCant ?? 0);
  }

  save(): void {
    this.isSaving = true;
    this.editForm.controls.cantidadEnPrestamo.enable();
    const com = this.editForm.controls.comentario.value || '';
    if (this.diferencia !== 0 && com?.length < 10) {
      this.editForm.controls.cantidadEnPrestamo.disable();
      this.isSaving = false;

      this.alertService.addAlert(
        {
          type: 'danger',
          message: 'Se debe agregar un comentario de al menos 10 carácteres.',
          timeout: 5000,
          toast: false,
        },
        this.alertService.get()
      );
      return;
    }
    this.editForm.controls.id.enable();

    const formData = this.editForm.value;
    const comentario = formData.comentario;
    delete formData.comentario;
    const data = {
      material: formData,
      observacion: comentario,
    };
    this.subscribeToSaveResponse(this.materialesService.updateStock(data));
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMateriales>>): void {
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

  protected updateForm(materiales: IMateriales): void {
    this.materiales = materiales;
    this.materialesFormService.resetForm(this.editForm, materiales);
  }
}
