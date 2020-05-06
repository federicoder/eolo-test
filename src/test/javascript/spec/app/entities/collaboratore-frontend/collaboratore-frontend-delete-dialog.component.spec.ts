import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EoloTestTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { CollaboratoreFrontendDeleteDialogComponent } from 'app/entities/collaboratore-frontend/collaboratore-frontend-delete-dialog.component';
import { CollaboratoreFrontendService } from 'app/entities/collaboratore-frontend/collaboratore-frontend.service';

describe('Component Tests', () => {
  describe('CollaboratoreFrontend Management Delete Component', () => {
    let comp: CollaboratoreFrontendDeleteDialogComponent;
    let fixture: ComponentFixture<CollaboratoreFrontendDeleteDialogComponent>;
    let service: CollaboratoreFrontendService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EoloTestTestModule],
        declarations: [CollaboratoreFrontendDeleteDialogComponent]
      })
        .overrideTemplate(CollaboratoreFrontendDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CollaboratoreFrontendDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CollaboratoreFrontendService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
