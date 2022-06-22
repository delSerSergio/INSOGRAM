/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package EJB;

import java.util.List;
import javax.ejb.Local;
import modelo.Comentario;
import modelo.Publicacion;

/**
 *
 * @author jsali
 */
@Local
public interface ComentarioFacadeLocal {

    void create(Comentario comentario);

    void edit(Comentario comentario);

    void remove(Comentario comentario);

    Comentario find(Object id);

    List<Comentario> findAll();
    
    List<Comentario> findAll(Publicacion pub);

    List<Comentario> findRange(int[] range);

    int count();
    
}
