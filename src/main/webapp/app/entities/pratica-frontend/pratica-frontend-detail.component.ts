import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPraticaFrontend } from 'app/shared/model/pratica-frontend.model';

@Component({
  selector: 'jhi-pratica-frontend-detail',
  templateUrl: './pratica-frontend-detail.component.html'
})
export class PraticaFrontendDetailComponent implements OnInit {
  pratica: IPraticaFrontend | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pratica }) => (this.pratica = pratica));
  }

  previousState(): void {
    window.history.back();
  }
}
