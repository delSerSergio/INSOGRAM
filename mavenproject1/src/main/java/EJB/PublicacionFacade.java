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
import modelo.Publicacion;

/**
 *
 * @author jsali
 */
@Stateless
public class PublicacionFacade extends AbstractFacade<Publicacion> implements PublicacionFacadeLocal {

    private static final String SQL_RECENT = "FROM Publicacion p ORDER BY p.fecha DESC";
    private static final String SQL_UPDATE_SCORE = "FROM Publicacion p WHERE p.idPublicacion = ?1";
    private static final String SQL_FOLLOWED = "FROM Publicacion p INNER JOIN Seguidos s ON p.idUsuario = s.idSeguidos WHERE s.idUsuario = ?1";
    private static final String SQL_UPLOADED = "FROM Publicacion p WHERE p.idUsuario = ?1";

    @PersistenceContext(unitName = "bbddPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PublicacionFacade() {
        super(Publicacion.class);
    }

    @Override
    public List<Publicacion> findAll() {
        try {
            Query query = em.createQuery(SQL_RECENT);

            List<Publicacion> lista = query.getResultList();
            if (!lista.isEmpty()) {
                return lista;
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }

    @Override
    public void editOnlyScore(Publicacion pub) {
        try {
            Query query = em.createQuery(SQL_UPDATE_SCORE);

            query.setParameter(1, pub.getIdPublicacion());

            List<Publicacion> lista = query.getResultList();

            if (!lista.isEmpty()) {
                Publicacion aux = lista.get(0);
                aux.setPuntuacion(pub.getPuntuacion());
                edit(aux);
            }
        } catch (Exception e) {
            System.err.println(e);
        }

    }

    @Override
    public List<Publicacion> findAllFollowed(int id) {

        try {
            Query query = em.createQuery(SQL_FOLLOWED);
            query.setParameter(1, id);

            List<Publicacion> lista = query.getResultList();
            if (!lista.isEmpty()) {
                return lista;
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }

    @Override
    public List<Publicacion> findAllUploaded(int id) {
         try {
            Query query = em.createQuery(SQL_UPLOADED);
            query.setParameter(1, id);

            List<Publicacion> lista = query.getResultList();
            if (!lista.isEmpty()) {
                return lista;
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
       }

}
