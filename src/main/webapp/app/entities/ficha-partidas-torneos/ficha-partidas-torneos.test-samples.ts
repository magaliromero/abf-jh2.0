import { IFichaPartidasTorneos, NewFichaPartidasTorneos } from './ficha-partidas-torneos.model';

export const sampleWithRequiredData: IFichaPartidasTorneos = {
  id: 7725,
};

export const sampleWithPartialData: IFichaPartidasTorneos = {
  id: 98891,
  winner: 'Mascotas',
  resultado: 'Relacciones deposit',
};

export const sampleWithFullData: IFichaPartidasTorneos = {
  id: 746,
  nombreContrincante: 'Teclado Checking',
  duracion: 69744,
  winner: 'withdrawal',
  resultado: 'Rial',
  comentarios: 'Informática Artesanal auxiliary',
  nombreArbitro: 'microchip Inteligente Práctico',
};

export const sampleWithNewData: NewFichaPartidasTorneos = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
