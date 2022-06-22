/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author sdels
 */

@Entity
@Table(name="publicacion")
public class Publicacion implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idPublicacion;
    
    @Column(name="titulo")
    private String titulo;
    
    @Column(name="imagen")
    private String imagen;
    
    @Column(name="puntuacion")
    private int puntuacion;
    
    @Column(name="idUsuario")
    private int idUsuario;
    
    @Column(name="fecha")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fecha;

    public int getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(int idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.idPublicacion;
        hash = 37 * hash + Objects.hashCode(this.titulo);
        hash = 37 * hash + Objects.hashCode(this.imagen);
        hash = 37 * hash + this.puntuacion;
        hash = 37 * hash + this.idUsuario;
        hash = 37 * hash + Objects.hashCode(this.fecha);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Publicacion other = (Publicacion) obj;
        if (this.idPublicacion != other.idPublicacion) {
            return false;
        }
        if (this.puntuacion != other.puntuacion) {
            return false;
        }
        if (this.idUsuario != other.idUsuario) {
            return false;
        }
        if (!Objects.equals(this.titulo, other.titulo)) {
            return false;
        }
        if (!Objects.equals(this.imagen, other.imagen)) {
            return false;
        }
        return Objects.equals(this.fecha, other.fecha);
    }
    
    
}
