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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import modelo.Usuarios;
import org.primefaces.model.file.UploadedFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author jsali
 */
@Named
@ViewScoped
public class profileController implements Serializable {

    /* -------------------------- Atributos -------------------------*/
    @EJB
    private UsuariosFacadeLocal EJBUsuario;
    private String username;
    private String bio;
    private String link;
    private String password;
    private String email;
    private String titulo;
    private UploadedFile image;
    private boolean comentarios;

    /* -------------------------- Atributos Manejo publicaciones -------------------------*/
    @EJB
    private ScoresFacadeLocal EJBScores;
    @EJB
    private SeguidosFacadeLocal EJBSeguidos;
    @EJB
    private PublicacionFacadeLocal EJBPublicacion;
    private List<Publicacion> publicaciones;
    private Publicacion selectedPost;

    public String save() {
        //Tendremos que guardar los datos y luego cerrar sesion        
        Usuarios user = (Usuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");

        String result = "";

        if (!this.bio.equals(user.getBio()) && !this.bio.equals("")) {
            user.setBio(this.bio);
            result = "/profile?faces-redirect=true";
        }
        if (!this.link.equals(user.getEnlace()) && !this.link.equals("")) {
            user.setEnlace(this.link);
            result = "/profile?faces-redirect=true";
        }
        if (!this.email.equals(user.getEmail()) && !this.email.equals("")) {
            user.setEmail(this.email);
            result = "/profile?faces-redirect=true";
        }
        if (!this.username.equals(user.getNick()) && !this.username.equals("")) {
            user.setNick(this.username);
            result = logout();
        }
        if (!this.password.equals(user.getPassword()) && !this.password.equals("")) {
            user.setPassword(this.password);
            result = logout();
        }
        //Query
        EJBUsuario.edit(user);

        return result;
    }

    /* -------------------------- Medodos redirecciones -------------------------*/
    public String logout() {
        return "/index?faces-redirect=true";
    }

    /* -------------------------- Metodos carga imagenes -------------------------*/
    public void init() {
        //Importacion de imagenes
        FacesContext context = FacesContext.getCurrentInstance();
        Usuarios loggedUser = (Usuarios) context.getExternalContext().getSessionMap().get("user");

        this.publicaciones = EJBPublicacion.findAllUploaded(loggedUser.getIdUsuario());
    }

    /* -------------------------- Metodos modificacion y borrado de imagenes -------------------------*/

    public String deleteImage(){
        Publicacion publi = selectedPost;
        
        EJBPublicacion.remove(publi);
        
        return "/profile?faces-redirect=true";
    }

    public String modifyImage(){
        Publicacion publi = selectedPost;
        
        if(publi.getTitulo() != this.titulo){
            publi.setTitulo(this.titulo);
        }
           
        if(!this.image.equals(null)){
            try {
            InputStream ins = this.image.getInputStream();
            BufferedImage imBuff = ImageIO.read(ins);

            String imageString;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            
            ImageIO.write(imBuff, "jpg", bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);
        
        
            if(!publi.getImagen().equals(imageString)){
                publi.setImagen(imageString);
            }
            
            }catch(IOException e){
                System.err.println(e.getMessage());
            }
        }
        
        
        /*
        if(publi.getPermisocomentarios() != this.comentarios){
            publi.setPermisocomentarios()
        }
        */
        
        EJBPublicacion.edit(publi);
        
        return "/profile?faces-redirect=true";
    }
      
    /* -------------------------- Metodo para obtener el autor de una publicacion -------------------------*/
    public String getAuthor(Publicacion pub) {
        System.out.println(pub.getTitulo());
        Usuarios res = EJBUsuario.getAuthor(pub.getIdUsuario());
        System.out.println(res.getNick());
        return res.getNick();
    }

    /* -------------------------- Getters y Setters -------------------------*/
    public UsuariosFacadeLocal getEJBUsuario() {
        return EJBUsuario;
    }

    public void setEJBUsuario(UsuariosFacadeLocal EJBUsuario) {
        this.EJBUsuario = EJBUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public List<Publicacion> getPublicaciones() {
        return publicaciones;
    }

    public void setPublicaciones(List<Publicacion> publicaciones) {
        this.publicaciones = publicaciones;
    }
   
    public Publicacion getSelectedPost() {
        return selectedPost;
    }

    public void setSelectedPost(Publicacion selectedPost) {
        this.selectedPost = selectedPost;
    }

    public String getSelectedImage() {
        return this.selectedPost.getImagen();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public UploadedFile getImage() {
        return image;
    }

    public void setImage(UploadedFile image) {
        this.image = image;
    }

    public boolean getComentarios() {
        return comentarios;
    }

    public void setComentarios(boolean comentarios) {
        this.comentarios = comentarios;
    }
    
    
}
