import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EoloTestTestModule } from '../../../test.module';
import { PraticaFrontendComponent } from 'app/entities/pratica-frontend/pratica-frontend.component';
import { PraticaFrontendService } from 'app/entities/pratica-frontend/pratica-frontend.service';
import { PraticaFrontend } from 'app/shared/model/pratica-frontend.model';

describe('Component Tests', () => {
  describe('PraticaFrontend Management Component', () => {
    let comp: PraticaFrontendComponent;
    let fixture: ComponentFixture<PraticaFrontendComponent>;
    let service: PraticaFrontendService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EoloTestTestModule],
        declarations: [PraticaFrontendComponent]
      })
        .overrideTemplate(PraticaFrontendComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PraticaFrontendComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PraticaFrontendService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PraticaFrontend(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.praticas && comp.praticas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
