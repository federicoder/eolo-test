import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EoloTestTestModule } from '../../../test.module';
import { CollaboratoreFrontendDetailComponent } from 'app/entities/collaboratore-frontend/collaboratore-frontend-detail.component';
import { CollaboratoreFrontend } from 'app/shared/model/collaboratore-frontend.model';

describe('Component Tests', () => {
  describe('CollaboratoreFrontend Management Detail Component', () => {
    let comp: CollaboratoreFrontendDetailComponent;
    let fixture: ComponentFixture<CollaboratoreFrontendDetailComponent>;
    const route = ({ data: of({ collaboratore: new CollaboratoreFrontend(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EoloTestTestModule],
        declarations: [CollaboratoreFrontendDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CollaboratoreFrontendDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CollaboratoreFrontendDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load collaboratore on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.collaboratore).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
