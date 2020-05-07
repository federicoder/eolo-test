package it.maggioli.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link it.maggioli.domain.Pratica} entity.
 */
public class PraticaDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Integer idPratica;

    @NotNull
    private Integer idLic;

    @NotNull
    private Integer tdp;

    private Integer idCollab;

    @NotNull
    private Integer idClient;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(Integer idPratica) {
        this.idPratica = idPratica;
    }

    public Integer getIdLic() {
        return idLic;
    }

    public void setIdLic(Integer idLic) {
        this.idLic = idLic;
    }

    public Integer getTdp() {
        return tdp;
    }

    public void setTdp(Integer tdp) {
        this.tdp = tdp;
    }

    public Integer getIdCollab() {
        return idCollab;
    }

    public void setIdCollab(Integer idCollab) {
        this.idCollab = idCollab;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PraticaDTO praticaDTO = (PraticaDTO) o;
        if (praticaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), praticaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PraticaDTO{" +
            "id=" + getId() +
            ", idPratica=" + getIdPratica() +
            ", idLic=" + getIdLic() +
            ", tdp=" + getTdp() +
            ", idCollab=" + getIdCollab() +
            ", idClient=" + getIdClient() +
            "}";
    }
}
