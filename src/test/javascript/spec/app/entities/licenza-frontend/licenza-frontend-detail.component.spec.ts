import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EoloTestTestModule } from '../../../test.module';
import { LicenzaFrontendDetailComponent } from 'app/entities/licenza-frontend/licenza-frontend-detail.component';
import { LicenzaFrontend } from 'app/shared/model/licenza-frontend.model';

describe('Component Tests', () => {
  describe('LicenzaFrontend Management Detail Component', () => {
    let comp: LicenzaFrontendDetailComponent;
    let fixture: ComponentFixture<LicenzaFrontendDetailComponent>;
    const route = ({ data: of({ licenza: new LicenzaFrontend(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EoloTestTestModule],
        declarations: [LicenzaFrontendDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LicenzaFrontendDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LicenzaFrontendDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load licenza on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.licenza).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
