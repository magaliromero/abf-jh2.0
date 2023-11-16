import dayjs from 'dayjs/esm';
import { IAlumnos } from 'app/entities/alumnos/alumnos.model';
import { CondicionVenta } from 'app/entities/enumerations/condicion-venta.model';
import { EstadosFacturas } from 'app/entities/enumerations/estados-facturas.model';

export interface IFacturas {
  id: number;
  fecha?: dayjs.Dayjs | null;
  facturaNro?: string | null;
  timbrado?: number | null;
  puntoExpedicion?: number | null;
  sucursal?: number | null;
  razonSocial?: string | null;
  ruc?: string | null;
  condicionVenta?: CondicionVenta | null;
  total?: number | null;
  estado?: EstadosFacturas | null;
  alumnos?: Pick<IAlumnos, 'id'> | null;
}

export type NewFacturas = Omit<IFacturas, 'id'> & { id: null };
