/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author jsali
 */
@Entity
@Table(name = "seguidos")
public class Seguidos implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSeguimiento;

    @Column(name = "idUsuario")
    private int idUsuario;

    @Column(name = "idSeguidos")
    private int idSeguidos;

    @Column(name = "bloqueado")
    private int bloqueado;
    
    public int getIdSeguimiento() {
        return idSeguimiento;
    }    

    public void setIdSeguimiento(int idSeguimiento) {
        this.idSeguimiento = idSeguimiento;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdSeguidos() {
        return idSeguidos;
    }

    public void setIdSeguidos(int idSeguidos) {
        this.idSeguidos = idSeguidos;
    }

    public int getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(int bloqueado) {
        this.bloqueado = bloqueado;
    }    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.idSeguimiento;
        hash = 29 * hash + this.idUsuario;
        hash = 29 * hash + this.idSeguidos;
        hash = 29 * hash + this.bloqueado;
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
        final Seguidos other = (Seguidos) obj;
        if (this.idSeguimiento != other.idSeguimiento) {
            return false;
        }
        if (this.idUsuario != other.idUsuario) {
            return false;
        }
        if (this.idSeguidos != other.idSeguidos) {
            return false;
        }
        return this.bloqueado == other.bloqueado;
    }
}
