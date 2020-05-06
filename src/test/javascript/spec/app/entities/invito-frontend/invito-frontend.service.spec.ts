import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { InvitoFrontendService } from 'app/entities/invito-frontend/invito-frontend.service';
import { IInvitoFrontend, InvitoFrontend } from 'app/shared/model/invito-frontend.model';

describe('Service Tests', () => {
  describe('InvitoFrontend Service', () => {
    let injector: TestBed;
    let service: InvitoFrontendService;
    let httpMock: HttpTestingController;
    let elemDefault: IInvitoFrontend;
    let expectedResult: IInvitoFrontend | IInvitoFrontend[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(InvitoFrontendService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new InvitoFrontend(0, false, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a InvitoFrontend', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new InvitoFrontend()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a InvitoFrontend', () => {
        const returnedFromService = Object.assign(
          {
            utenteIscritto: true,
            idUtente: 1,
            idPratica: 1,
            idInvito: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of InvitoFrontend', () => {
        const returnedFromService = Object.assign(
          {
            utenteIscritto: true,
            idUtente: 1,
            idPratica: 1,
            idInvito: 1
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

      it('should delete a InvitoFrontend', () => {
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
