import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EoloTestTestModule } from '../../../test.module';
import { InvitoFrontendComponent } from 'app/entities/invito-frontend/invito-frontend.component';
import { InvitoFrontendService } from 'app/entities/invito-frontend/invito-frontend.service';
import { InvitoFrontend } from 'app/shared/model/invito-frontend.model';

describe('Component Tests', () => {
  describe('InvitoFrontend Management Component', () => {
    let comp: InvitoFrontendComponent;
    let fixture: ComponentFixture<InvitoFrontendComponent>;
    let service: InvitoFrontendService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EoloTestTestModule],
        declarations: [InvitoFrontendComponent]
      })
        .overrideTemplate(InvitoFrontendComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InvitoFrontendComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InvitoFrontendService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new InvitoFrontend(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.invitos && comp.invitos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
