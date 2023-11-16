import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INotaCreditoDetalle } from '../nota-credito-detalle.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../nota-credito-detalle.test-samples';

import { NotaCreditoDetalleService } from './nota-credito-detalle.service';

const requireRestSample: INotaCreditoDetalle = {
  ...sampleWithRequiredData,
};

describe('NotaCreditoDetalle Service', () => {
  let service: NotaCreditoDetalleService;
  let httpMock: HttpTestingController;
  let expectedResult: INotaCreditoDetalle | INotaCreditoDetalle[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NotaCreditoDetalleService);
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

    it('should create a NotaCreditoDetalle', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const notaCreditoDetalle = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(notaCreditoDetalle).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NotaCreditoDetalle', () => {
      const notaCreditoDetalle = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(notaCreditoDetalle).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a NotaCreditoDetalle', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of NotaCreditoDetalle', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a NotaCreditoDetalle', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addNotaCreditoDetalleToCollectionIfMissing', () => {
      it('should add a NotaCreditoDetalle to an empty array', () => {
        const notaCreditoDetalle: INotaCreditoDetalle = sampleWithRequiredData;
        expectedResult = service.addNotaCreditoDetalleToCollectionIfMissing([], notaCreditoDetalle);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(notaCreditoDetalle);
      });

      it('should not add a NotaCreditoDetalle to an array that contains it', () => {
        const notaCreditoDetalle: INotaCreditoDetalle = sampleWithRequiredData;
        const notaCreditoDetalleCollection: INotaCreditoDetalle[] = [
          {
            ...notaCreditoDetalle,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addNotaCreditoDetalleToCollectionIfMissing(notaCreditoDetalleCollection, notaCreditoDetalle);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NotaCreditoDetalle to an array that doesn't contain it", () => {
        const notaCreditoDetalle: INotaCreditoDetalle = sampleWithRequiredData;
        const notaCreditoDetalleCollection: INotaCreditoDetalle[] = [sampleWithPartialData];
        expectedResult = service.addNotaCreditoDetalleToCollectionIfMissing(notaCreditoDetalleCollection, notaCreditoDetalle);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(notaCreditoDetalle);
      });

      it('should add only unique NotaCreditoDetalle to an array', () => {
        const notaCreditoDetalleArray: INotaCreditoDetalle[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const notaCreditoDetalleCollection: INotaCreditoDetalle[] = [sampleWithRequiredData];
        expectedResult = service.addNotaCreditoDetalleToCollectionIfMissing(notaCreditoDetalleCollection, ...notaCreditoDetalleArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const notaCreditoDetalle: INotaCreditoDetalle = sampleWithRequiredData;
        const notaCreditoDetalle2: INotaCreditoDetalle = sampleWithPartialData;
        expectedResult = service.addNotaCreditoDetalleToCollectionIfMissing([], notaCreditoDetalle, notaCreditoDetalle2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(notaCreditoDetalle);
        expect(expectedResult).toContain(notaCreditoDetalle2);
      });

      it('should accept null and undefined values', () => {
        const notaCreditoDetalle: INotaCreditoDetalle = sampleWithRequiredData;
        expectedResult = service.addNotaCreditoDetalleToCollectionIfMissing([], null, notaCreditoDetalle, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(notaCreditoDetalle);
      });

      it('should return initial array if no NotaCreditoDetalle is added', () => {
        const notaCreditoDetalleCollection: INotaCreditoDetalle[] = [sampleWithRequiredData];
        expectedResult = service.addNotaCreditoDetalleToCollectionIfMissing(notaCreditoDetalleCollection, undefined, null);
        expect(expectedResult).toEqual(notaCreditoDetalleCollection);
      });
    });

    describe('compareNotaCreditoDetalle', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareNotaCreditoDetalle(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareNotaCreditoDetalle(entity1, entity2);
        const compareResult2 = service.compareNotaCreditoDetalle(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareNotaCreditoDetalle(entity1, entity2);
        const compareResult2 = service.compareNotaCreditoDetalle(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareNotaCreditoDetalle(entity1, entity2);
        const compareResult2 = service.compareNotaCreditoDetalle(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
