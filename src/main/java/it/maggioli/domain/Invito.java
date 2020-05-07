package it.maggioli.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @OneToMany(mappedBy = "invito")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Collaboratore> collaboratores = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("invitos")
    private Pratica pratica;

    @ManyToOne
    @JsonIgnoreProperties("invitos")
    private Professionista professionista;

    @ManyToMany(mappedBy = "idCollaboratores")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Collaboratore> idUtentes = new HashSet<>();

    @ManyToMany(mappedBy = "idClientes")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Cliente> idPraticas = new HashSet<>();

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

    public Set<Collaboratore> getCollaboratores() {
        return collaboratores;
    }

    public Invito collaboratores(Set<Collaboratore> collaboratores) {
        this.collaboratores = collaboratores;
        return this;
    }

    public Invito addCollaboratore(Collaboratore collaboratore) {
        this.collaboratores.add(collaboratore);
        collaboratore.setInvito(this);
        return this;
    }

    public Invito removeCollaboratore(Collaboratore collaboratore) {
        this.collaboratores.remove(collaboratore);
        collaboratore.setInvito(null);
        return this;
    }

    public void setCollaboratores(Set<Collaboratore> collaboratores) {
        this.collaboratores = collaboratores;
    }

    public Pratica getPratica() {
        return pratica;
    }

    public Invito pratica(Pratica pratica) {
        this.pratica = pratica;
        return this;
    }

    public void setPratica(Pratica pratica) {
        this.pratica = pratica;
    }

    public Professionista getProfessionista() {
        return professionista;
    }

    public Invito professionista(Professionista professionista) {
        this.professionista = professionista;
        return this;
    }

    public void setProfessionista(Professionista professionista) {
        this.professionista = professionista;
    }

    public Set<Collaboratore> getIdUtentes() {
        return idUtentes;
    }

    public Invito idUtentes(Set<Collaboratore> collaboratores) {
        this.idUtentes = collaboratores;
        return this;
    }

    public Invito addIdUtente(Collaboratore collaboratore) {
        this.idUtentes.add(collaboratore);
        collaboratore.getIdCollaboratores().add(this);
        return this;
    }

    public Invito removeIdUtente(Collaboratore collaboratore) {
        this.idUtentes.remove(collaboratore);
        collaboratore.getIdCollaboratores().remove(this);
        return this;
    }

    public void setIdUtentes(Set<Collaboratore> collaboratores) {
        this.idUtentes = collaboratores;
    }

    public Set<Cliente> getIdPraticas() {
        return idPraticas;
    }

    public Invito idPraticas(Set<Cliente> clientes) {
        this.idPraticas = clientes;
        return this;
    }

    public Invito addIdPratica(Cliente cliente) {
        this.idPraticas.add(cliente);
        cliente.getIdClientes().add(this);
        return this;
    }

    public Invito removeIdPratica(Cliente cliente) {
        this.idPraticas.remove(cliente);
        cliente.getIdClientes().remove(this);
        return this;
    }

    public void setIdPraticas(Set<Cliente> clientes) {
        this.idPraticas = clientes;
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
