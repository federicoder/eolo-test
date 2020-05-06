import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EoloTestTestModule } from '../../../test.module';
import { PraticaFrontendDetailComponent } from 'app/entities/pratica-frontend/pratica-frontend-detail.component';
import { PraticaFrontend } from 'app/shared/model/pratica-frontend.model';

describe('Component Tests', () => {
  describe('PraticaFrontend Management Detail Component', () => {
    let comp: PraticaFrontendDetailComponent;
    let fixture: ComponentFixture<PraticaFrontendDetailComponent>;
    const route = ({ data: of({ pratica: new PraticaFrontend(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EoloTestTestModule],
        declarations: [PraticaFrontendDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PraticaFrontendDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PraticaFrontendDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pratica on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pratica).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
