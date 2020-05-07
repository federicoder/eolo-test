package it.maggioli.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link it.maggioli.domain.Collaboratore} entity.
 */
public class CollaboratoreDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Integer idCollaboratore;

    private String nome;

    private String cognome;

    private String tipologia;

    @NotNull
    private Integer idPratic;

    private Integer idInvito;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdCollaboratore() {
        return idCollaboratore;
    }

    public void setIdCollaboratore(Integer idCollaboratore) {
        this.idCollaboratore = idCollaboratore;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public Integer getIdPratic() {
        return idPratic;
    }

    public void setIdPratic(Integer idPratic) {
        this.idPratic = idPratic;
    }

    public Integer getIdInvito() {
        return idInvito;
    }

    public void setIdInvito(Integer idInvito) {
        this.idInvito = idInvito;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CollaboratoreDTO collaboratoreDTO = (CollaboratoreDTO) o;
        if (collaboratoreDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), collaboratoreDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CollaboratoreDTO{" +
            "id=" + getId() +
            ", idCollaboratore=" + getIdCollaboratore() +
            ", nome='" + getNome() + "'" +
            ", cognome='" + getCognome() + "'" +
            ", tipologia='" + getTipologia() + "'" +
            ", idPratic=" + getIdPratic() +
            ", idInvito=" + getIdInvito() +
            "}";
    }
}
