import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILicenzaFrontend } from 'app/shared/model/licenza-frontend.model';

@Component({
  selector: 'jhi-licenza-frontend-detail',
  templateUrl: './licenza-frontend-detail.component.html'
})
export class LicenzaFrontendDetailComponent implements OnInit {
  licenza: ILicenzaFrontend | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ licenza }) => (this.licenza = licenza));
  }

  previousState(): void {
    window.history.back();
  }
}
