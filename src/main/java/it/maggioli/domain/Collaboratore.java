package it.maggioli.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Collaboratore.
 */
@Entity
@Table(name = "collaboratore")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "collaboratore")
public class Collaboratore implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_collaboratore", nullable = false)
    private Integer idCollaboratore;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cognome")
    private String cognome;

    @Column(name = "tipologia")
    private String tipologia;

    @NotNull
    @Column(name = "id_pratic", nullable = false)
    private Integer idPratic;

    @Column(name = "id_invito")
    private Integer idInvito;

    @OneToOne(mappedBy = "collaboratore")
    @JsonIgnore
    private Invito invito;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdCollaboratore() {
        return idCollaboratore;
    }

    public Collaboratore idCollaboratore(Integer idCollaboratore) {
        this.idCollaboratore = idCollaboratore;
        return this;
    }

    public void setIdCollaboratore(Integer idCollaboratore) {
        this.idCollaboratore = idCollaboratore;
    }

    public String getNome() {
        return nome;
    }

    public Collaboratore nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public Collaboratore cognome(String cognome) {
        this.cognome = cognome;
        return this;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getTipologia() {
        return tipologia;
    }

    public Collaboratore tipologia(String tipologia) {
        this.tipologia = tipologia;
        return this;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public Integer getIdPratic() {
        return idPratic;
    }

    public Collaboratore idPratic(Integer idPratic) {
        this.idPratic = idPratic;
        return this;
    }

    public void setIdPratic(Integer idPratic) {
        this.idPratic = idPratic;
    }

    public Integer getIdInvito() {
        return idInvito;
    }

    public Collaboratore idInvito(Integer idInvito) {
        this.idInvito = idInvito;
        return this;
    }

    public void setIdInvito(Integer idInvito) {
        this.idInvito = idInvito;
    }

    public Invito getInvito() {
        return invito;
    }

    public Collaboratore invito(Invito invito) {
        this.invito = invito;
        return this;
    }

    public void setInvito(Invito invito) {
        this.invito = invito;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Collaboratore)) {
            return false;
        }
        return id != null && id.equals(((Collaboratore) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Collaboratore{" +
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
