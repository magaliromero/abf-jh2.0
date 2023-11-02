import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IMatricula } from '../matricula.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../matricula.test-samples';

import { MatriculaService, RestMatricula } from './matricula.service';

const requireRestSample: RestMatricula = {
  ...sampleWithRequiredData,
  fechaInscripcion: sampleWithRequiredData.fechaInscripcion?.format(DATE_FORMAT),
  fechaInicio: sampleWithRequiredData.fechaInicio?.format(DATE_FORMAT),
  fechaPago: sampleWithRequiredData.fechaPago?.format(DATE_FORMAT),
};

describe('Matricula Service', () => {
  let service: MatriculaService;
  let httpMock: HttpTestingController;
  let expectedResult: IMatricula | IMatricula[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MatriculaService);
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

    it('should create a Matricula', () => {
      const matricula = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(matricula).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Matricula', () => {
      const matricula = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(matricula).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Matricula', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Matricula', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Matricula', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMatriculaToCollectionIfMissing', () => {
      it('should add a Matricula to an empty array', () => {
        const matricula: IMatricula = sampleWithRequiredData;
        expectedResult = service.addMatriculaToCollectionIfMissing([], matricula);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(matricula);
      });

      it('should not add a Matricula to an array that contains it', () => {
        const matricula: IMatricula = sampleWithRequiredData;
        const matriculaCollection: IMatricula[] = [
          {
            ...matricula,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMatriculaToCollectionIfMissing(matriculaCollection, matricula);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Matricula to an array that doesn't contain it", () => {
        const matricula: IMatricula = sampleWithRequiredData;
        const matriculaCollection: IMatricula[] = [sampleWithPartialData];
        expectedResult = service.addMatriculaToCollectionIfMissing(matriculaCollection, matricula);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(matricula);
      });

      it('should add only unique Matricula to an array', () => {
        const matriculaArray: IMatricula[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const matriculaCollection: IMatricula[] = [sampleWithRequiredData];
        expectedResult = service.addMatriculaToCollectionIfMissing(matriculaCollection, ...matriculaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const matricula: IMatricula = sampleWithRequiredData;
        const matricula2: IMatricula = sampleWithPartialData;
        expectedResult = service.addMatriculaToCollectionIfMissing([], matricula, matricula2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(matricula);
        expect(expectedResult).toContain(matricula2);
      });

      it('should accept null and undefined values', () => {
        const matricula: IMatricula = sampleWithRequiredData;
        expectedResult = service.addMatriculaToCollectionIfMissing([], null, matricula, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(matricula);
      });

      it('should return initial array if no Matricula is added', () => {
        const matriculaCollection: IMatricula[] = [sampleWithRequiredData];
        expectedResult = service.addMatriculaToCollectionIfMissing(matriculaCollection, undefined, null);
        expect(expectedResult).toEqual(matriculaCollection);
      });
    });

    describe('compareMatricula', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMatricula(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMatricula(entity1, entity2);
        const compareResult2 = service.compareMatricula(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMatricula(entity1, entity2);
        const compareResult2 = service.compareMatricula(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMatricula(entity1, entity2);
        const compareResult2 = service.compareMatricula(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
