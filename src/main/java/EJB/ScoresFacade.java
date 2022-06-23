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
import modelo.Scores;

/**
 *
 * @author jsali
 */
@Stateless
public class ScoresFacade extends AbstractFacade<Scores> implements ScoresFacadeLocal {

    private static final String SQL_FIND = "FROM Scores s WHERE s.idUsuario = ?1 and s.idPublicacion = ?2";
    
    @PersistenceContext(unitName = "bbddPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ScoresFacade() {
        super(Scores.class);
    }
    
    @Override
    public Scores find(Object id){
    Scores aux = (Scores) id;
    
    try {
            Query query = em.createQuery(SQL_FIND);
            query.setParameter(1, aux.getIdUsuario());
            query.setParameter(2, aux.getIdPublicacion());

            List<Scores> lista = query.getResultList();
            if (!lista.isEmpty()) {             
                return lista.get(0);
            }else{
                return null;
            }
        } catch (Exception e) {
            System.err.println(e);
        }          
    return null;
    }
}
