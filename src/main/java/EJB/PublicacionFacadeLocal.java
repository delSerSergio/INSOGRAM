/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package EJB;

import java.util.List;
import javax.ejb.Local;
import modelo.Publicacion;

/**
 *
 * @author jsali
 */
@Local
public interface PublicacionFacadeLocal {

    void create(Publicacion publicacion);

    void edit(Publicacion publicacion);

    void remove(Publicacion publicacion);

    Publicacion find(Object id);

    List<Publicacion> findAll();

    List<Publicacion> findRange(int[] range);

    int count();

    public void editOnlyScore(Publicacion pub);

    public List<Publicacion> findAllFollowed(int id);

    public List<Publicacion> findAllUploaded(int id);
    
}
