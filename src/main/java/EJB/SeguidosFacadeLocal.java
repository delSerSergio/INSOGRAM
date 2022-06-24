/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package EJB;

import java.util.List;
import javax.ejb.Local;
import modelo.Seguidos;
import modelo.Usuarios;

/**
 *
 * @author jsali
 */
@Local
public interface SeguidosFacadeLocal {

    void create(Seguidos seguidos);

    void edit(Seguidos seguidos);

    void remove(Seguidos seguidos);

    Seguidos find(Object id);

    Seguidos find(int id);

    List<Seguidos> findAll();

    List<Seguidos> findRange(int[] range);

    int count();

    public List<Seguidos> findSeguidores(Usuarios us);

    public List<Seguidos> findSeguidos(Usuarios us);

}
