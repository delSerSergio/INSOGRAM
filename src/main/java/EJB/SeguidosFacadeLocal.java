/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package EJB;

import java.util.List;
import javax.ejb.Local;
import modelo.Seguidos;

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

    List<Seguidos> findAll();

    List<Seguidos> findRange(int[] range);

    int count();
    
}
