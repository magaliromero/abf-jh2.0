export interface ICursos {
  id: number;
  nombreCurso?: string | null;
}

export type NewCursos = Omit<ICursos, 'id'> & { id: null };
