import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IRegistroStockMateriales } from '../registro-stock-materiales.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../registro-stock-materiales.test-samples';

import { RegistroStockMaterialesService, RestRegistroStockMateriales } from './registro-stock-materiales.service';

const requireRestSample: RestRegistroStockMateriales = {
  ...sampleWithRequiredData,
  fecha: sampleWithRequiredData.fecha?.format(DATE_FORMAT),
};

describe('RegistroStockMateriales Service', () => {
  let service: RegistroStockMaterialesService;
  let httpMock: HttpTestingController;
  let expectedResult: IRegistroStockMateriales | IRegistroStockMateriales[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RegistroStockMaterialesService);
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

    it('should create a RegistroStockMateriales', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const registroStockMateriales = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(registroStockMateriales).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RegistroStockMateriales', () => {
      const registroStockMateriales = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(registroStockMateriales).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RegistroStockMateriales', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RegistroStockMateriales', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a RegistroStockMateriales', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRegistroStockMaterialesToCollectionIfMissing', () => {
      it('should add a RegistroStockMateriales to an empty array', () => {
        const registroStockMateriales: IRegistroStockMateriales = sampleWithRequiredData;
        expectedResult = service.addRegistroStockMaterialesToCollectionIfMissing([], registroStockMateriales);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(registroStockMateriales);
      });

      it('should not add a RegistroStockMateriales to an array that contains it', () => {
        const registroStockMateriales: IRegistroStockMateriales = sampleWithRequiredData;
        const registroStockMaterialesCollection: IRegistroStockMateriales[] = [
          {
            ...registroStockMateriales,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRegistroStockMaterialesToCollectionIfMissing(
          registroStockMaterialesCollection,
          registroStockMateriales
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RegistroStockMateriales to an array that doesn't contain it", () => {
        const registroStockMateriales: IRegistroStockMateriales = sampleWithRequiredData;
        const registroStockMaterialesCollection: IRegistroStockMateriales[] = [sampleWithPartialData];
        expectedResult = service.addRegistroStockMaterialesToCollectionIfMissing(
          registroStockMaterialesCollection,
          registroStockMateriales
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(registroStockMateriales);
      });

      it('should add only unique RegistroStockMateriales to an array', () => {
        const registroStockMaterialesArray: IRegistroStockMateriales[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const registroStockMaterialesCollection: IRegistroStockMateriales[] = [sampleWithRequiredData];
        expectedResult = service.addRegistroStockMaterialesToCollectionIfMissing(
          registroStockMaterialesCollection,
          ...registroStockMaterialesArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const registroStockMateriales: IRegistroStockMateriales = sampleWithRequiredData;
        const registroStockMateriales2: IRegistroStockMateriales = sampleWithPartialData;
        expectedResult = service.addRegistroStockMaterialesToCollectionIfMissing([], registroStockMateriales, registroStockMateriales2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(registroStockMateriales);
        expect(expectedResult).toContain(registroStockMateriales2);
      });

      it('should accept null and undefined values', () => {
        const registroStockMateriales: IRegistroStockMateriales = sampleWithRequiredData;
        expectedResult = service.addRegistroStockMaterialesToCollectionIfMissing([], null, registroStockMateriales, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(registroStockMateriales);
      });

      it('should return initial array if no RegistroStockMateriales is added', () => {
        const registroStockMaterialesCollection: IRegistroStockMateriales[] = [sampleWithRequiredData];
        expectedResult = service.addRegistroStockMaterialesToCollectionIfMissing(registroStockMaterialesCollection, undefined, null);
        expect(expectedResult).toEqual(registroStockMaterialesCollection);
      });
    });

    describe('compareRegistroStockMateriales', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRegistroStockMateriales(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareRegistroStockMateriales(entity1, entity2);
        const compareResult2 = service.compareRegistroStockMateriales(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareRegistroStockMateriales(entity1, entity2);
        const compareResult2 = service.compareRegistroStockMateriales(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareRegistroStockMateriales(entity1, entity2);
        const compareResult2 = service.compareRegistroStockMateriales(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
