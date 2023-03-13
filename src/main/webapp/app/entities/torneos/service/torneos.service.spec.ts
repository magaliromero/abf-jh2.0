import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ITorneos } from '../torneos.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../torneos.test-samples';

import { TorneosService, RestTorneos } from './torneos.service';

const requireRestSample: RestTorneos = {
  ...sampleWithRequiredData,
  fechaInicio: sampleWithRequiredData.fechaInicio?.format(DATE_FORMAT),
  fechaFin: sampleWithRequiredData.fechaFin?.format(DATE_FORMAT),
};

describe('Torneos Service', () => {
  let service: TorneosService;
  let httpMock: HttpTestingController;
  let expectedResult: ITorneos | ITorneos[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TorneosService);
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

    it('should create a Torneos', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const torneos = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(torneos).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Torneos', () => {
      const torneos = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(torneos).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Torneos', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Torneos', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Torneos', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTorneosToCollectionIfMissing', () => {
      it('should add a Torneos to an empty array', () => {
        const torneos: ITorneos = sampleWithRequiredData;
        expectedResult = service.addTorneosToCollectionIfMissing([], torneos);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(torneos);
      });

      it('should not add a Torneos to an array that contains it', () => {
        const torneos: ITorneos = sampleWithRequiredData;
        const torneosCollection: ITorneos[] = [
          {
            ...torneos,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTorneosToCollectionIfMissing(torneosCollection, torneos);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Torneos to an array that doesn't contain it", () => {
        const torneos: ITorneos = sampleWithRequiredData;
        const torneosCollection: ITorneos[] = [sampleWithPartialData];
        expectedResult = service.addTorneosToCollectionIfMissing(torneosCollection, torneos);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(torneos);
      });

      it('should add only unique Torneos to an array', () => {
        const torneosArray: ITorneos[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const torneosCollection: ITorneos[] = [sampleWithRequiredData];
        expectedResult = service.addTorneosToCollectionIfMissing(torneosCollection, ...torneosArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const torneos: ITorneos = sampleWithRequiredData;
        const torneos2: ITorneos = sampleWithPartialData;
        expectedResult = service.addTorneosToCollectionIfMissing([], torneos, torneos2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(torneos);
        expect(expectedResult).toContain(torneos2);
      });

      it('should accept null and undefined values', () => {
        const torneos: ITorneos = sampleWithRequiredData;
        expectedResult = service.addTorneosToCollectionIfMissing([], null, torneos, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(torneos);
      });

      it('should return initial array if no Torneos is added', () => {
        const torneosCollection: ITorneos[] = [sampleWithRequiredData];
        expectedResult = service.addTorneosToCollectionIfMissing(torneosCollection, undefined, null);
        expect(expectedResult).toEqual(torneosCollection);
      });
    });

    describe('compareTorneos', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTorneos(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTorneos(entity1, entity2);
        const compareResult2 = service.compareTorneos(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTorneos(entity1, entity2);
        const compareResult2 = service.compareTorneos(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTorneos(entity1, entity2);
        const compareResult2 = service.compareTorneos(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
