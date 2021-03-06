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
 * A Licenza.
 */
@Entity
@Table(name = "licenza")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "licenza")
public class Licenza implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_licenza", nullable = false)
    private Integer idLicenza;

    @Column(name = "tipologia")
    private String tipologia;

    @Column(name = "descrizione")
    private String descrizione;

    @Column(name = "data_scadenza")
    private LocalDate dataScadenza;

    @OneToOne
    @JoinColumn(unique = true)
    private Professionista professionista;

    @OneToOne
    @JoinColumn(unique = true)
    private StorageCloud storageCloud;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdLicenza() {
        return idLicenza;
    }

    public Licenza idLicenza(Integer idLicenza) {
        this.idLicenza = idLicenza;
        return this;
    }

    public void setIdLicenza(Integer idLicenza) {
        this.idLicenza = idLicenza;
    }

    public String getTipologia() {
        return tipologia;
    }

    public Licenza tipologia(String tipologia) {
        this.tipologia = tipologia;
        return this;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public Licenza descrizione(String descrizione) {
        this.descrizione = descrizione;
        return this;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public Licenza dataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
        return this;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public Professionista getProfessionista() {
        return professionista;
    }

    public Licenza professionista(Professionista professionista) {
        this.professionista = professionista;
        return this;
    }

    public void setProfessionista(Professionista professionista) {
        this.professionista = professionista;
    }

    public StorageCloud getStorageCloud() {
        return storageCloud;
    }

    public Licenza storageCloud(StorageCloud storageCloud) {
        this.storageCloud = storageCloud;
        return this;
    }

    public void setStorageCloud(StorageCloud storageCloud) {
        this.storageCloud = storageCloud;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Licenza)) {
            return false;
        }
        return id != null && id.equals(((Licenza) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Licenza{" +
            "id=" + getId() +
            ", idLicenza=" + getIdLicenza() +
            ", tipologia='" + getTipologia() + "'" +
            ", descrizione='" + getDescrizione() + "'" +
            ", dataScadenza='" + getDataScadenza() + "'" +
            "}";
    }
}
