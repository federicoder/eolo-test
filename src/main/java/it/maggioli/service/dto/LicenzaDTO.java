package it.maggioli.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link it.maggioli.domain.Licenza} entity.
 */
public class LicenzaDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Integer idLicenza;

    private String tipologia;

    private String descrizione;

    private LocalDate dataScadenza;


    private Long professionistaId;

    private Long storageCloudId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdLicenza() {
        return idLicenza;
    }

    public void setIdLicenza(Integer idLicenza) {
        this.idLicenza = idLicenza;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public Long getProfessionistaId() {
        return professionistaId;
    }

    public void setProfessionistaId(Long professionistaId) {
        this.professionistaId = professionistaId;
    }

    public Long getStorageCloudId() {
        return storageCloudId;
    }

    public void setStorageCloudId(Long storageCloudId) {
        this.storageCloudId = storageCloudId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LicenzaDTO licenzaDTO = (LicenzaDTO) o;
        if (licenzaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), licenzaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LicenzaDTO{" +
            "id=" + getId() +
            ", idLicenza=" + getIdLicenza() +
            ", tipologia='" + getTipologia() + "'" +
            ", descrizione='" + getDescrizione() + "'" +
            ", dataScadenza='" + getDataScadenza() + "'" +
            ", professionistaId=" + getProfessionistaId() +
            ", storageCloudId=" + getStorageCloudId() +
            "}";
    }
}
