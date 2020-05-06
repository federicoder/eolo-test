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
    private Collaboratore idUtente;

    @OneToOne
    @JoinColumn(unique = true)
    private Cliente idUtente;

    @OneToMany(mappedBy = "idInvito")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Collaboratore> idInvitos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("idPraticas")
    private Pratica idPratica;

    @ManyToOne
    @JsonIgnoreProperties("idProfessionistas")
    private Professionista idUtente;

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

    public Collaboratore getIdUtente() {
        return idUtente;
    }

    public Invito idUtente(Collaboratore collaboratore) {
        this.idUtente = collaboratore;
        return this;
    }

    public void setIdUtente(Collaboratore collaboratore) {
        this.idUtente = collaboratore;
    }

    public Cliente getIdUtente() {
        return idUtente;
    }

    public Invito idUtente(Cliente cliente) {
        this.idUtente = cliente;
        return this;
    }

    public void setIdUtente(Cliente cliente) {
        this.idUtente = cliente;
    }

    public Set<Collaboratore> getIdInvitos() {
        return idInvitos;
    }

    public Invito idInvitos(Set<Collaboratore> collaboratores) {
        this.idInvitos = collaboratores;
        return this;
    }

    public Invito addIdInvito(Collaboratore collaboratore) {
        this.idInvitos.add(collaboratore);
        collaboratore.setIdInvito(this);
        return this;
    }

    public Invito removeIdInvito(Collaboratore collaboratore) {
        this.idInvitos.remove(collaboratore);
        collaboratore.setIdInvito(null);
        return this;
    }

    public void setIdInvitos(Set<Collaboratore> collaboratores) {
        this.idInvitos = collaboratores;
    }

    public Pratica getIdPratica() {
        return idPratica;
    }

    public Invito idPratica(Pratica pratica) {
        this.idPratica = pratica;
        return this;
    }

    public void setIdPratica(Pratica pratica) {
        this.idPratica = pratica;
    }

    public Professionista getIdUtente() {
        return idUtente;
    }

    public Invito idUtente(Professionista professionista) {
        this.idUtente = professionista;
        return this;
    }

    public void setIdUtente(Professionista professionista) {
        this.idUtente = professionista;
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
