import dayjs from 'dayjs/esm';
import { IMateriales } from 'app/entities/materiales/materiales.model';

export interface IRegistroStockMateriales {
  id: number;
  comentario?: string | null;
  cantidadInicial?: number | null;
  cantidadModificada?: number | null;
  fecha?: dayjs.Dayjs | null;
  materiales?: Pick<IMateriales, 'id'> | null;
}

export type NewRegistroStockMateriales = Omit<IRegistroStockMateriales, 'id'> & { id: null };
