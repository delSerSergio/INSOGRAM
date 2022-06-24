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
import modelo.Seguidos;
import modelo.Usuarios;

/**
 *
 * @author jsali
 */
@Stateless
public class SeguidosFacade extends AbstractFacade<Seguidos> implements SeguidosFacadeLocal {

    private static final String SQL_FIND = "FROM Seguidos s WHERE s.idUsuario = ?1 and s.idSeguidos = ?2 and s.bloqueado = 0";
    private static final String SQL_FOLLOWED = "FROM Seguidos s WHERE s.idSeguidos = ?1";
    private static final String SQL_FOLLOWING = "FROM Seguidos s WHERE s.idUsuario = ?1";
    
    private static final String SQL_BLOCK = "FROM Seguidos s WHERE s.idSeguimiento = ?1";

    @PersistenceContext(unitName = "bbddPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SeguidosFacade() {
        super(Seguidos.class);
    }

    @Override
    public Seguidos find(Object seg) {
        Seguidos aux = (Seguidos) seg;

        try {
            Query query = em.createQuery(SQL_FIND);
            query.setParameter(1, aux.getIdUsuario());
            query.setParameter(2, aux.getIdSeguidos());

            List<Seguidos> lista = query.getResultList();
            if (!lista.isEmpty()) {
                return lista.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }

    @Override
    public List<Seguidos> findSeguidores(Usuarios us) {
        try {
            Query query = em.createQuery(SQL_FOLLOWED);
            query.setParameter(1, us.getIdUsuario());
            

            List<Seguidos> lista = query.getResultList();
            
            if (!lista.isEmpty()) {
                return lista;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }

    @Override
    public List<Seguidos> findSeguidos(Usuarios us) {
    try {
            Query query = em.createQuery(SQL_FOLLOWING);
            query.setParameter(1, us.getIdUsuario());
            

            List<Seguidos> lista = query.getResultList();
            
            if (!lista.isEmpty()) {
                return lista;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;   
    }

    @Override
    public Seguidos find(int id) {
        try {
            Query query = em.createQuery(SQL_BLOCK);
            query.setParameter(1, id);
            

            List<Seguidos> lista = query.getResultList();
            
            if (!lista.isEmpty()) {
                return lista.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return null; }

}
