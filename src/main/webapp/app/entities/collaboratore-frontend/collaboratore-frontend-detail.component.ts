import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICollaboratoreFrontend } from 'app/shared/model/collaboratore-frontend.model';

@Component({
  selector: 'jhi-collaboratore-frontend-detail',
  templateUrl: './collaboratore-frontend-detail.component.html'
})
export class CollaboratoreFrontendDetailComponent implements OnInit {
  collaboratore: ICollaboratoreFrontend | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ collaboratore }) => (this.collaboratore = collaboratore));
  }

  previousState(): void {
    window.history.back();
  }
}
