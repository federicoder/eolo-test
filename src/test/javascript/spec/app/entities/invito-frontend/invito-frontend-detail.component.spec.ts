import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EoloTestTestModule } from '../../../test.module';
import { InvitoFrontendDetailComponent } from 'app/entities/invito-frontend/invito-frontend-detail.component';
import { InvitoFrontend } from 'app/shared/model/invito-frontend.model';

describe('Component Tests', () => {
  describe('InvitoFrontend Management Detail Component', () => {
    let comp: InvitoFrontendDetailComponent;
    let fixture: ComponentFixture<InvitoFrontendDetailComponent>;
    const route = ({ data: of({ invito: new InvitoFrontend(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EoloTestTestModule],
        declarations: [InvitoFrontendDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InvitoFrontendDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InvitoFrontendDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load invito on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.invito).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
