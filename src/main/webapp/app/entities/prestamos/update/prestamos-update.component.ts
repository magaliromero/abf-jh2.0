/* eslint-disable eqeqeq */
/* eslint-disable @typescript-eslint/explicit-function-return-type */
import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PrestamosFormService, PrestamosFormGroup } from './prestamos-form.service';
import { IPrestamos } from '../prestamos.model';
import { PrestamosService } from '../service/prestamos.service';
import { IMateriales } from 'app/entities/materiales/materiales.model';
import { MaterialesService } from 'app/entities/materiales/service/materiales.service';
import { IAlumnos } from 'app/entities/alumnos/alumnos.model';
import { AlumnosService } from 'app/entities/alumnos/service/alumnos.service';
import { EstadosPrestamos } from 'app/entities/enumerations/estados-prestamos.model';
import dayjs from 'dayjs/esm';
import { AlertService } from 'app/core/util/alert.service';

@Component({
  selector: 'jhi-prestamos-update',
  templateUrl: './prestamos-update.component.html',
})
export class PrestamosUpdateComponent implements OnInit {
  isSaving = false;
  prestamos: IPrestamos | null = null;
  estadosPrestamosValues = Object.keys(EstadosPrestamos);

  materialesSharedCollection: IMateriales[] = [];
  alumnosSharedCollection: IAlumnos[] = [];

  editForm: PrestamosFormGroup = this.prestamosFormService.createPrestamosFormGroup();

  hayStock = true;

  constructor(
    protected prestamosService: PrestamosService,
    protected prestamosFormService: PrestamosFormService,
    protected materialesService: MaterialesService,
    protected alumnosService: AlumnosService,
    protected activatedRoute: ActivatedRoute,
    private alertService: AlertService
  ) {}

  compareMateriales = (o1: IMateriales | null, o2: IMateriales | null): boolean => this.materialesService.compareMateriales(o1, o2);

  compareAlumnos = (o1: IAlumnos | null, o2: IAlumnos | null): boolean => this.alumnosService.compareAlumnos(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prestamos }) => {
      this.prestamos = prestamos;
      if (prestamos) {
        this.updateForm(prestamos);
      } else {
        const now = new Date();
        const date = dayjs(now);
        this.editForm.controls.fechaPrestamo.setValue(date);

        this.editForm.controls.estado.setValue(EstadosPrestamos.VENCIDO);
      }

      this.loadRelationshipsOptions();
    });
  }
  verificarStock() {
    const data = this.materialesSharedCollection.find(item => item.id == this.editForm.controls.materiales.value?.id);
    if ((data?.cantidad ?? 0) < 1) {
      const mensaje = `El material solicitado no se encuentra disponible para préstamo.`;
      this.alertService.addAlert(
        {
          type: 'danger',
          message: mensaje,
          timeout: 5000,
          toast: false,
        },
        this.alertService.get()
      );
      this.hayStock = false;
    } else {
      this.hayStock = true;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const prestamos = this.prestamosFormService.getPrestamos(this.editForm);
    if (prestamos.id !== null) {
      this.subscribeToSaveResponse(this.prestamosService.update(prestamos));
    } else {
      this.subscribeToSaveResponse(this.prestamosService.create(prestamos));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrestamos>>): void {
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

  protected updateForm(prestamos: IPrestamos): void {
    this.prestamos = prestamos;
    this.prestamosFormService.resetForm(this.editForm, prestamos);

    this.materialesSharedCollection = this.materialesService.addMaterialesToCollectionIfMissing<IMateriales>(
      this.materialesSharedCollection,
      prestamos.materiales
    );
    this.alumnosSharedCollection = this.alumnosService.addAlumnosToCollectionIfMissing<IAlumnos>(
      this.alumnosSharedCollection,
      prestamos.alumnos
    );
  }

  protected loadRelationshipsOptions(): void {
    this.materialesService
      .query()
      .pipe(map((res: HttpResponse<IMateriales[]>) => res.body ?? []))
      .pipe(
        map((materiales: IMateriales[]) =>
          this.materialesService.addMaterialesToCollectionIfMissing<IMateriales>(materiales, this.prestamos?.materiales)
        )
      )
      .subscribe((materiales: IMateriales[]) => (this.materialesSharedCollection = materiales));

    this.alumnosService
      .query()
      .pipe(map((res: HttpResponse<IAlumnos[]>) => res.body ?? []))
      .pipe(map((alumnos: IAlumnos[]) => this.alumnosService.addAlumnosToCollectionIfMissing<IAlumnos>(alumnos, this.prestamos?.alumnos)))
      .subscribe((alumnos: IAlumnos[]) => (this.alumnosSharedCollection = alumnos));
  }
}
