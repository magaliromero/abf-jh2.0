import dayjs from 'dayjs/esm';

export interface ITimbrados {
  id: number;
  numeroTimbrado?: number | null;
  fechaInicio?: dayjs.Dayjs | null;
  fechaFin?: dayjs.Dayjs | null;
}

export type NewTimbrados = Omit<ITimbrados, 'id'> & { id: null };
