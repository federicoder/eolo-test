import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { EoloTestTestModule } from '../../../test.module';
import { CollaboratoreFrontendComponent } from 'app/entities/collaboratore-frontend/collaboratore-frontend.component';
import { CollaboratoreFrontendService } from 'app/entities/collaboratore-frontend/collaboratore-frontend.service';
import { CollaboratoreFrontend } from 'app/shared/model/collaboratore-frontend.model';

describe('Component Tests', () => {
  describe('CollaboratoreFrontend Management Component', () => {
    let comp: CollaboratoreFrontendComponent;
    let fixture: ComponentFixture<CollaboratoreFrontendComponent>;
    let service: CollaboratoreFrontendService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EoloTestTestModule],
        declarations: [CollaboratoreFrontendComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(CollaboratoreFrontendComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CollaboratoreFrontendComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CollaboratoreFrontendService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CollaboratoreFrontend(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.collaboratores && comp.collaboratores[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CollaboratoreFrontend(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.collaboratores && comp.collaboratores[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
