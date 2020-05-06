import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EoloTestTestModule } from '../../../test.module';
import { CollaboratoreFrontendUpdateComponent } from 'app/entities/collaboratore-frontend/collaboratore-frontend-update.component';
import { CollaboratoreFrontendService } from 'app/entities/collaboratore-frontend/collaboratore-frontend.service';
import { CollaboratoreFrontend } from 'app/shared/model/collaboratore-frontend.model';

describe('Component Tests', () => {
  describe('CollaboratoreFrontend Management Update Component', () => {
    let comp: CollaboratoreFrontendUpdateComponent;
    let fixture: ComponentFixture<CollaboratoreFrontendUpdateComponent>;
    let service: CollaboratoreFrontendService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EoloTestTestModule],
        declarations: [CollaboratoreFrontendUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CollaboratoreFrontendUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CollaboratoreFrontendUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CollaboratoreFrontendService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CollaboratoreFrontend(123);
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
        const entity = new CollaboratoreFrontend();
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
