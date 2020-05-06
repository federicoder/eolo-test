package it.maggioli.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link it.maggioli.domain.Cliente} entity.
 */
public class ClienteDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Integer idCliente;

    private String nome;

    private String cognome;

    @NotNull
    private Integer idPraticaConnessa;

    private Set<InvitoDTO> idClientes = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Integer getIdPraticaConnessa() {
        return idPraticaConnessa;
    }

    public void setIdPraticaConnessa(Integer idPraticaConnessa) {
        this.idPraticaConnessa = idPraticaConnessa;
    }

    public Set<InvitoDTO> getIdClientes() {
        return idClientes;
    }

    public void setIdClientes(Set<InvitoDTO> invitos) {
        this.idClientes = invitos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClienteDTO clienteDTO = (ClienteDTO) o;
        if (clienteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clienteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClienteDTO{" +
            "id=" + getId() +
            ", idCliente=" + getIdCliente() +
            ", nome='" + getNome() + "'" +
            ", cognome='" + getCognome() + "'" +
            ", idPraticaConnessa=" + getIdPraticaConnessa() +
            ", idClientes='" + getIdClientes() + "'" +
            "}";
    }
}
