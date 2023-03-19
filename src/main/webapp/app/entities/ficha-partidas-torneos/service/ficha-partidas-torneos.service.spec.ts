import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFichaPartidasTorneos } from '../ficha-partidas-torneos.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../ficha-partidas-torneos.test-samples';

import { FichaPartidasTorneosService } from './ficha-partidas-torneos.service';

const requireRestSample: IFichaPartidasTorneos = {
  ...sampleWithRequiredData,
};

describe('FichaPartidasTorneos Service', () => {
  let service: FichaPartidasTorneosService;
  let httpMock: HttpTestingController;
  let expectedResult: IFichaPartidasTorneos | IFichaPartidasTorneos[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FichaPartidasTorneosService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a FichaPartidasTorneos', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const fichaPartidasTorneos = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(fichaPartidasTorneos).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FichaPartidasTorneos', () => {
      const fichaPartidasTorneos = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(fichaPartidasTorneos).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FichaPartidasTorneos', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FichaPartidasTorneos', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FichaPartidasTorneos', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFichaPartidasTorneosToCollectionIfMissing', () => {
      it('should add a FichaPartidasTorneos to an empty array', () => {
        const fichaPartidasTorneos: IFichaPartidasTorneos = sampleWithRequiredData;
        expectedResult = service.addFichaPartidasTorneosToCollectionIfMissing([], fichaPartidasTorneos);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fichaPartidasTorneos);
      });

      it('should not add a FichaPartidasTorneos to an array that contains it', () => {
        const fichaPartidasTorneos: IFichaPartidasTorneos = sampleWithRequiredData;
        const fichaPartidasTorneosCollection: IFichaPartidasTorneos[] = [
          {
            ...fichaPartidasTorneos,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFichaPartidasTorneosToCollectionIfMissing(fichaPartidasTorneosCollection, fichaPartidasTorneos);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FichaPartidasTorneos to an array that doesn't contain it", () => {
        const fichaPartidasTorneos: IFichaPartidasTorneos = sampleWithRequiredData;
        const fichaPartidasTorneosCollection: IFichaPartidasTorneos[] = [sampleWithPartialData];
        expectedResult = service.addFichaPartidasTorneosToCollectionIfMissing(fichaPartidasTorneosCollection, fichaPartidasTorneos);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fichaPartidasTorneos);
      });

      it('should add only unique FichaPartidasTorneos to an array', () => {
        const fichaPartidasTorneosArray: IFichaPartidasTorneos[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const fichaPartidasTorneosCollection: IFichaPartidasTorneos[] = [sampleWithRequiredData];
        expectedResult = service.addFichaPartidasTorneosToCollectionIfMissing(fichaPartidasTorneosCollection, ...fichaPartidasTorneosArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fichaPartidasTorneos: IFichaPartidasTorneos = sampleWithRequiredData;
        const fichaPartidasTorneos2: IFichaPartidasTorneos = sampleWithPartialData;
        expectedResult = service.addFichaPartidasTorneosToCollectionIfMissing([], fichaPartidasTorneos, fichaPartidasTorneos2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fichaPartidasTorneos);
        expect(expectedResult).toContain(fichaPartidasTorneos2);
      });

      it('should accept null and undefined values', () => {
        const fichaPartidasTorneos: IFichaPartidasTorneos = sampleWithRequiredData;
        expectedResult = service.addFichaPartidasTorneosToCollectionIfMissing([], null, fichaPartidasTorneos, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fichaPartidasTorneos);
      });

      it('should return initial array if no FichaPartidasTorneos is added', () => {
        const fichaPartidasTorneosCollection: IFichaPartidasTorneos[] = [sampleWithRequiredData];
        expectedResult = service.addFichaPartidasTorneosToCollectionIfMissing(fichaPartidasTorneosCollection, undefined, null);
        expect(expectedResult).toEqual(fichaPartidasTorneosCollection);
      });
    });

    describe('compareFichaPartidasTorneos', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFichaPartidasTorneos(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFichaPartidasTorneos(entity1, entity2);
        const compareResult2 = service.compareFichaPartidasTorneos(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFichaPartidasTorneos(entity1, entity2);
        const compareResult2 = service.compareFichaPartidasTorneos(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFichaPartidasTorneos(entity1, entity2);
        const compareResult2 = service.compareFichaPartidasTorneos(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
