import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IEmpleado, Empleado } from '../empleado.model';
import { EmpleadoService } from '../service/empleado.service';

@Component({
  selector: 'jhi-empleado-update',
  templateUrl: './empleado-update.component.html',
})
export class EmpleadoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    dni: [],
    nombre: [],
    activo: [],
    numeroVentas: [],
  });

  constructor(protected empleadoService: EmpleadoService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ empleado }) => {
      this.updateForm(empleado);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const empleado = this.createFromForm();
    if (empleado.id !== undefined) {
      this.subscribeToSaveResponse(this.empleadoService.update(empleado));
    } else {
      this.subscribeToSaveResponse(this.empleadoService.create(empleado));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmpleado>>): void {
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

  protected updateForm(empleado: IEmpleado): void {
    this.editForm.patchValue({
      id: empleado.id,
      dni: empleado.dni,
      nombre: empleado.nombre,
      activo: empleado.activo,
      numeroVentas: empleado.numeroVentas,
    });
  }

  protected createFromForm(): IEmpleado {
    return {
      ...new Empleado(),
      id: this.editForm.get(['id'])!.value,
      dni: this.editForm.get(['dni'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      activo: this.editForm.get(['activo'])!.value,
      numeroVentas: this.editForm.get(['numeroVentas'])!.value,
    };
  }
}
