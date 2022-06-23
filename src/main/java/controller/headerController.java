/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import EJB.PublicacionFacadeLocal;
import EJB.UsuariosFacadeLocal;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import modelo.Publicacion;
import modelo.Usuarios;
import org.primefaces.model.file.UploadedFile;
import sun.misc.BASE64Encoder;

/**
 *
 * @author jsali
 */
@Named
@ViewScoped
public class headerController implements Serializable {

    /* -------------------------- Atributos -------------------------*/
 /* -------------------------- Atributos manejo usuario -------------------------*/
    @EJB
    private UsuariosFacadeLocal EJBUsuario;
    private static Usuarios user;
    private final FacesContext context = FacesContext.getCurrentInstance();

    /* -------------------------- Atributos subida imagenes -------------------------*/
    @EJB
    private PublicacionFacadeLocal EJBPublicacion;
    private UploadedFile image;
    private String titulo;
    private boolean comentarios;

    /* -------------------------- Medodo inicializacion -------------------------*/
    public void init() {
        user = (Usuarios) context.getExternalContext().getSessionMap().get("user");
        user = EJBUsuario.login(user);
    }

    /* -------------------------- Medodos redirecciones -------------------------*/
    public void extRedirect() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect(getLink());
    }

    public String redirectProfile() {
        return "/profile?faces-redirect=true";
    }

    public String redirectDashboard() {
        return "/dashboard?faces-redirect=true";
    }

    public String logout() {
        user = null;

        return "/index?faces-redirect=true";
    }

    /* -------------------------- Gestion publicaciones -------------------------*/
    public String upload() {
        try {
            Publicacion publi = new Publicacion();

            publi.setTitulo(this.titulo);

            if (this.comentarios == false) {
                publi.setPermisocomentarios("false");
            } else {
                publi.setPermisocomentarios("true");
            }

            //System.out.println(this.image.getContentType());
            InputStream ins = this.image.getInputStream();

            BufferedImage imBuff = ImageIO.read(ins);

            String imageString;
            System.out.println(this.getClass().getProtectionDomain().getCodeSource().getLocation());

            String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
            path = path.substring(6, path.lastIndexOf("W")) + "resources/images/";
            path = path.replace("/", "\\");

            System.out.println(path);

            String name = "";
            if (this.EJBPublicacion.findAll() == null) {
                name = 1 + ".png";
            } else {
                name = (this.EJBPublicacion.findAll().size() + 1) + ".png";
            }

            File bos = new File(path + name);

            try {
                ImageIO.write(imBuff, "png", bos);

                publi.setImagen(name);
                publi.setIdUsuario(this.user.getIdUsuario());

                Date date = new Date();

                System.out.println(date);

                publi.setFecha(date);

                EJBPublicacion.create(publi);

                //Recargar 
                return "/dashboard?faces-redirect=true";

            } catch (IOException e) {
                System.err.println(e);
            }

        } catch (IOException ex) {
            Logger.getLogger(headerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /* -------------------------- Getters y Setters -------------------------*/
    public String getDesc() {
        return user.getBio();
    }

    public void setDesc(String desc) {
        this.user.setBio(desc);
    }

    public UploadedFile getImage() {
        return image;
    }

    public void setImage(UploadedFile image) {
        this.image = image;
    }

    public Usuarios getUser() {
        return this.user;
    }

    public String getUsername() {
        return this.user.getNick();
    }

    public String getName() {
        return this.user.getNombre() + " " + this.user.getApellido();
    }

    public String getLink() {
        String aux = "";
        String link = this.user.getEnlace();
        if (link == null) {
            return "";
        }

        if (!link.contains("http")) {
            aux += "https://";
        }

        link = aux + link;
        return link;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public PublicacionFacadeLocal getEJBPublicacion() {
        return EJBPublicacion;
    }

    public void setEJBPublicacion(PublicacionFacadeLocal EJBPublicacion) {
        this.EJBPublicacion = EJBPublicacion;
    }

    public boolean getComentarios() {
        return comentarios;
    }

    public void setComentarios(boolean comentarios) {
        this.comentarios = comentarios;
    }

}
