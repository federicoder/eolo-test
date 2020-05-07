package it.maggioli.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Professionista.
 */
@Entity
@Table(name = "professionista")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "professionista")
public class Professionista implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_professionista", nullable = false)
    private Integer idProfessionista;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cognome")
    private String cognome;

    @Column(name = "tipologia")
    private String tipologia;

    @NotNull
    @Column(name = "codice_fiscale", nullable = false)
    private String codiceFiscale;

    @Column(name = "p_iva")
    private String pIva;

    @Column(name = "studio_associato")
    private String studioAssociato;

    @Column(name = "id_licenza")
    private Integer idLicenza;

    @OneToOne
    @JoinColumn(unique = true)
    private StorageCloud storageCloud;

    @OneToMany(mappedBy = "professionista")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Invito> invitos = new HashSet<>();

    @OneToOne(mappedBy = "professionista")
    @JsonIgnore
    private Licenza licenza;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdProfessionista() {
        return idProfessionista;
    }

    public Professionista idProfessionista(Integer idProfessionista) {
        this.idProfessionista = idProfessionista;
        return this;
    }

    public void setIdProfessionista(Integer idProfessionista) {
        this.idProfessionista = idProfessionista;
    }

    public String getNome() {
        return nome;
    }

    public Professionista nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public Professionista cognome(String cognome) {
        this.cognome = cognome;
        return this;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getTipologia() {
        return tipologia;
    }

    public Professionista tipologia(String tipologia) {
        this.tipologia = tipologia;
        return this;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public Professionista codiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
        return this;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getpIva() {
        return pIva;
    }

    public Professionista pIva(String pIva) {
        this.pIva = pIva;
        return this;
    }

    public void setpIva(String pIva) {
        this.pIva = pIva;
    }

    public String getStudioAssociato() {
        return studioAssociato;
    }

    public Professionista studioAssociato(String studioAssociato) {
        this.studioAssociato = studioAssociato;
        return this;
    }

    public void setStudioAssociato(String studioAssociato) {
        this.studioAssociato = studioAssociato;
    }

    public Integer getIdLicenza() {
        return idLicenza;
    }

    public Professionista idLicenza(Integer idLicenza) {
        this.idLicenza = idLicenza;
        return this;
    }

    public void setIdLicenza(Integer idLicenza) {
        this.idLicenza = idLicenza;
    }

    public StorageCloud getStorageCloud() {
        return storageCloud;
    }

    public Professionista storageCloud(StorageCloud storageCloud) {
        this.storageCloud = storageCloud;
        return this;
    }

    public void setStorageCloud(StorageCloud storageCloud) {
        this.storageCloud = storageCloud;
    }

    public Set<Invito> getInvitos() {
        return invitos;
    }

    public Professionista invitos(Set<Invito> invitos) {
        this.invitos = invitos;
        return this;
    }

    public Professionista addInvito(Invito invito) {
        this.invitos.add(invito);
        invito.setProfessionista(this);
        return this;
    }

    public Professionista removeInvito(Invito invito) {
        this.invitos.remove(invito);
        invito.setProfessionista(null);
        return this;
    }

    public void setInvitos(Set<Invito> invitos) {
        this.invitos = invitos;
    }

    public Licenza getLicenza() {
        return licenza;
    }

    public Professionista licenza(Licenza licenza) {
        this.licenza = licenza;
        return this;
    }

    public void setLicenza(Licenza licenza) {
        this.licenza = licenza;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Professionista)) {
            return false;
        }
        return id != null && id.equals(((Professionista) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Professionista{" +
            "id=" + getId() +
            ", idProfessionista=" + getIdProfessionista() +
            ", nome='" + getNome() + "'" +
            ", cognome='" + getCognome() + "'" +
            ", tipologia='" + getTipologia() + "'" +
            ", codiceFiscale='" + getCodiceFiscale() + "'" +
            ", pIva='" + getpIva() + "'" +
            ", studioAssociato='" + getStudioAssociato() + "'" +
            ", idLicenza=" + getIdLicenza() +
            "}";
    }
}
