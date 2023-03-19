import { IMallaCurricular } from 'app/entities/malla-curricular/malla-curricular.model';

export interface ITemas {
  id: number;
  titulo?: string | null;
  descripcion?: string | null;
  mallaCurriculars?: Pick<IMallaCurricular, 'id' | 'titulo'>[] | null;
}

export type NewTemas = Omit<ITemas, 'id'> & { id: null };
