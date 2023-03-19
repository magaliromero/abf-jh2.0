import dayjs from 'dayjs/esm';

export interface ITorneos {
  id: number;
  nombreTorneo?: string | null;
  fechaInicio?: dayjs.Dayjs | null;
  fechaFin?: dayjs.Dayjs | null;
  lugar?: string | null;
  tiempo?: string | null;
  tipoTorneo?: string | null;
  torneoEvaluado?: boolean | null;
  federado?: boolean | null;
}

export type NewTorneos = Omit<ITorneos, 'id'> & { id: null };
