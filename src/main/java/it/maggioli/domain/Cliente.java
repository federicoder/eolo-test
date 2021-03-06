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
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_cliente", nullable = false)
    private Integer idCliente;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cognome")
    private String cognome;

    @NotNull
    @Column(name = "id_pratica_connessa", nullable = false)
    private Integer idPraticaConnessa;

    @OneToOne(mappedBy = "cliente")
    @JsonIgnore
    private Invito invito;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public Cliente idCliente(Integer idCliente) {
        this.idCliente = idCliente;
        return this;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public Cliente nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public Cliente cognome(String cognome) {
        this.cognome = cognome;
        return this;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Integer getIdPraticaConnessa() {
        return idPraticaConnessa;
    }

    public Cliente idPraticaConnessa(Integer idPraticaConnessa) {
        this.idPraticaConnessa = idPraticaConnessa;
        return this;
    }

    public void setIdPraticaConnessa(Integer idPraticaConnessa) {
        this.idPraticaConnessa = idPraticaConnessa;
    }

    public Invito getInvito() {
        return invito;
    }

    public Cliente invito(Invito invito) {
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
        if (!(o instanceof Cliente)) {
            return false;
        }
        return id != null && id.equals(((Cliente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            ", idCliente=" + getIdCliente() +
            ", nome='" + getNome() + "'" +
            ", cognome='" + getCognome() + "'" +
            ", idPraticaConnessa=" + getIdPraticaConnessa() +
            "}";
    }
}
