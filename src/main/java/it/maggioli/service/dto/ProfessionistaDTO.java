package it.maggioli.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link it.maggioli.domain.Professionista} entity.
 */
public class ProfessionistaDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Integer idProfessionista;

    private String nome;

    private String cognome;

    private String tipologia;

    @NotNull
    private String codiceFiscale;

    private String pIva;

    private String studioAssociato;

    private Integer idLicenza;


    private Long idProfessionistaId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdProfessionista() {
        return idProfessionista;
    }

    public void setIdProfessionista(Integer idProfessionista) {
        this.idProfessionista = idProfessionista;
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

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getpIva() {
        return pIva;
    }

    public void setpIva(String pIva) {
        this.pIva = pIva;
    }

    public String getStudioAssociato() {
        return studioAssociato;
    }

    public void setStudioAssociato(String studioAssociato) {
        this.studioAssociato = studioAssociato;
    }

    public Integer getIdLicenza() {
        return idLicenza;
    }

    public void setIdLicenza(Integer idLicenza) {
        this.idLicenza = idLicenza;
    }

    public Long getIdProfessionistaId() {
        return idProfessionistaId;
    }

    public void setIdProfessionistaId(Long storageCloudId) {
        this.idProfessionistaId = storageCloudId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProfessionistaDTO professionistaDTO = (ProfessionistaDTO) o;
        if (professionistaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), professionistaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProfessionistaDTO{" +
            "id=" + getId() +
            ", idProfessionista=" + getIdProfessionista() +
            ", nome='" + getNome() + "'" +
            ", cognome='" + getCognome() + "'" +
            ", tipologia='" + getTipologia() + "'" +
            ", codiceFiscale='" + getCodiceFiscale() + "'" +
            ", pIva='" + getpIva() + "'" +
            ", studioAssociato='" + getStudioAssociato() + "'" +
            ", idLicenza=" + getIdLicenza() +
            ", idProfessionistaId=" + getIdProfessionistaId() +
            "}";
    }
}
