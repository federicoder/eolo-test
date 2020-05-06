import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EoloTestTestModule } from '../../../test.module';
import { StorageCloudFrontendUpdateComponent } from 'app/entities/storage-cloud-frontend/storage-cloud-frontend-update.component';
import { StorageCloudFrontendService } from 'app/entities/storage-cloud-frontend/storage-cloud-frontend.service';
import { StorageCloudFrontend } from 'app/shared/model/storage-cloud-frontend.model';

describe('Component Tests', () => {
  describe('StorageCloudFrontend Management Update Component', () => {
    let comp: StorageCloudFrontendUpdateComponent;
    let fixture: ComponentFixture<StorageCloudFrontendUpdateComponent>;
    let service: StorageCloudFrontendService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EoloTestTestModule],
        declarations: [StorageCloudFrontendUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(StorageCloudFrontendUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StorageCloudFrontendUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StorageCloudFrontendService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new StorageCloudFrontend(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new StorageCloudFrontend();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
