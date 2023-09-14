import { ISucursales } from 'app/entities/sucursales/sucursales.model';

export interface IPuntoDeExpedicion {
  id: number;
  numeroPuntoDeExpedicion?: number | null;
  sucursales?: Pick<ISucursales, 'id' | 'nombreSucursal'> | null;
}

export type NewPuntoDeExpedicion = Omit<IPuntoDeExpedicion, 'id'> & { id: null };
