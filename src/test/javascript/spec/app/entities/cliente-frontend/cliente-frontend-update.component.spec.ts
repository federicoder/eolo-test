import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EoloTestTestModule } from '../../../test.module';
import { ClienteFrontendUpdateComponent } from 'app/entities/cliente-frontend/cliente-frontend-update.component';
import { ClienteFrontendService } from 'app/entities/cliente-frontend/cliente-frontend.service';
import { ClienteFrontend } from 'app/shared/model/cliente-frontend.model';

describe('Component Tests', () => {
  describe('ClienteFrontend Management Update Component', () => {
    let comp: ClienteFrontendUpdateComponent;
    let fixture: ComponentFixture<ClienteFrontendUpdateComponent>;
    let service: ClienteFrontendService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EoloTestTestModule],
        declarations: [ClienteFrontendUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ClienteFrontendUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClienteFrontendUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClienteFrontendService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ClienteFrontend(123);
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
        const entity = new ClienteFrontend();
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
