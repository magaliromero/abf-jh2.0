export interface IMateriales {
  id: number;
  descripcion?: string | null;
  estado?: string | null;
  cantidad?: number | null;
}

export type NewMateriales = Omit<IMateriales, 'id'> & { id: null };
