/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controller;


import EJB.PublicacionFacadeLocal;
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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
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
public class indexController implements Serializable{   
    
     /* -------------------------- Atributos Manejo publicaciones -------------------------*/    
    @EJB
    private UsuariosFacadeLocal EJBUsuario;
    @EJB
    private PublicacionFacadeLocal EJBPublicacion;
    private List<Publicacion> publicaciones;
    private String titulo;
    private String image;
    private int selectedPost;
    private Usuarios user;
    
    
    /* -------------------------- Metodo inicializacion -------------------------*/
    @PostConstruct
    public void init(){
       user = new Usuarios();
    }
    
    /* -------------------------- Metodos carga imagenes -------------------------*/
    public void load() {
        //Importacion de imagenes
        try {
            this.publicaciones = EJBPublicacion.findAll();

            List<BufferedImage> images = new ArrayList<>();

            int i = 0;

            for (Publicacion aux : this.publicaciones) {
                BufferedImage value = decodeToImage(aux.getImagen());

                images.add(value);

                ImageIO.write(images.get(i), "png", new File("tmpImage.png"));
                byte[] imageBytes = Files.readAllBytes(Paths.get("tmpImage.png"));

                Base64.Encoder encoder = Base64.getEncoder();

                aux.setImagen("data:image/png;base64," + encoder.encodeToString(imageBytes));

                i++;
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

        Usuarios res = EJBUsuario.getAuthor(pub.getIdUsuario());

        return res.getNick();
    }

    /* -------------------------- Metodos de gestion de usuarios -------------------------*/    
    public void register(){
        try{            
            EJBUsuario.create(user);
            
            System.out.println("Se crea el usuario");
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "AVISO", "¡REGISTRO CORRECTO!"));
            
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    public String login(){
        Usuarios us;              
        
        //Almacenamiento de session
        
        
        String redireccion = null;
        
        try{            
            us = EJBUsuario.login(user);
            
            if(us != null){
                
                redireccion = "/dashboard?faces-redirect=true"; 
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", us);
                
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "AVISO", "¡CREDENCIALES INCORRECTAS!"));
            } 
        }catch(Exception e){
            System.err.println(e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "AVISO", "¡ERROR!"));
        }
        
        return redireccion;
    }  

    /* -------------------------- Getters y Setters -------------------------*/
    public Usuarios getUser() {
        return user;
    }  
    
    public void setUser(Usuarios user) {
        this.user = user;
    }

    public UsuariosFacadeLocal getEJBUsuario() {
        return EJBUsuario;
    }

    public void setEJBUsuario(UsuariosFacadeLocal EJBUsuario) {
        this.EJBUsuario = EJBUsuario;
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
