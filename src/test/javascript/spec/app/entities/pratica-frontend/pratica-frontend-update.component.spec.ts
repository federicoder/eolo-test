import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EoloTestTestModule } from '../../../test.module';
import { PraticaFrontendUpdateComponent } from 'app/entities/pratica-frontend/pratica-frontend-update.component';
import { PraticaFrontendService } from 'app/entities/pratica-frontend/pratica-frontend.service';
import { PraticaFrontend } from 'app/shared/model/pratica-frontend.model';

describe('Component Tests', () => {
  describe('PraticaFrontend Management Update Component', () => {
    let comp: PraticaFrontendUpdateComponent;
    let fixture: ComponentFixture<PraticaFrontendUpdateComponent>;
    let service: PraticaFrontendService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EoloTestTestModule],
        declarations: [PraticaFrontendUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PraticaFrontendUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PraticaFrontendUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PraticaFrontendService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PraticaFrontend(123);
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
        const entity = new PraticaFrontend();
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
