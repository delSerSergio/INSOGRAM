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
import modelo.Usuarios;
import sun.misc.BASE64Decoder;

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
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Usuarios loggedUser = (Usuarios) context.getExternalContext().getSessionMap().get("user");

            this.publicaciones = EJBPublicacion.findAllUploaded(loggedUser.getIdUsuario());

            List<BufferedImage> images = new ArrayList<>();

            int i = 0;
            if (this.publicaciones != null) {
                for (Publicacion aux : this.publicaciones) {
                    BufferedImage value = decodeToImage(aux.getImagen());

                    images.add(value);

                    ImageIO.write(images.get(i), "png", new File("tmpImage.png"));
                    byte[] imageBytes = Files.readAllBytes(Paths.get("tmpImage.png"));

                    Base64.Encoder encoder = Base64.getEncoder();

                    aux.setImagen("data:image/png;base64," + encoder.encodeToString(imageBytes));

                    i++;
                }
            }

        } catch (IOException e) {
            System.err.println(e);
        }

    }

    public BufferedImage decodeToImage(String imageString) {
        BufferedImage result = null;

        try {
            byte[] imageByte;
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            result = ImageIO.read(bis);
        } catch (IOException e) {
            System.err.println(e);
        }

        return result;
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
}
