import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInvitoFrontend } from 'app/shared/model/invito-frontend.model';

@Component({
  selector: 'jhi-invito-frontend-detail',
  templateUrl: './invito-frontend-detail.component.html'
})
export class InvitoFrontendDetailComponent implements OnInit {
  invito: IInvitoFrontend | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invito }) => (this.invito = invito));
  }

  previousState(): void {
    window.history.back();
  }
}
