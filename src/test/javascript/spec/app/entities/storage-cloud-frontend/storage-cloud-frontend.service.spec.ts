import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { StorageCloudFrontendService } from 'app/entities/storage-cloud-frontend/storage-cloud-frontend.service';
import { IStorageCloudFrontend, StorageCloudFrontend } from 'app/shared/model/storage-cloud-frontend.model';

describe('Service Tests', () => {
  describe('StorageCloudFrontend Service', () => {
    let injector: TestBed;
    let service: StorageCloudFrontendService;
    let httpMock: HttpTestingController;
    let elemDefault: IStorageCloudFrontend;
    let expectedResult: IStorageCloudFrontend | IStorageCloudFrontend[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(StorageCloudFrontendService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new StorageCloudFrontend(0, 0, 0, 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataCessione: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a StorageCloudFrontend', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataCessione: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataCessione: currentDate
          },
          returnedFromService
        );

        service.create(new StorageCloudFrontend()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a StorageCloudFrontend', () => {
        const returnedFromService = Object.assign(
          {
            idUtente: 1,
            idLicenza: 1,
            pianoBase: 'BBBBBB',
            dataCessione: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataCessione: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of StorageCloudFrontend', () => {
        const returnedFromService = Object.assign(
          {
            idUtente: 1,
            idLicenza: 1,
            pianoBase: 'BBBBBB',
            dataCessione: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataCessione: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a StorageCloudFrontend', () => {
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
