import { ITemas } from 'app/entities/temas/temas.model';
import { Niveles } from 'app/entities/enumerations/niveles.model';

export interface IMallaCurricular {
  id: number;
  titulo?: string | null;
  nivel?: Niveles | null;
  temas?: Pick<ITemas, 'id' | 'titulo'>[] | null;
}

export type NewMallaCurricular = Omit<IMallaCurricular, 'id'> & { id: null };
