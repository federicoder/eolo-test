package it.maggioli.domain;

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
 * A Pratica.
 */
@Entity
@Table(name = "pratica")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "pratica")
public class Pratica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_pratica", nullable = false)
    private Integer idPratica;

    @NotNull
    @Column(name = "id_lic", nullable = false)
    private Integer idLic;

    @NotNull
    @Column(name = "tdp", nullable = false)
    private Integer tdp;

    @Column(name = "id_collab")
    private Integer idCollab;

    @NotNull
    @Column(name = "id_client", nullable = false)
    private Integer idClient;

    @OneToMany(mappedBy = "idPratica")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Invito> idPraticas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("idLicenzas")
    private Licenza idLic;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdPratica() {
        return idPratica;
    }

    public Pratica idPratica(Integer idPratica) {
        this.idPratica = idPratica;
        return this;
    }

    public void setIdPratica(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public Integer getIdLic() {
        return idLic;
    }

    public Pratica idLic(Integer idLic) {
        this.idLic = idLic;
        return this;
    }

    public void setIdLic(Integer idLic) {
        this.idLic = idLic;
    }

    public Integer getTdp() {
        return tdp;
    }

    public Pratica tdp(Integer tdp) {
        this.tdp = tdp;
        return this;
    }

    public void setTdp(Integer tdp) {
        this.tdp = tdp;
    }

    public Integer getIdCollab() {
        return idCollab;
    }

    public Pratica idCollab(Integer idCollab) {
        this.idCollab = idCollab;
        return this;
    }

    public void setIdCollab(Integer idCollab) {
        this.idCollab = idCollab;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public Pratica idClient(Integer idClient) {
        this.idClient = idClient;
        return this;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public Set<Invito> getIdPraticas() {
        return idPraticas;
    }

    public Pratica idPraticas(Set<Invito> invitos) {
        this.idPraticas = invitos;
        return this;
    }

    public Pratica addIdPratica(Invito invito) {
        this.idPraticas.add(invito);
        invito.setIdPratica(this);
        return this;
    }

    public Pratica removeIdPratica(Invito invito) {
        this.idPraticas.remove(invito);
        invito.setIdPratica(null);
        return this;
    }

    public void setIdPraticas(Set<Invito> invitos) {
        this.idPraticas = invitos;
    }

    public Licenza getIdLic() {
        return idLic;
    }

    public Pratica idLic(Licenza licenza) {
        this.idLic = licenza;
        return this;
    }

    public void setIdLic(Licenza licenza) {
        this.idLic = licenza;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pratica)) {
            return false;
        }
        return id != null && id.equals(((Pratica) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Pratica{" +
            "id=" + getId() +
            ", idPratica=" + getIdPratica() +
            ", idLic=" + getIdLic() +
            ", tdp=" + getTdp() +
            ", idCollab=" + getIdCollab() +
            ", idClient=" + getIdClient() +
            "}";
    }
}
