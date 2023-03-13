import { IUsuarios, NewUsuarios } from './usuarios.model';

export const sampleWithRequiredData: IUsuarios = {
  id: 97822,
  documento: 93174,
  idRol: 39895,
};

export const sampleWithPartialData: IUsuarios = {
  id: 46659,
  documento: 42978,
  idRol: 23327,
};

export const sampleWithFullData: IUsuarios = {
  id: 47166,
  documento: 67261,
  idRol: 68646,
};

export const sampleWithNewData: NewUsuarios = {
  documento: 59570,
  idRol: 45728,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
