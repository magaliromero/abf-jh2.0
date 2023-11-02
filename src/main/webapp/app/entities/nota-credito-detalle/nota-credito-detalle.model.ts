import { INotaCredito } from 'app/entities/nota-credito/nota-credito.model';
import { IProductos } from 'app/entities/productos/productos.model';

export interface INotaCreditoDetalle {
  id: number;
  cantidad?: number | null;
  precioUnitario?: number | null;
  subtotal?: number | null;
  porcentajeIva?: number | null;
  valorPorcentaje?: number | null;
  notaCredito?: Pick<INotaCredito, 'id' | 'notaNro'> | null;
  producto?: Pick<IProductos, 'id' | 'descripcion'> | null;
}

export type NewNotaCreditoDetalle = Omit<INotaCreditoDetalle, 'id'> & { id: null };
