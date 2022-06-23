/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import EJB.PublicacionFacadeLocal;
import EJB.ScoresFacadeLocal;
import EJB.SeguidosFacadeLocal;
import EJB.UsuariosFacadeLocal;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Named;
import modelo.Publicacion;
import modelo.Scores;
import modelo.Seguidos;
import modelo.Usuarios;
import sun.misc.BASE64Decoder;

/**
 *
 * @author jsali
 */
@Named
@ViewScoped
public class exploreController implements Serializable{
    /* -------------------------- Atributos Manejo publicaciones -------------------------*/
    @EJB
    private ScoresFacadeLocal EJBScores;
    @EJB
    private SeguidosFacadeLocal EJBSeguidos;
    @EJB
    private PublicacionFacadeLocal EJBPublicacion;
    @EJB
    private UsuariosFacadeLocal EJBUsuario;
    
    private List<Publicacion> publicaciones;
    private String titulo;
    private String image;
    private int selectedPost;

    /* -------------------------- Metodos carga imagenes -------------------------*/
    public void init() {
        //Importacion de imagenes
        this.publicaciones = EJBPublicacion.findAll();

        List<BufferedImage> images = new ArrayList<>();
    }

    /* -------------------------- Metodo para obtener el autor de una publicacion -------------------------*/
    public String getAuthor(Publicacion pub) {

        Usuarios res = EJBUsuario.getAuthor(pub.getIdUsuario());

        return res.getNick();
    }

    /* -------------------------- Metodos gestion de votos -------------------------*/
    public void registerUpvote(Publicacion pub) {

        Scores aux = new Scores();

        aux.setIdPublicacion(pub.getIdPublicacion());

        FacesContext context = FacesContext.getCurrentInstance();
        Usuarios loggedUser = (Usuarios) context.getExternalContext().getSessionMap().get("user");

        aux.setIdUsuario(loggedUser.getIdUsuario());

        Scores scores = EJBScores.find(aux);
        
        

        if (scores == null) {
            int score = pub.getPuntuacion() + 1;
            pub.setPuntuacion(score);

            EJBPublicacion.editOnlyScore(pub);
            
            aux.setVote(1);
            
            EJBScores.create(aux);
        }else if(scores.getVote() == 1){            
            EJBScores.remove(scores);
            
            int score = pub.getPuntuacion() - 1;
            pub.setPuntuacion(score);

            EJBPublicacion.editOnlyScore(pub);
        }else{
            scores.setVote(1);
            EJBScores.edit(scores);
            
            int score = pub.getPuntuacion() + 2;
            pub.setPuntuacion(score);

            EJBPublicacion.editOnlyScore(pub);
        }  
    }

    public void registerDownvote(Publicacion pub) {

        Scores aux = new Scores();

        aux.setIdPublicacion(pub.getIdPublicacion());

        FacesContext context = FacesContext.getCurrentInstance();
        Usuarios loggedUser = (Usuarios) context.getExternalContext().getSessionMap().get("user");

        aux.setIdUsuario(loggedUser.getIdUsuario());

        Scores scores = EJBScores.find(aux);
        
        

        if (scores == null) {
            int score = pub.getPuntuacion() - 1;
            pub.setPuntuacion(score);

            EJBPublicacion.editOnlyScore(pub);
            
            aux.setVote(0);
            
            EJBScores.create(aux);
        }else if(scores.getVote() == 0){            
            EJBScores.remove(scores);
            
            int score = pub.getPuntuacion() + 1;
            pub.setPuntuacion(score);

            EJBPublicacion.editOnlyScore(pub);
        }else{
            scores.setVote(0);
            EJBScores.edit(scores);
            
            int score = pub.getPuntuacion() - 2;
            pub.setPuntuacion(score);

            EJBPublicacion.editOnlyScore(pub);
        }
    }

    /* -------------------------- Metodo gestion de seguimiento -------------------------*/
    public void follow(Publicacion pub) {

        FacesContext context = FacesContext.getCurrentInstance();
        Usuarios loggedUser = (Usuarios) context.getExternalContext().getSessionMap().get("user");

        Seguidos seguimiento = new Seguidos();

        seguimiento.setIdUsuario(loggedUser.getIdUsuario());
        seguimiento.setIdSeguidos(pub.getIdUsuario());

        //Sacamos por query
        Seguidos aux = EJBSeguidos.find(seguimiento);

        if (aux == null) {
            EJBSeguidos.create(seguimiento);
        } else {
            EJBSeguidos.remove(aux);
        }
    }

    /* -------------------------- Getters y Setters -------------------------*/

    public ScoresFacadeLocal getEJBScores() {
        return EJBScores;
    }

    public void setEJBScores(ScoresFacadeLocal EJBScores) {
        this.EJBScores = EJBScores;
    }

    public SeguidosFacadeLocal getEJBSeguidos() {
        return EJBSeguidos;
    }

    public void setEJBSeguidos(SeguidosFacadeLocal EJBSeguidos) {
        this.EJBSeguidos = EJBSeguidos;
    }

    public PublicacionFacadeLocal getEJBPublicacion() {
        return EJBPublicacion;
    }

    public void setEJBPublicacion(PublicacionFacadeLocal EJBPublicacion) {
        this.EJBPublicacion = EJBPublicacion;
    }

    public UsuariosFacadeLocal getEJBUsuario() {
        return EJBUsuario;
    }

    public void setEJBUsuario(UsuariosFacadeLocal EJBUsuario) {
        this.EJBUsuario = EJBUsuario;
    }

    public List<Publicacion> getPublicaciones() {
        return publicaciones;
    }

    public void setPublicaciones(List<Publicacion> publicaciones) {
        this.publicaciones = publicaciones;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getSelectedPost() {
        return selectedPost;
    }

    public void setSelectedPost(int selectedPost) {
        this.selectedPost = selectedPost;
    }
   
}
