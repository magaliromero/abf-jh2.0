import dayjs from 'dayjs/esm';
import { IFacturas } from 'app/entities/facturas/facturas.model';
import { Motivo } from 'app/entities/enumerations/motivo.model';

export interface INotaCredito {
  id: number;
  fecha?: dayjs.Dayjs | null;
  notaNro?: string | null;
  puntoExpedicion?: number | null;
  sucursal?: number | null;
  razonSocial?: string | null;
  ruc?: string | null;
  direccion?: string | null;
  motivoEmision?: keyof typeof Motivo | null;
  total?: number | null;
  facturas?: Pick<IFacturas, 'id' | 'facturaNro'> | null;
}

export type NewNotaCredito = Omit<INotaCredito, 'id'> & { id: null };
