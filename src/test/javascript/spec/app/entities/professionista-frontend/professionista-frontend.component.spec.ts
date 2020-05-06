import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { EoloTestTestModule } from '../../../test.module';
import { ProfessionistaFrontendComponent } from 'app/entities/professionista-frontend/professionista-frontend.component';
import { ProfessionistaFrontendService } from 'app/entities/professionista-frontend/professionista-frontend.service';
import { ProfessionistaFrontend } from 'app/shared/model/professionista-frontend.model';

describe('Component Tests', () => {
  describe('ProfessionistaFrontend Management Component', () => {
    let comp: ProfessionistaFrontendComponent;
    let fixture: ComponentFixture<ProfessionistaFrontendComponent>;
    let service: ProfessionistaFrontendService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EoloTestTestModule],
        declarations: [ProfessionistaFrontendComponent],
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
        .overrideTemplate(ProfessionistaFrontendComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProfessionistaFrontendComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfessionistaFrontendService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProfessionistaFrontend(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.professionistas && comp.professionistas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProfessionistaFrontend(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.professionistas && comp.professionistas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
