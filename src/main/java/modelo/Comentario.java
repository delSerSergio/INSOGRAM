/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author sdels
 */

@Entity
@Table(name="comentario")
public class Comentario implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idComentario;
    
    @Column(name="idpublicacion")
    private int idPublicacion;
    
    @Column(name="idUsuario")
    private int idUsuario;
    
    @Column(name="texto")
    private String texto;
    
    

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public int getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(int idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.idComentario;
        hash = 23 * hash + this.idPublicacion;
        hash = 23 * hash + this.idUsuario;
        hash = 23 * hash + Objects.hashCode(this.texto);
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
        final Comentario other = (Comentario) obj;
        if (this.idComentario != other.idComentario) {
            return false;
        }
        if (this.idPublicacion != other.idPublicacion) {
            return false;
        }
        if (this.idUsuario != other.idUsuario) {
            return false;
        }
        return Objects.equals(this.texto, other.texto);
    }
    
   
}
