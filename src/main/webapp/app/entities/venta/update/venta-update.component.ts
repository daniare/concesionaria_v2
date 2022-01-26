import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IVenta, Venta } from '../venta.model';
import { VentaService } from '../service/venta.service';
import { ICoche } from 'app/entities/coche/coche.model';
import { CocheService } from 'app/entities/coche/service/coche.service';
import { IEmpleado } from 'app/entities/empleado/empleado.model';
import { EmpleadoService } from 'app/entities/empleado/service/empleado.service';
import { ICliente } from 'app/entities/cliente/cliente.model';
import { ClienteService } from 'app/entities/cliente/service/cliente.service';

@Component({
  selector: 'jhi-venta-update',
  templateUrl: './venta-update.component.html',
})
export class VentaUpdateComponent implements OnInit {
  isSaving = false;

  cochesCollection: ICoche[] = [];
  empleadosSharedCollection: IEmpleado[] = [];
  clientesSharedCollection: ICliente[] = [];

  editForm = this.fb.group({
    id: [],
    fecha: [],
    tipoPago: [],
    total: [],
    coches: [],
    empleado: [],
    cliente: [],
  });

  constructor(
    protected ventaService: VentaService,
    protected cocheService: CocheService,
    protected empleadoService: EmpleadoService,
    protected clienteService: ClienteService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ venta }) => {
      this.updateForm(venta);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const venta = this.createFromForm();
    if (venta.id !== undefined) {
      this.subscribeToSaveResponse(this.ventaService.update(venta));
    } else {
      this.subscribeToSaveResponse(this.ventaService.create(venta));
    }
  }

  trackCocheById(index: number, item: ICoche): number {
    return item.id!;
  }

  trackEmpleadoById(index: number, item: IEmpleado): number {
    return item.id!;
  }

  trackClienteById(index: number, item: ICliente): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVenta>>): void {
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

  protected updateForm(venta: IVenta): void {
    this.editForm.patchValue({
      id: venta.id,
      fecha: venta.fecha,
      tipoPago: venta.tipoPago,
      total: venta.total,
      coches: venta.coches,
      empleado: venta.empleado,
      cliente: venta.cliente,
    });

    this.cochesCollection = this.cocheService.addCocheToCollectionIfMissing(this.cochesCollection, venta.coches);
    this.empleadosSharedCollection = this.empleadoService.addEmpleadoToCollectionIfMissing(this.empleadosSharedCollection, venta.empleado);
    this.clientesSharedCollection = this.clienteService.addClienteToCollectionIfMissing(this.clientesSharedCollection, venta.cliente);
  }

  protected loadRelationshipsOptions(): void {
    this.cocheService
      .query({ filter: 'venta-is-null' })
      .pipe(map((res: HttpResponse<ICoche[]>) => res.body ?? []))
      .pipe(map((coches: ICoche[]) => this.cocheService.addCocheToCollectionIfMissing(coches, this.editForm.get('coches')!.value)))
      .subscribe((coches: ICoche[]) => (this.cochesCollection = coches));

    this.empleadoService
      .query()
      .pipe(map((res: HttpResponse<IEmpleado[]>) => res.body ?? []))
      .pipe(
        map((empleados: IEmpleado[]) =>
          this.empleadoService.addEmpleadoToCollectionIfMissing(empleados, this.editForm.get('empleado')!.value)
        )
      )
      .subscribe((empleados: IEmpleado[]) => (this.empleadosSharedCollection = empleados));

    this.clienteService
      .query()
      .pipe(map((res: HttpResponse<ICliente[]>) => res.body ?? []))
      .pipe(
        map((clientes: ICliente[]) => this.clienteService.addClienteToCollectionIfMissing(clientes, this.editForm.get('cliente')!.value))
      )
      .subscribe((clientes: ICliente[]) => (this.clientesSharedCollection = clientes));
  }

  protected createFromForm(): IVenta {
    return {
      ...new Venta(),
      id: this.editForm.get(['id'])!.value,
      fecha: this.editForm.get(['fecha'])!.value,
      tipoPago: this.editForm.get(['tipoPago'])!.value,
      total: this.editForm.get(['total'])!.value,
      coches: this.editForm.get(['coches'])!.value,
      empleado: this.editForm.get(['empleado'])!.value,
      cliente: this.editForm.get(['cliente'])!.value,
    };
  }
}
