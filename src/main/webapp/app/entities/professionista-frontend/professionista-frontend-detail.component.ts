import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProfessionistaFrontend } from 'app/shared/model/professionista-frontend.model';

@Component({
  selector: 'jhi-professionista-frontend-detail',
  templateUrl: './professionista-frontend-detail.component.html'
})
export class ProfessionistaFrontendDetailComponent implements OnInit {
  professionista: IProfessionistaFrontend | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ professionista }) => (this.professionista = professionista));
  }

  previousState(): void {
    window.history.back();
  }
}
