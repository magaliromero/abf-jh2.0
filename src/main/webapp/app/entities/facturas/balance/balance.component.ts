/* eslint-disable @typescript-eslint/no-unsafe-return */
/* eslint-disable @typescript-eslint/restrict-plus-operands */
import { Component, OnInit } from '@angular/core';
import dayjs from 'dayjs/esm';
import { FacturasService } from '../service/facturas.service';
import { AlertService } from 'app/core/util/alert.service';

@Component({
  selector: 'jhi-balance',
  templateUrl: './balance.component.html',
  styleUrls: ['./balance.component.scss'],
})
export class BalanceComponent implements OnInit {
  startDate: any;
  endDate: any;
  isLoading = false;
  listaFactura: any[] = [];
  listaNC: any[] = [];
  diferencia = 0;
  totalNota = 0;
  totalFactura = 0;

  constructor(private facturaService: FacturasService, private alertService: AlertService) {}

  ngOnInit(): void {
    const now = new Date();
    const date = dayjs(now);
    this.startDate = date;
    this.endDate = date;
    this.actualizar();
  }
  verificarFecha(): void {
    if (!this.isValidDateRange()) {
      this.alertService.addAlert(
        {
          type: 'warning',
          message: 'La fecha inicio no debe ser mayor a la fecha fin, y ambas deben ser el inicio y fin del mismo mes.',
          timeout: 5000,
          toast: false,
        },
        this.alertService.get()
      );
    }
  }
  // eslint-disable-next-line @typescript-eslint/explicit-function-return-type
  isValidDateRange() {
    const startDate = dayjs(this.startDate);
    const endDate = dayjs(this.endDate);

    // Compara el año y mes de ambas fechas
    const isSameMonth = startDate.isSame(endDate, 'month');

    // Comprueba si la fecha de inicio es anterior o igual a la fecha de fin
    const isStartDateBeforeEndDate = startDate.isBefore(endDate) || startDate.isSame(endDate);

    return isSameMonth && isStartDateBeforeEndDate;
  }
  actualizar(): void {
    this.isLoading = true;
    const param = {
      fechaInicio: dayjs(this.startDate).format('YYYY-MM-DD'),
      fechaFin: dayjs(this.endDate).format('YYYY-MM-DD'),
    };

    this.facturaService.balance(param).subscribe({
      next: data => {
        this.isLoading = false;
        this.listaFactura = data.body.listaFactura;
        this.listaNC = data.body.listaNC;
        this.calcularDiferencia();
      },
      error: data => {
        this.isLoading = false;
      },
    });
  }
  calcularDiferencia(): void {
    this.totalFactura = this.listaFactura.reduce((acc, item) => acc + item.total, 0);
    this.totalNota = this.listaNC.reduce((acc, item) => acc + item.total, 0);
    this.diferencia = this.totalFactura - this.totalNota;
  }
  formatSPE(i: any): string {
    return String(i).padStart(3, '0');
  }
}
