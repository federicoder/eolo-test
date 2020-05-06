import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EoloTestTestModule } from '../../../test.module';
import { LicenzaFrontendUpdateComponent } from 'app/entities/licenza-frontend/licenza-frontend-update.component';
import { LicenzaFrontendService } from 'app/entities/licenza-frontend/licenza-frontend.service';
import { LicenzaFrontend } from 'app/shared/model/licenza-frontend.model';

describe('Component Tests', () => {
  describe('LicenzaFrontend Management Update Component', () => {
    let comp: LicenzaFrontendUpdateComponent;
    let fixture: ComponentFixture<LicenzaFrontendUpdateComponent>;
    let service: LicenzaFrontendService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EoloTestTestModule],
        declarations: [LicenzaFrontendUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LicenzaFrontendUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LicenzaFrontendUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LicenzaFrontendService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LicenzaFrontend(123);
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
        const entity = new LicenzaFrontend();
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
