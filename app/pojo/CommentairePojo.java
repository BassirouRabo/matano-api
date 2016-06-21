package pojo;

import models.Commentaire;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CommentairePojo {
    private Long id;
    private String nom;
    private String prenom;
    private String telephone;
    private Date jour;
    private String image;
    private String commentaire;

    public CommentairePojo(Long id, String nom, String prenom, String telephone, Date jour, String image, String commentaire) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.jour = jour;
        this.image = image;
        this.commentaire = commentaire;
    }

    public CommentairePojo() {
    }

    /**
     * @param commentaire
     * @return
     */
    public CommentairePojo transformation(Commentaire commentaire) {
        return new CommentairePojo(commentaire.getId(), commentaire.getUtilisateur().getNom(), commentaire.getUtilisateur().getPrenom(), commentaire.getUtilisateur().getTelephone(), commentaire.getJour(), commentaire.getUtilisateur().getImage(), commentaire.getCommentaire());
    }

    /**
     * @param commentaires
     * @return
     */
    public List<CommentairePojo> tranformationListe(List<Commentaire> commentaires) {
        return commentaires.stream().map(commentaire -> new CommentairePojo().transformation(commentaire)).collect(Collectors.toList());
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

    public Date getJour() {
        return jour;
    }

    public void setJour(Date jour) {
        this.jour = jour;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}
