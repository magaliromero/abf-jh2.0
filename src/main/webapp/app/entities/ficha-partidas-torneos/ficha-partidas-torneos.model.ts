import { ITorneos } from 'app/entities/torneos/torneos.model';

export interface IFichaPartidasTorneos {
  id: number;
  nombreContrincante?: string | null;
  duracion?: number | null;
  winner?: string | null;
  resultado?: string | null;
  comentarios?: string | null;
  nombreArbitro?: string | null;
  torneos?: Pick<ITorneos, 'id' | 'nombreTorneo'> | null;
}

export type NewFichaPartidasTorneos = Omit<IFichaPartidasTorneos, 'id'> & { id: null };
