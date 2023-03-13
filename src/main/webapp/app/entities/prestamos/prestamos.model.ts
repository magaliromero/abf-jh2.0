import dayjs from 'dayjs/esm';

export interface IPrestamos {
  id: number;
  fechaPrestamo?: dayjs.Dayjs | null;
  vigenciaPrestamo?: number | null;
  fechaDevolucion?: dayjs.Dayjs | null;
}

export type NewPrestamos = Omit<IPrestamos, 'id'> & { id: null };
