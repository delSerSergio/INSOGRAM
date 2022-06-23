/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.*;
import modelo.Usuarios;

/**
 *
 * @author sdels
 */
@Stateless
public class UsuariosFacade extends AbstractFacade<Usuarios> implements UsuariosFacadeLocal {

    private static final String SQL_LOGIN = "FROM Usuarios u WHERE u.nick = ?1 and u.password = ?2";
    private static final String SQL_CREATE = "FROM Usuarios u WHERE u.nick = ?1 or u.email = ?2";
    private static final String SQL_POST_AUTHOR = "FROM Usuarios u WHERE u.idUsuario = ?1";

    @PersistenceContext(unitName = "bbddPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuariosFacade() {
        super(Usuarios.class);
    }

    @Override
    public Usuarios login(Usuarios user) {
        Usuarios usuario = null;

        try {
            Query query = em.createQuery(SQL_LOGIN);
            
            query.setParameter(1, user.getNick());
            query.setParameter(2, user.getPassword());

            List<Usuarios> lista = query.getResultList();
            if (!lista.isEmpty()) {
                usuario = lista.get(0);
            }
        } catch (Exception e) {
            System.err.println(e);
        }

        return usuario;
    }

    public void register(Usuarios user) {
        try {
            Query query = em.createQuery(SQL_CREATE);

            query.setParameter(1, user.getNick());
            query.setParameter(2, user.getEmail());

            List<Usuarios> lista = query.getResultList();

            if (!lista.isEmpty()) {
                create(user);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    @Override
    public Usuarios getAuthor(int id) {
        Usuarios usuario = null;
       
        try {
            Query query = em.createQuery(SQL_POST_AUTHOR);
            query.setParameter(1, id);

            List<Usuarios> lista = query.getResultList();
            if (!lista.isEmpty()) {
                usuario = lista.get(0);
            }
            
        } catch (Exception e) {
            System.err.println(e);
        }

        return usuario;
    }
}
