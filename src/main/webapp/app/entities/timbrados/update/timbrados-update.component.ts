import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITimbrados } from '../timbrados.model';
import { TimbradosService } from '../service/timbrados.service';
import { TimbradosFormService, TimbradosFormGroup } from './timbrados-form.service';

@Component({
  standalone: true,
  selector: 'jhi-timbrados-update',
  templateUrl: './timbrados-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TimbradosUpdateComponent implements OnInit {
  isSaving = false;
  timbrados: ITimbrados | null = null;

  editForm: TimbradosFormGroup = this.timbradosFormService.createTimbradosFormGroup();

  constructor(
    protected timbradosService: TimbradosService,
    protected timbradosFormService: TimbradosFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ timbrados }) => {
      this.timbrados = timbrados;
      if (timbrados) {
        this.updateForm(timbrados);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const timbrados = this.timbradosFormService.getTimbrados(this.editForm);
    if (timbrados.id !== null) {
      this.subscribeToSaveResponse(this.timbradosService.update(timbrados));
    } else {
      this.subscribeToSaveResponse(this.timbradosService.create(timbrados));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITimbrados>>): void {
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

  protected updateForm(timbrados: ITimbrados): void {
    this.timbrados = timbrados;
    this.timbradosFormService.resetForm(this.editForm, timbrados);
  }
}
