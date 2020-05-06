package it.maggioli.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

/**
 * A StorageCloud.
 */
@Entity
@Table(name = "storage_cloud")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "storagecloud")
public class StorageCloud implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_utente", nullable = false)
    private Integer idUtente;

    @NotNull
    @Column(name = "id_licenza", nullable = false)
    private Integer idLicenza;

    @Column(name = "piano_base")
    private String pianoBase;

    @Column(name = "data_cessione")
    private LocalDate dataCessione;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdUtente() {
        return idUtente;
    }

    public StorageCloud idUtente(Integer idUtente) {
        this.idUtente = idUtente;
        return this;
    }

    public void setIdUtente(Integer idUtente) {
        this.idUtente = idUtente;
    }

    public Integer getIdLicenza() {
        return idLicenza;
    }

    public StorageCloud idLicenza(Integer idLicenza) {
        this.idLicenza = idLicenza;
        return this;
    }

    public void setIdLicenza(Integer idLicenza) {
        this.idLicenza = idLicenza;
    }

    public String getPianoBase() {
        return pianoBase;
    }

    public StorageCloud pianoBase(String pianoBase) {
        this.pianoBase = pianoBase;
        return this;
    }

    public void setPianoBase(String pianoBase) {
        this.pianoBase = pianoBase;
    }

    public LocalDate getDataCessione() {
        return dataCessione;
    }

    public StorageCloud dataCessione(LocalDate dataCessione) {
        this.dataCessione = dataCessione;
        return this;
    }

    public void setDataCessione(LocalDate dataCessione) {
        this.dataCessione = dataCessione;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StorageCloud)) {
            return false;
        }
        return id != null && id.equals(((StorageCloud) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StorageCloud{" +
            "id=" + getId() +
            ", idUtente=" + getIdUtente() +
            ", idLicenza=" + getIdLicenza() +
            ", pianoBase='" + getPianoBase() + "'" +
            ", dataCessione='" + getDataCessione() + "'" +
            "}";
    }
}
