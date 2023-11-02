import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { INotaCredito } from '../nota-credito.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../nota-credito.test-samples';

import { NotaCreditoService, RestNotaCredito } from './nota-credito.service';

const requireRestSample: RestNotaCredito = {
  ...sampleWithRequiredData,
  fecha: sampleWithRequiredData.fecha?.format(DATE_FORMAT),
};

describe('NotaCredito Service', () => {
  let service: NotaCreditoService;
  let httpMock: HttpTestingController;
  let expectedResult: INotaCredito | INotaCredito[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NotaCreditoService);
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

    it('should create a NotaCredito', () => {
      const notaCredito = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(notaCredito).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NotaCredito', () => {
      const notaCredito = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(notaCredito).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a NotaCredito', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of NotaCredito', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a NotaCredito', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addNotaCreditoToCollectionIfMissing', () => {
      it('should add a NotaCredito to an empty array', () => {
        const notaCredito: INotaCredito = sampleWithRequiredData;
        expectedResult = service.addNotaCreditoToCollectionIfMissing([], notaCredito);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(notaCredito);
      });

      it('should not add a NotaCredito to an array that contains it', () => {
        const notaCredito: INotaCredito = sampleWithRequiredData;
        const notaCreditoCollection: INotaCredito[] = [
          {
            ...notaCredito,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addNotaCreditoToCollectionIfMissing(notaCreditoCollection, notaCredito);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NotaCredito to an array that doesn't contain it", () => {
        const notaCredito: INotaCredito = sampleWithRequiredData;
        const notaCreditoCollection: INotaCredito[] = [sampleWithPartialData];
        expectedResult = service.addNotaCreditoToCollectionIfMissing(notaCreditoCollection, notaCredito);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(notaCredito);
      });

      it('should add only unique NotaCredito to an array', () => {
        const notaCreditoArray: INotaCredito[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const notaCreditoCollection: INotaCredito[] = [sampleWithRequiredData];
        expectedResult = service.addNotaCreditoToCollectionIfMissing(notaCreditoCollection, ...notaCreditoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const notaCredito: INotaCredito = sampleWithRequiredData;
        const notaCredito2: INotaCredito = sampleWithPartialData;
        expectedResult = service.addNotaCreditoToCollectionIfMissing([], notaCredito, notaCredito2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(notaCredito);
        expect(expectedResult).toContain(notaCredito2);
      });

      it('should accept null and undefined values', () => {
        const notaCredito: INotaCredito = sampleWithRequiredData;
        expectedResult = service.addNotaCreditoToCollectionIfMissing([], null, notaCredito, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(notaCredito);
      });

      it('should return initial array if no NotaCredito is added', () => {
        const notaCreditoCollection: INotaCredito[] = [sampleWithRequiredData];
        expectedResult = service.addNotaCreditoToCollectionIfMissing(notaCreditoCollection, undefined, null);
        expect(expectedResult).toEqual(notaCreditoCollection);
      });
    });

    describe('compareNotaCredito', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareNotaCredito(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareNotaCredito(entity1, entity2);
        const compareResult2 = service.compareNotaCredito(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareNotaCredito(entity1, entity2);
        const compareResult2 = service.compareNotaCredito(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareNotaCredito(entity1, entity2);
        const compareResult2 = service.compareNotaCredito(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
