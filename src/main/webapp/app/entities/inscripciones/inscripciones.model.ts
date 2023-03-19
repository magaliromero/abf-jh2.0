import dayjs from 'dayjs/esm';
import { IAlumnos } from 'app/entities/alumnos/alumnos.model';

export interface IInscripciones {
  id: number;
  fecha?: dayjs.Dayjs | null;
  alumnos?: Pick<IAlumnos, 'id' | 'nombreCompleto'> | null;
}

export type NewInscripciones = Omit<IInscripciones, 'id'> & { id: null };
