import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EoloTestTestModule } from '../../../test.module';
import { ProfessionistaFrontendDetailComponent } from 'app/entities/professionista-frontend/professionista-frontend-detail.component';
import { ProfessionistaFrontend } from 'app/shared/model/professionista-frontend.model';

describe('Component Tests', () => {
  describe('ProfessionistaFrontend Management Detail Component', () => {
    let comp: ProfessionistaFrontendDetailComponent;
    let fixture: ComponentFixture<ProfessionistaFrontendDetailComponent>;
    const route = ({ data: of({ professionista: new ProfessionistaFrontend(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EoloTestTestModule],
        declarations: [ProfessionistaFrontendDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProfessionistaFrontendDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProfessionistaFrontendDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load professionista on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.professionista).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
