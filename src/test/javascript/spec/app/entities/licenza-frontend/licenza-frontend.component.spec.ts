import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EoloTestTestModule } from '../../../test.module';
import { LicenzaFrontendComponent } from 'app/entities/licenza-frontend/licenza-frontend.component';
import { LicenzaFrontendService } from 'app/entities/licenza-frontend/licenza-frontend.service';
import { LicenzaFrontend } from 'app/shared/model/licenza-frontend.model';

describe('Component Tests', () => {
  describe('LicenzaFrontend Management Component', () => {
    let comp: LicenzaFrontendComponent;
    let fixture: ComponentFixture<LicenzaFrontendComponent>;
    let service: LicenzaFrontendService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EoloTestTestModule],
        declarations: [LicenzaFrontendComponent]
      })
        .overrideTemplate(LicenzaFrontendComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LicenzaFrontendComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LicenzaFrontendService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LicenzaFrontend(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.licenzas && comp.licenzas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
