import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMallaCurricular } from '../malla-curricular.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../malla-curricular.test-samples';

import { MallaCurricularService } from './malla-curricular.service';

const requireRestSample: IMallaCurricular = {
  ...sampleWithRequiredData,
};

describe('MallaCurricular Service', () => {
  let service: MallaCurricularService;
  let httpMock: HttpTestingController;
  let expectedResult: IMallaCurricular | IMallaCurricular[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MallaCurricularService);
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

    it('should create a MallaCurricular', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const mallaCurricular = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(mallaCurricular).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MallaCurricular', () => {
      const mallaCurricular = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(mallaCurricular).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MallaCurricular', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MallaCurricular', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MallaCurricular', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMallaCurricularToCollectionIfMissing', () => {
      it('should add a MallaCurricular to an empty array', () => {
        const mallaCurricular: IMallaCurricular = sampleWithRequiredData;
        expectedResult = service.addMallaCurricularToCollectionIfMissing([], mallaCurricular);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mallaCurricular);
      });

      it('should not add a MallaCurricular to an array that contains it', () => {
        const mallaCurricular: IMallaCurricular = sampleWithRequiredData;
        const mallaCurricularCollection: IMallaCurricular[] = [
          {
            ...mallaCurricular,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMallaCurricularToCollectionIfMissing(mallaCurricularCollection, mallaCurricular);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MallaCurricular to an array that doesn't contain it", () => {
        const mallaCurricular: IMallaCurricular = sampleWithRequiredData;
        const mallaCurricularCollection: IMallaCurricular[] = [sampleWithPartialData];
        expectedResult = service.addMallaCurricularToCollectionIfMissing(mallaCurricularCollection, mallaCurricular);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mallaCurricular);
      });

      it('should add only unique MallaCurricular to an array', () => {
        const mallaCurricularArray: IMallaCurricular[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const mallaCurricularCollection: IMallaCurricular[] = [sampleWithRequiredData];
        expectedResult = service.addMallaCurricularToCollectionIfMissing(mallaCurricularCollection, ...mallaCurricularArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const mallaCurricular: IMallaCurricular = sampleWithRequiredData;
        const mallaCurricular2: IMallaCurricular = sampleWithPartialData;
        expectedResult = service.addMallaCurricularToCollectionIfMissing([], mallaCurricular, mallaCurricular2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mallaCurricular);
        expect(expectedResult).toContain(mallaCurricular2);
      });

      it('should accept null and undefined values', () => {
        const mallaCurricular: IMallaCurricular = sampleWithRequiredData;
        expectedResult = service.addMallaCurricularToCollectionIfMissing([], null, mallaCurricular, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mallaCurricular);
      });

      it('should return initial array if no MallaCurricular is added', () => {
        const mallaCurricularCollection: IMallaCurricular[] = [sampleWithRequiredData];
        expectedResult = service.addMallaCurricularToCollectionIfMissing(mallaCurricularCollection, undefined, null);
        expect(expectedResult).toEqual(mallaCurricularCollection);
      });
    });

    describe('compareMallaCurricular', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMallaCurricular(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMallaCurricular(entity1, entity2);
        const compareResult2 = service.compareMallaCurricular(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMallaCurricular(entity1, entity2);
        const compareResult2 = service.compareMallaCurricular(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMallaCurricular(entity1, entity2);
        const compareResult2 = service.compareMallaCurricular(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
