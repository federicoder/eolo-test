import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EoloTestTestModule } from '../../../test.module';
import { ProfessionistaFrontendUpdateComponent } from 'app/entities/professionista-frontend/professionista-frontend-update.component';
import { ProfessionistaFrontendService } from 'app/entities/professionista-frontend/professionista-frontend.service';
import { ProfessionistaFrontend } from 'app/shared/model/professionista-frontend.model';

describe('Component Tests', () => {
  describe('ProfessionistaFrontend Management Update Component', () => {
    let comp: ProfessionistaFrontendUpdateComponent;
    let fixture: ComponentFixture<ProfessionistaFrontendUpdateComponent>;
    let service: ProfessionistaFrontendService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EoloTestTestModule],
        declarations: [ProfessionistaFrontendUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProfessionistaFrontendUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProfessionistaFrontendUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfessionistaFrontendService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProfessionistaFrontend(123);
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
        const entity = new ProfessionistaFrontend();
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
