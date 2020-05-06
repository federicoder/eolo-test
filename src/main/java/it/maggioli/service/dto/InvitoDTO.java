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


    private Long idUtenteId;

    private Long idUtenteId;

    private Long idPraticaId;

    private Long idUtenteId;
    
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

    public Long getIdUtenteId() {
        return idUtenteId;
    }

    public void setIdUtenteId(Long collaboratoreId) {
        this.idUtenteId = collaboratoreId;
    }

    public Long getIdUtenteId() {
        return idUtenteId;
    }

    public void setIdUtenteId(Long clienteId) {
        this.idUtenteId = clienteId;
    }

    public Long getIdPraticaId() {
        return idPraticaId;
    }

    public void setIdPraticaId(Long praticaId) {
        this.idPraticaId = praticaId;
    }

    public Long getIdUtenteId() {
        return idUtenteId;
    }

    public void setIdUtenteId(Long professionistaId) {
        this.idUtenteId = professionistaId;
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
            ", idUtenteId=" + getIdUtenteId() +
            ", idUtenteId=" + getIdUtenteId() +
            ", idPraticaId=" + getIdPraticaId() +
            ", idUtenteId=" + getIdUtenteId() +
            "}";
    }
}
