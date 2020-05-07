package it.maggioli.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link it.maggioli.domain.Invito} entity.
 */
public class InvitoDTO implements Serializable {
    
    private Long id;

    private Boolean utenteIscritto;

    private Integer idUtente;

    private Integer idPratica;

    @NotNull
    private Integer idInvito;


    private Long collaboratoreId;

    private Long clienteId;

    private Long praticaId;

    private Long professionistaId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isUtenteIscritto() {
        return utenteIscritto;
    }

    public void setUtenteIscritto(Boolean utenteIscritto) {
        this.utenteIscritto = utenteIscritto;
    }

    public Integer getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Integer idUtente) {
        this.idUtente = idUtente;
    }

    public Integer getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public Integer getIdInvito() {
        return idInvito;
    }

    public void setIdInvito(Integer idInvito) {
        this.idInvito = idInvito;
    }

    public Long getCollaboratoreId() {
        return collaboratoreId;
    }

    public void setCollaboratoreId(Long collaboratoreId) {
        this.collaboratoreId = collaboratoreId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getPraticaId() {
        return praticaId;
    }

    public void setPraticaId(Long praticaId) {
        this.praticaId = praticaId;
    }

    public Long getProfessionistaId() {
        return professionistaId;
    }

    public void setProfessionistaId(Long professionistaId) {
        this.professionistaId = professionistaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InvitoDTO invitoDTO = (InvitoDTO) o;
        if (invitoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), invitoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InvitoDTO{" +
            "id=" + getId() +
            ", utenteIscritto='" + isUtenteIscritto() + "'" +
            ", idUtente=" + getIdUtente() +
            ", idPratica=" + getIdPratica() +
            ", idInvito=" + getIdInvito() +
            ", collaboratoreId=" + getCollaboratoreId() +
            ", clienteId=" + getClienteId() +
            ", praticaId=" + getPraticaId() +
            ", professionistaId=" + getProfessionistaId() +
            "}";
    }
}
