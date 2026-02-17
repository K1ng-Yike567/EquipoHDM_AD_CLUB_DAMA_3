package org.example.Entidades;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "pistas")
public class Pista {
    @Id
    @Column(name = "id_pista", nullable = false, length = 36)
    private String idPista;

    @Column(name = "deporte", nullable = false)
    private String deporte;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @ColumnDefault("1")
    @Column(name = "disponible", nullable = false)
    private Boolean disponible;

    public Pista() {
    }

    public Pista(String idPista, String deporte, String descripcion, Boolean disponible) {
        this.idPista = idPista;
        this.deporte = deporte;
        this.descripcion = descripcion;
        this.disponible = disponible;
    }

    public String getIdPista() {
        return idPista;
    }

    public void setIdPista(String idPista) {
        this.idPista = idPista;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

}