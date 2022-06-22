/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EJB;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.Comentario;
import modelo.Publicacion;

/**
 *
 * @author jsali
 */
@Stateless
public class ComentarioFacade extends AbstractFacade<Comentario> implements ComentarioFacadeLocal {

    private static final String SQL_COMMENTS = "FROM Comentario c WHERE c.idPublicacion = ?1";
    
    @PersistenceContext(unitName = "bbddPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComentarioFacade() {
        super(Comentario.class);
    }
    
    
    @Override
    public List<Comentario> findAll(Publicacion pub) {
        try {
            Query query = em.createQuery(SQL_COMMENTS);
            
            query.setParameter(1, pub.getIdPublicacion());
            

            List<Comentario> lista = query.getResultList();
            if (!lista.isEmpty()) {
                return lista;
            }else{
                return null;
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        
        return null;
    }
}
