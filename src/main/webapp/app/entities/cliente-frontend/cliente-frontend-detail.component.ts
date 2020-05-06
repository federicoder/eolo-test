import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClienteFrontend } from 'app/shared/model/cliente-frontend.model';

@Component({
  selector: 'jhi-cliente-frontend-detail',
  templateUrl: './cliente-frontend-detail.component.html'
})
export class ClienteFrontendDetailComponent implements OnInit {
  cliente: IClienteFrontend | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cliente }) => (this.cliente = cliente));
  }

  previousState(): void {
    window.history.back();
  }
}
