/* eslint-disable @typescript-eslint/no-unsafe-return */
/* eslint-disable @typescript-eslint/explicit-function-return-type */
/* eslint-disable @typescript-eslint/member-ordering */
/* eslint-disable eqeqeq */
/* eslint-disable @typescript-eslint/restrict-template-expressions */
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
import { AlertService } from 'app/core/util/alert.service';
import { PuntoDeExpedicionService } from 'app/entities/punto-de-expedicion/service/punto-de-expedicion.service';
import { SucursalesService } from 'app/entities/sucursales/service/sucursales.service';
import { TimbradosService } from 'app/entities/timbrados/service/timbrados.service';
import { ConsultaClienteService } from 'app/util-services/consulta-cliente.service';
import dayjs from 'dayjs/esm';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ProductosService } from 'app/entities/productos/service/productos.service';
import { ClientesUpdateComponent } from 'app/entities/clientes/update/clientes-update.component';

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
  isTotalNCValid = false;

  editForm: NotaCreditoFormGroup = this.notaCreditoFormService.createNotaCreditoFormGroup();

  nuevoItem: any = {
    cantidad: undefined,
    subtotal: undefined,
    precio: undefined,
    iva: undefined,
    producto: undefined,
  };
  productos: any[] = [];
  timbrados: any[] = [];
  sucursales: any[] = [];
  listaDetalle: any[] = [];
  puntosExpedicion: any[] = [];

  constructor(
    protected notaCreditoService: NotaCreditoService,
    protected notaCreditoFormService: NotaCreditoFormService,
    protected facturasService: FacturasService,
    private alertService: AlertService,
    protected activatedRoute: ActivatedRoute,
    protected consultaCliente: ConsultaClienteService,
    protected timbradoService: TimbradosService,
    protected sucursalesService: SucursalesService,
    protected puntosExpedicionSerice: PuntoDeExpedicionService,
    protected productoService: ProductosService,

    protected modalService: NgbModal
  ) {}

  compareFacturas = (o1: IFacturas | null, o2: IFacturas | null): boolean => this.facturasService.compareFacturas(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notaCredito, facturas }) => {
      this.notaCredito = notaCredito;
      if (notaCredito) {
        this.updateForm(notaCredito);
      }
      if (facturas) {
        this.editForm.controls.facturas.setValue(facturas);
        this.editForm.controls.razonSocial.setValue(facturas.razonSocial);
        this.editForm.controls.ruc.setValue(facturas.ruc);
        // this.editForm.controls.direccion.setValue(facturas.direccion);
        this.validarMontoNC(facturas);
      } else {
        const now = new Date();
        const date = dayjs(now);
        this.editForm.controls.total.disable();
        this.editForm.controls.fecha.setValue(date);
      }

      this.loadRelationshipsOptions();
    });

    this.queryBackendProductos().subscribe(data => {
      const { body } = data;
      this.productos = body;
    });
    this.queryBackendTimbrados().subscribe(data => {
      const { body } = data;
      this.timbrados = body;
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    /*  this.isSaving = true;
    const notaCredito = this.notaCreditoFormService.getNotaCredito(this.editForm);
    if (notaCredito.id !== null) {
      this.subscribeToSaveResponse(this.notaCreditoService.update(notaCredito));
    } else {
      this.subscribeToSaveResponse(this.notaCreditoService.create(notaCredito));
    } */

    this.isSaving = true;
    const cabecera = this.notaCreditoFormService.getNotaCredito(this.editForm);
    const detalle = this.listaDetalle.map((item: any) => ({
      cantidad: item.cantidad,
      precioUnitario: item.precio,
      // eslint-disable-next-line radix
      producto: parseInt(item.producto),
      subtotal: item.subtotal,
    }));

    const data = {
      cabecera,
      detalle,
    };
    console.log(data);

    this.subscribeToSaveResponse(this.notaCreditoService.createNew(data));
  }

  validarMontoNC(facturas: any): void {
    const nc = facturas.notaCreditos;
    let totalNC = 0;
    for (let index = 0; index < nc.length; index++) {
      const element = nc[index];
      totalNC += element.total;
    }

    if (totalNC >= facturas.total) {
      this.isTotalNCValid = true;
      this.alertService.addAlert(
        {
          type: 'danger',
          message: `El monto total de Notas de Crédito para la factura seleccionada ya ha alcanzado el limite disponible`,
          timeout: 5000,
          toast: false,
        },
        this.alertService.get()
      );
    } else {
      this.alertService.addAlert(
        {
          type: 'warning',
          message: `Monto total NC de factura  es: ${totalNC} - ${facturas.total}`,
          timeout: 5000,
          toast: false,
        },
        this.alertService.get()
      );
      this.editForm.controls.total.setValue(facturas.total - totalNC);
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
  public updateRazonSocial() {
    const ruc = this.editForm.controls.ruc.value;
    this.consultaCliente.consultarCliente(ruc ?? ' ').subscribe({
      next: (response: any) => {
        this.editForm.controls.razonSocial.setValue(response.body.razonSocial);
        this.editForm.controls.direccion.setValue(response.body.direccion);
      },
      error(error: any) {
        //this.modalService.open(ClientesUpdateComponent);
      },
    });
  }

  asignarNroFactura() {
    const timbrado = this.editForm.controls.timbrado.value;
    const pe = this.editForm.controls.puntoExpedicion.value;
    const sucursal = this.editForm.controls.sucursal.value;
    this.consultaCliente.consultaNumeroFac(timbrado, pe, sucursal).subscribe({
      next: (resp: any) => {
        const nro = resp.body?.toString() ?? '';
        this.editForm.controls.notaNro.setValue(nro);
      },
    });
  }
  calcularTotal(): void {
    let total = 0;
    for (let i = 0; i < this.listaDetalle.length; i++) {
      const element = this.listaDetalle[i];
      total += element.subtotal;
    }
    this.editForm.controls.total.setValue(total);
  }
  agregarDetalle(): void {
    for (const propiedad in this.nuevoItem) {
      // eslint-disable-next-line no-prototype-builtins
      if (this.nuevoItem.hasOwnProperty(propiedad)) {
        if (this.nuevoItem[propiedad] === undefined) {
          this.alertService.addAlert(
            {
              type: 'danger',
              message: 'Debe agregar todos los campos requeridos para el detalle de la NC.',
              timeout: 5000,
              toast: false,
            },
            this.alertService.get()
          );
          return;
        }
      }
    }
    this.listaDetalle.push(Object.assign({}, this.nuevoItem));
    this.nuevoItem = {
      cantidad: undefined,
      subtotal: undefined,
      precio: undefined,
      iva: undefined,
      producto: undefined,
    };
    this.calcularTotal();
    console.log(this.listaDetalle);
  }
  eliminarDetalle(i: any): void {
    this.listaDetalle.splice(i, 1);
    this.calcularTotal();
  }
  calcularSubtotal(): void {
    this.nuevoItem.subtotal = (this.nuevoItem.cantidad ? this.nuevoItem.cantidad : 0) * (this.nuevoItem.precio ? this.nuevoItem.precio : 0);
  }
  seleccionaProducto(): void {
    // eslint-disable-next-line eqeqeq
    const product = this.productos.find(item => item.id == this.nuevoItem.producto);
    if (product) {
      this.nuevoItem.precio = product.precioUnitario;
      this.nuevoItem.iva = product.porcentajeIva;
      this.nuevoItem.descripcionProducto = product.descripcion;
    }
  }

  // eslint-disable-next-line @typescript-eslint/explicit-function-return-type
  obtenerSucursales() {
    this.validarTimbrado();
    this.editForm.controls.puntoExpedicion.setValue(null);
    this.editForm.controls.sucursal.setValue(null);
    this.puntosExpedicion = [];
    this.sucursales = [];
    this.queryBackenSucursales().subscribe(data => {
      const { body } = data;
      this.sucursales = body;
    });
  }
  // eslint-disable-next-line @typescript-eslint/explicit-function-return-type
  obtenerPE() {
    this.editForm.controls.puntoExpedicion.setValue(null);
    this.puntosExpedicion = [];
    this.queryBackendPE().subscribe(data => {
      const { body } = data;
      this.puntosExpedicion = body;
    });
  }
  validarTimbrado() {
    const data = this.timbrados.find(item => item.numeroTimbrado == this.editForm.controls.timbrado.value);
    const cantDias = data?.fechaFin?.diff(new Date(), 'days');
    // eslint-disable-next-line @typescript-eslint/restrict-template-expressions
    const mensaje = `El timbrado vence en ${cantDias ?? 0} días.`;
    if (cantDias > 15 && cantDias < 31) {
      this.alertService.addAlert(
        {
          type: 'warning',
          message: mensaje,
          timeout: 5000,
          toast: false,
        },
        this.alertService.get()
      );
    } else if (cantDias > 0 && cantDias < 15) {
      this.alertService.addAlert(
        {
          type: 'danger',
          message: mensaje,
          timeout: 5000,
          toast: false,
        },
        this.alertService.get()
      );
    }
  }
  protected queryBackendProductos(): Observable<any> {
    const pageToLoad = 1;
    const queryObject: any = {
      page: pageToLoad - 1,
      size: 100,
      sort: '',
    };
    return this.productoService.query(queryObject);
  }
  protected queryBackendTimbrados(): Observable<any> {
    const pageToLoad = 1;
    const now = new Date();
    const date = dayjs(now);
    const queryObject: any = {
      page: pageToLoad - 1,
      size: 100,
      sort: '',
      'fechaFin.greaterThan': date.format('YYYY-MM-DD'),
    };
    return this.timbradoService.query(queryObject);
  }
  protected queryBackenSucursales(): Observable<any> {
    const data = this.timbrados.find(item => item.numeroTimbrado == this.editForm.controls.timbrado.value);
    const pageToLoad = 1;
    const queryObject: any = {
      page: pageToLoad - 1,
      size: 20,
      sort: ['id,asc'],
      eagerload: true,
      'timbradosId.in': data.id,
    };
    return this.sucursalesService.query(queryObject);
  }
  protected queryBackendPE(): Observable<any> {
    const data = this.sucursales.find(item => item.numeroEstablecimiento == this.editForm.controls.sucursal.value);

    const pageToLoad = 1;
    const queryObject: any = {
      page: pageToLoad - 1,
      size: 100,
      sort: '',
      'sucursalesId.in': data.id,
    };
    return this.puntosExpedicionSerice.query(queryObject);
  }
}
