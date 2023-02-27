import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { MallaCurricularFormService, MallaCurricularFormGroup } from './malla-curricular-form.service';
import { IMallaCurricular } from '../malla-curricular.model';
import { MallaCurricularService } from '../service/malla-curricular.service';
import { ITemas } from 'app/entities/temas/temas.model';
import { TemasService } from 'app/entities/temas/service/temas.service';
import { Niveles } from 'app/entities/enumerations/niveles.model';

@Component({
  selector: 'jhi-malla-curricular-update',
  templateUrl: './malla-curricular-update.component.html',
})
export class MallaCurricularUpdateComponent implements OnInit {
  isSaving = false;
  mallaCurricular: IMallaCurricular | null = null;
  nivelesValues = Object.keys(Niveles);

  temasSharedCollection: ITemas[] = [];

  editForm: MallaCurricularFormGroup = this.mallaCurricularFormService.createMallaCurricularFormGroup();

  constructor(
    protected mallaCurricularService: MallaCurricularService,
    protected mallaCurricularFormService: MallaCurricularFormService,
    protected temasService: TemasService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareTemas = (o1: ITemas | null, o2: ITemas | null): boolean => this.temasService.compareTemas(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mallaCurricular }) => {
      this.mallaCurricular = mallaCurricular;
      if (mallaCurricular) {
        this.updateForm(mallaCurricular);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mallaCurricular = this.mallaCurricularFormService.getMallaCurricular(this.editForm);
    if (mallaCurricular.id !== null) {
      this.subscribeToSaveResponse(this.mallaCurricularService.update(mallaCurricular));
    } else {
      this.subscribeToSaveResponse(this.mallaCurricularService.create(mallaCurricular));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMallaCurricular>>): void {
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

  protected updateForm(mallaCurricular: IMallaCurricular): void {
    this.mallaCurricular = mallaCurricular;
    this.mallaCurricularFormService.resetForm(this.editForm, mallaCurricular);

    this.temasSharedCollection = this.temasService.addTemasToCollectionIfMissing<ITemas>(
      this.temasSharedCollection,
      ...(mallaCurricular.temas ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.temasService
      .query()
      .pipe(map((res: HttpResponse<ITemas[]>) => res.body ?? []))
      .pipe(
        map((temas: ITemas[]) => this.temasService.addTemasToCollectionIfMissing<ITemas>(temas, ...(this.mallaCurricular?.temas ?? [])))
      )
      .subscribe((temas: ITemas[]) => (this.temasSharedCollection = temas));
  }
}
