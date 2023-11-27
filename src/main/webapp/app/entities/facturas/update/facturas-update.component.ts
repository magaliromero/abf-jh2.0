/* eslint-disable eqeqeq */
/* eslint-disable @typescript-eslint/explicit-function-return-type */
import dayjs from 'dayjs/esm';
import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { FacturasFormService, FacturasFormGroup } from './facturas-form.service';
import { IFacturas } from '../facturas.model';
import { FacturasService } from '../service/facturas.service';
import { CondicionVenta } from 'app/entities/enumerations/condicion-venta.model';
import { ConsultaClienteService } from 'app/util-services/consulta-cliente.service';
import { EntityArrayResponseType, ProductosService } from 'app/entities/productos/service/productos.service';
import { TimbradosService } from 'app/entities/timbrados/service/timbrados.service';
import { SucursalesService } from 'app/entities/sucursales/service/sucursales.service';
import { PuntoDeExpedicionService } from 'app/entities/punto-de-expedicion/service/punto-de-expedicion.service';
import { AlertService } from 'app/core/util/alert.service';
import { ITimbrados } from 'app/entities/timbrados/timbrados.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ClientesUpdateComponent } from 'app/entities/clientes/update/clientes-update.component';
import { EstadosFacturas } from 'app/entities/enumerations/estados-facturas.model';

@Component({
  selector: 'jhi-facturas-update',
  templateUrl: './facturas-update.component.html',
})
export class FacturasUpdateComponent implements OnInit {
  isSaving = false;
  facturas: IFacturas | null = null;

  condicionVentaValues = Object.keys(CondicionVenta);
  estadosFacturas = Object.keys(EstadosFacturas);

  editForm: FacturasFormGroup = this.facturasFormService.createFacturasFormGroup();

  nuevoItem: any = {};
  productos: any[] = [];
  timbrados: any[] = [];
  sucursales: any[] = [];
  listaDetalle: any[] = [];
  puntosExpedicion: any[] = [];
  constructor(
    protected facturasService: FacturasService,
    protected facturasFormService: FacturasFormService,
    protected productoService: ProductosService,
    protected activatedRoute: ActivatedRoute,
    protected consultaCliente: ConsultaClienteService,
    protected timbradoService: TimbradosService,
    protected sucursalesService: SucursalesService,
    protected puntosExpedicionSerice: PuntoDeExpedicionService,
    private alertService: AlertService,
    protected modalService: NgbModal
  ) {}

  ngOnInit(): void {
    //this.editForm.controls.condicionVenta.setValue(CondicionVenta.CONTADO);

    this.activatedRoute.data.subscribe(({ facturas }) => {
      console.log(facturas);

      this.facturas = facturas;
      if (facturas) {
        this.updateForm(facturas);

        // this.timbrados.find();
        this.editForm.controls.timbrado.setValue(facturas.timbrado);

        this.obtenerSucursales();
        this.editForm.controls.sucursal.setValue(facturas.sucursal);

        this.obtenerPE();
        this.editForm.controls.puntoExpedicion.setValue(facturas.puntoExpedicion);

        // this.editForm.controls.estado(this.estadosFacturas.find(item => item === facturas.estado))
        this.listaDetalle = facturas.facturaDetalles;
        this.editForm.controls.total.disable();
        if (facturas.poseeNC) {
          this.editForm.controls.estado.disable();
          this.editForm.controls.ruc.disable();
          this.editForm.controls.razonSocial.disable();
        }
      } else {
        const now = new Date();
        const date = dayjs(now);
        this.editForm.controls.total.disable();
        this.editForm.controls.fecha.setValue(date);

        this.editForm.controls.estado.setValue(EstadosFacturas.PAGADO);
        this.editForm.controls.condicionVenta.setValue(CondicionVenta.CONTADO);
      }
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
    this.isSaving = true;
    const facturas = this.facturasFormService.getFacturas(this.editForm);
    const detalle = this.listaDetalle.map((item: any) => ({
      cantidad: item.cantidad,
      precioUnitario: item.precio,
      // eslint-disable-next-line radix
      producto: parseInt(item.producto),
      subtotal: item.subtotal,
    }));
    const data = {
      factura: facturas,
      // eslint-disable-next-line object-shorthand
      detalle: detalle,
    };
    this.subscribeToSaveResponse(this.facturasService.createNew(data));

    /* if (facturas.id !== null) {
      this.subscribeToSaveResponse(this.facturasService.update(facturas));
    } else {
      this.subscribeToSaveResponse(this.facturasService.create(facturas));
    } */
  }
  agregarDetalle(): void {
    this.listaDetalle.push(Object.assign({}, this.nuevoItem));
    this.nuevoItem = {};
    this.calcularTotal();
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
  asignarNroFactura() {
    const timbrado = this.editForm.controls.timbrado.value;
    const pe = this.editForm.controls.puntoExpedicion.value;
    const sucursal = this.editForm.controls.sucursal.value;
    this.consultaCliente.consultaNumeroFac(timbrado, pe, sucursal).subscribe({
      next: (resp: any) => {
        const nro = resp.body?.toString() ?? '';
        this.editForm.controls.facturaNro.setValue(nro);
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFacturas>>): void {
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

  protected updateForm(facturas: IFacturas): void {
    this.facturas = facturas;
    this.facturasFormService.resetForm(this.editForm, facturas);
  }

  // eslint-disable-next-line @typescript-eslint/member-ordering
  public updateRazonSocial() {
    const ruc = this.editForm.controls.ruc.value;
    this.consultaCliente.consultarCliente(ruc ?? ' ').subscribe({
      next: (response: any) => {
        this.editForm.controls.razonSocial.setValue(response.body.razonSocial);
      },
      error: (error: any) => {
        this.modalService.open(ClientesUpdateComponent);
      },
    });
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
