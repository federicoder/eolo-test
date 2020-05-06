import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EoloTestTestModule } from '../../../test.module';
import { ClienteFrontendDetailComponent } from 'app/entities/cliente-frontend/cliente-frontend-detail.component';
import { ClienteFrontend } from 'app/shared/model/cliente-frontend.model';

describe('Component Tests', () => {
  describe('ClienteFrontend Management Detail Component', () => {
    let comp: ClienteFrontendDetailComponent;
    let fixture: ComponentFixture<ClienteFrontendDetailComponent>;
    const route = ({ data: of({ cliente: new ClienteFrontend(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EoloTestTestModule],
        declarations: [ClienteFrontendDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ClienteFrontendDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClienteFrontendDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cliente on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cliente).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
