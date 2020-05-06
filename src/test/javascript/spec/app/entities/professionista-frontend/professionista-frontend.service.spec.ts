import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ProfessionistaFrontendService } from 'app/entities/professionista-frontend/professionista-frontend.service';
import { IProfessionistaFrontend, ProfessionistaFrontend } from 'app/shared/model/professionista-frontend.model';

describe('Service Tests', () => {
  describe('ProfessionistaFrontend Service', () => {
    let injector: TestBed;
    let service: ProfessionistaFrontendService;
    let httpMock: HttpTestingController;
    let elemDefault: IProfessionistaFrontend;
    let expectedResult: IProfessionistaFrontend | IProfessionistaFrontend[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ProfessionistaFrontendService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ProfessionistaFrontend(0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ProfessionistaFrontend', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ProfessionistaFrontend()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ProfessionistaFrontend', () => {
        const returnedFromService = Object.assign(
          {
            idProfessionista: 1,
            nome: 'BBBBBB',
            cognome: 'BBBBBB',
            tipologia: 'BBBBBB',
            codiceFiscale: 'BBBBBB',
            pIva: 'BBBBBB',
            studioAssociato: 'BBBBBB',
            idLicenza: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ProfessionistaFrontend', () => {
        const returnedFromService = Object.assign(
          {
            idProfessionista: 1,
            nome: 'BBBBBB',
            cognome: 'BBBBBB',
            tipologia: 'BBBBBB',
            codiceFiscale: 'BBBBBB',
            pIva: 'BBBBBB',
            studioAssociato: 'BBBBBB',
            idLicenza: 1
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

      it('should delete a ProfessionistaFrontend', () => {
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
