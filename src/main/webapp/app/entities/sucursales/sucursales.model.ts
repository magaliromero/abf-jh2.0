import { ITimbrados } from 'app/entities/timbrados/timbrados.model';

export interface ISucursales {
  id: number;
  nombreSucursal?: string | null;
  direccion?: string | null;
  numeroEstablecimiento?: number | null;
  timbrados?: Pick<ITimbrados, 'id' | 'numeroTimbrado'> | null;
}

export type NewSucursales = Omit<ISucursales, 'id'> & { id: null };
