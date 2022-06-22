/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import EJB.ComentarioFacadeLocal;
import EJB.UsuariosFacadeLocal;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Comentario;
import modelo.Publicacion;
import modelo.Usuarios;

/**
 *
 * @author jsali
 */
@Named
@ViewScoped
public class commentsController implements Serializable {

    @EJB
    private UsuariosFacadeLocal EJBUsuario;

    @EJB
    private ComentarioFacadeLocal EJBComentario;
    
    private String newComment;

    private List<Comentario> comentarios;

    private Publicacion selectedPost;

    //--------------- Metodo inicializacion ---------------
    public void init(Publicacion pub) {

        selectedPost = pub;
        comentarios = EJBComentario.findAll(pub);
    }
    //--------------- Metodos ---------------

    public String getAuthor(int id) {        
        Usuarios res = EJBUsuario.getAuthor(id);
        
        if (res.getNick() != null) {
            return res.getNick();
        } else {
            return "";
        }
    }

    public String postCommentExplore(){
        Comentario comment = new Comentario();
        comment.setTexto(this.newComment);
        comment.setIdPublicacion(this.selectedPost.getIdPublicacion());
        FacesContext context = FacesContext.getCurrentInstance();
        Usuarios loggedUser = (Usuarios) context.getExternalContext().getSessionMap().get("user");
        comment.setIdUsuario(loggedUser.getIdUsuario());
        EJBComentario.create(comment);
        
        return "/explore?faces-redirect=true";
    }
    
    public String postCommentDashboard(){
        Comentario comment = new Comentario();
        comment.setTexto(this.newComment);
        comment.setIdPublicacion(this.selectedPost.getIdPublicacion());
        FacesContext context = FacesContext.getCurrentInstance();
        Usuarios loggedUser = (Usuarios) context.getExternalContext().getSessionMap().get("user");
        comment.setIdUsuario(loggedUser.getIdUsuario());
        EJBComentario.create(comment);
        
        return "/dashboard?faces-redirect=true";
    }
    
    public String postCommentProfile(){
        Comentario comment = new Comentario();
        comment.setTexto(this.newComment);
        comment.setIdPublicacion(this.selectedPost.getIdPublicacion());
        FacesContext context = FacesContext.getCurrentInstance();
        Usuarios loggedUser = (Usuarios) context.getExternalContext().getSessionMap().get("user");
        comment.setIdUsuario(loggedUser.getIdUsuario());
        EJBComentario.create(comment);
        
        return "/profile?faces-redirect=true";
    }
    
    //--------------- Getters y Setters ---------------
    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public Publicacion getSelectedPost() {
        return selectedPost;
    }

    public void setSelectedPost(Publicacion selectedPost) {
        this.selectedPost = selectedPost;
    }

    public UsuariosFacadeLocal getEJBUsuario() {
        return EJBUsuario;
    }

    public void setEJBUsuario(UsuariosFacadeLocal EJBUsuario) {
        this.EJBUsuario = EJBUsuario;
    }

    public ComentarioFacadeLocal getEJBComentario() {
        return EJBComentario;
    }

    public void setEJBComentario(ComentarioFacadeLocal EJBComentario) {
        this.EJBComentario = EJBComentario;
    }

    public String getNewComment() {
        return newComment;
    }

    public void setNewComment(String newComment) {
        this.newComment = newComment;
    }

    
}
