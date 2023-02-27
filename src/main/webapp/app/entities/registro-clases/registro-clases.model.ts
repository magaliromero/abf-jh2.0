import dayjs from 'dayjs/esm';
import { IMallaCurricular } from 'app/entities/malla-curricular/malla-curricular.model';
import { ITemas } from 'app/entities/temas/temas.model';
import { IFuncionarios } from 'app/entities/funcionarios/funcionarios.model';
import { IAlumnos } from 'app/entities/alumnos/alumnos.model';

export interface IRegistroClases {
  id: number;
  fecha?: dayjs.Dayjs | null;
  cantidadHoras?: number | null;
  asistenciaAlumno?: boolean | null;
  mallaCurricular?: Pick<IMallaCurricular, 'id' | 'titulo'> | null;
  temas?: Pick<ITemas, 'id' | 'titulo'> | null;
  funcionarios?: Pick<IFuncionarios, 'id' | 'nombreCompleto'> | null;
  alumnos?: Pick<IAlumnos, 'id' | 'nombreCompleto'> | null;
}

export type NewRegistroClases = Omit<IRegistroClases, 'id'> & { id: null };
