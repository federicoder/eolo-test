import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EoloTestTestModule } from '../../../test.module';
import { StorageCloudFrontendComponent } from 'app/entities/storage-cloud-frontend/storage-cloud-frontend.component';
import { StorageCloudFrontendService } from 'app/entities/storage-cloud-frontend/storage-cloud-frontend.service';
import { StorageCloudFrontend } from 'app/shared/model/storage-cloud-frontend.model';

describe('Component Tests', () => {
  describe('StorageCloudFrontend Management Component', () => {
    let comp: StorageCloudFrontendComponent;
    let fixture: ComponentFixture<StorageCloudFrontendComponent>;
    let service: StorageCloudFrontendService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EoloTestTestModule],
        declarations: [StorageCloudFrontendComponent]
      })
        .overrideTemplate(StorageCloudFrontendComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StorageCloudFrontendComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StorageCloudFrontendService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new StorageCloudFrontend(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.storageClouds && comp.storageClouds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
