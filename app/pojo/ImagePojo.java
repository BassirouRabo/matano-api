package pojo;


import models.Image;

import java.util.List;
import java.util.stream.Collectors;

public class ImagePojo {
    private Long id;
    private String nom;
    private String prenom;
    private String telephone;
    private String image;
    private String imagegalerie;

    public ImagePojo(Long id, String nom, String prenom, String telephone, String image, String imagegalerie) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.image = image;
        this.imagegalerie = imagegalerie;
    }

    public ImagePojo() {
    }

    /**
     * @param image
     * @return
     */
    public ImagePojo transformation(Image image) {
        return new ImagePojo(image.getId(), image.getUtilisateur().getNom(), image.getUtilisateur().getPrenom(), image.getUtilisateur().getTelephone(), image.getUtilisateur().getImage(), image.getImagegalerie());
    }

    /**
     * @param images
     * @return
     */
    public List<ImagePojo> transformationListe(List<Image> images) {
        return images.stream().map(image -> new ImagePojo().transformation(image)).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImagegalerie() {
        return imagegalerie;
    }

    public void setImagegalerie(String imagegalerie) {
        this.imagegalerie = imagegalerie;
    }
}
