package it.maggioli.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Invito.
 */
@Entity
@Table(name = "invito")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "invito")
public class Invito implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "utente_iscritto")
    private Boolean utenteIscritto;

    @Column(name = "id_utente")
    private Integer idUtente;

    @Column(name = "id_pratica")
    private Integer idPratica;

    @NotNull
    @Column(name = "id_invito", nullable = false)
    private Integer idInvito;

    @OneToOne
    @JoinColumn(unique = true)
    private Collaboratore collaboratore;

    @OneToOne
    @JoinColumn(unique = true)
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isUtenteIscritto() {
        return utenteIscritto;
    }

    public Invito utenteIscritto(Boolean utenteIscritto) {
        this.utenteIscritto = utenteIscritto;
        return this;
    }

    public void setUtenteIscritto(Boolean utenteIscritto) {
        this.utenteIscritto = utenteIscritto;
    }

    public Integer getIdUtente() {
        return idUtente;
    }

    public Invito idUtente(Integer idUtente) {
        this.idUtente = idUtente;
        return this;
    }

    public void setIdUtente(Integer idUtente) {
        this.idUtente = idUtente;
    }

    public Integer getIdPratica() {
        return idPratica;
    }

    public Invito idPratica(Integer idPratica) {
        this.idPratica = idPratica;
        return this;
    }

    public void setIdPratica(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public Integer getIdInvito() {
        return idInvito;
    }

    public Invito idInvito(Integer idInvito) {
        this.idInvito = idInvito;
        return this;
    }

    public void setIdInvito(Integer idInvito) {
        this.idInvito = idInvito;
    }

    public Collaboratore getCollaboratore() {
        return collaboratore;
    }

    public Invito collaboratore(Collaboratore collaboratore) {
        this.collaboratore = collaboratore;
        return this;
    }

    public void setCollaboratore(Collaboratore collaboratore) {
        this.collaboratore = collaboratore;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Invito cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Invito)) {
            return false;
        }
        return id != null && id.equals(((Invito) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Invito{" +
            "id=" + getId() +
            ", utenteIscritto='" + isUtenteIscritto() + "'" +
            ", idUtente=" + getIdUtente() +
            ", idPratica=" + getIdPratica() +
            ", idInvito=" + getIdInvito() +
            "}";
    }
}
