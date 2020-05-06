import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PraticaFrontendService } from 'app/entities/pratica-frontend/pratica-frontend.service';
import { IPraticaFrontend, PraticaFrontend } from 'app/shared/model/pratica-frontend.model';

describe('Service Tests', () => {
  describe('PraticaFrontend Service', () => {
    let injector: TestBed;
    let service: PraticaFrontendService;
    let httpMock: HttpTestingController;
    let elemDefault: IPraticaFrontend;
    let expectedResult: IPraticaFrontend | IPraticaFrontend[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PraticaFrontendService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new PraticaFrontend(0, 0, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PraticaFrontend', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new PraticaFrontend()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PraticaFrontend', () => {
        const returnedFromService = Object.assign(
          {
            idPratica: 1,
            idLic: 1,
            tdp: 1,
            idCollab: 1,
            idClient: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PraticaFrontend', () => {
        const returnedFromService = Object.assign(
          {
            idPratica: 1,
            idLic: 1,
            tdp: 1,
            idCollab: 1,
            idClient: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PraticaFrontend', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
