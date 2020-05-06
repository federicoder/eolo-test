import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EoloTestTestModule } from '../../../test.module';
import { StorageCloudFrontendDetailComponent } from 'app/entities/storage-cloud-frontend/storage-cloud-frontend-detail.component';
import { StorageCloudFrontend } from 'app/shared/model/storage-cloud-frontend.model';

describe('Component Tests', () => {
  describe('StorageCloudFrontend Management Detail Component', () => {
    let comp: StorageCloudFrontendDetailComponent;
    let fixture: ComponentFixture<StorageCloudFrontendDetailComponent>;
    const route = ({ data: of({ storageCloud: new StorageCloudFrontend(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EoloTestTestModule],
        declarations: [StorageCloudFrontendDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(StorageCloudFrontendDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StorageCloudFrontendDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load storageCloud on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.storageCloud).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
