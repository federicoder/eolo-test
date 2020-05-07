import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IClienteFrontend, ClienteFrontend } from 'app/shared/model/cliente-frontend.model';
import { ClienteFrontendService } from './cliente-frontend.service';

@Component({
  selector: 'jhi-cliente-frontend-update',
  templateUrl: './cliente-frontend-update.component.html'
})
export class ClienteFrontendUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    idCliente: [null, [Validators.required]],
    nome: [],
    cognome: [],
    idPraticaConnessa: [null, [Validators.required]]
  });

  constructor(protected clienteService: ClienteFrontendService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cliente }) => {
      this.updateForm(cliente);
    });
  }

  updateForm(cliente: IClienteFrontend): void {
    this.editForm.patchValue({
      id: cliente.id,
      idCliente: cliente.idCliente,
      nome: cliente.nome,
      cognome: cliente.cognome,
      idPraticaConnessa: cliente.idPraticaConnessa
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cliente = this.createFromForm();
    if (cliente.id !== undefined) {
      this.subscribeToSaveResponse(this.clienteService.update(cliente));
    } else {
      this.subscribeToSaveResponse(this.clienteService.create(cliente));
    }
  }

  private createFromForm(): IClienteFrontend {
    return {
      ...new ClienteFrontend(),
      id: this.editForm.get(['id'])!.value,
      idCliente: this.editForm.get(['idCliente'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      cognome: this.editForm.get(['cognome'])!.value,
      idPraticaConnessa: this.editForm.get(['idPraticaConnessa'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClienteFrontend>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
