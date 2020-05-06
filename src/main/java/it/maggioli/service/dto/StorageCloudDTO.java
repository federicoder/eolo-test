package it.maggioli.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link it.maggioli.domain.StorageCloud} entity.
 */
public class StorageCloudDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Integer idUtente;

    @NotNull
    private Integer idLicenza;

    private String pianoBase;

    private LocalDate dataCessione;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Integer idUtente) {
        this.idUtente = idUtente;
    }

    public Integer getIdLicenza() {
        return idLicenza;
    }

    public void setIdLicenza(Integer idLicenza) {
        this.idLicenza = idLicenza;
    }

    public String getPianoBase() {
        return pianoBase;
    }

    public void setPianoBase(String pianoBase) {
        this.pianoBase = pianoBase;
    }

    public LocalDate getDataCessione() {
        return dataCessione;
    }

    public void setDataCessione(LocalDate dataCessione) {
        this.dataCessione = dataCessione;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StorageCloudDTO storageCloudDTO = (StorageCloudDTO) o;
        if (storageCloudDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), storageCloudDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StorageCloudDTO{" +
            "id=" + getId() +
            ", idUtente=" + getIdUtente() +
            ", idLicenza=" + getIdLicenza() +
            ", pianoBase='" + getPianoBase() + "'" +
            ", dataCessione='" + getDataCessione() + "'" +
            "}";
    }
}
