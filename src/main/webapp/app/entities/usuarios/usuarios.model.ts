export interface IUsuarios {
  id: number;
  documento?: number | null;
  idRol?: number | null;
}

export type NewUsuarios = Omit<IUsuarios, 'id'> & { id: null };
