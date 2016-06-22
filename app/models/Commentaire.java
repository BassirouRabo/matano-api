package models;

import play.db.jpa.JPA;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "commentaire")
public class Commentaire {
    @ManyToOne
    protected Evenement evenement;
    @ManyToOne
    protected Utilisateur utilisateur;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "jour")
    private Date jour;
    @Column(name = "commentaire")       // text
    private String commentaire;
    @Transient
    private String nom;
    @Transient
    private String prenom;
    @Transient
    private String image;
    @Transient
    private String telephone;

    /**
     * @return
     */
    public List findList() {
        try {
            return JPA.em().createQuery("select commentaire From Commentaire commentaire").getResultList();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * @param id
     * @return
     */
    public Commentaire findById(Long id) {
        try {
            return (Commentaire) JPA.em().createQuery("select commentaire From Commentaire commentaire WHERE commentaire.id = :id").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     *
     * @param commentaire
     * @return
     */
    public Commentaire transformation(Commentaire commentaire){
        Utilisateur utilisateur = new Utilisateur().findById(commentaire.getUtilisateur().getId());
        if(utilisateur == null){
            return null;
        }else {
            commentaire.setNom(utilisateur.getNom());
            commentaire.setPrenom(utilisateur.getPrenom());
            commentaire.setTelephone(utilisateur.getTelephone());
            commentaire.setImage(utilisateur.getImage());
            return commentaire;
        }
    }

    /**
     * @param idEvenement
     * @return
     */
    public List findListByEvenementAndUtilisateur(Long idEvenement, Long idUtilisateur) {
        try {
            return JPA.em().createQuery("select commentaire From Commentaire commentaire where commentaire.evenement.id = :idEvenement and commentaire.utilisateur.id = :idUtilisateur").setParameter("idEvenement", idEvenement).setParameter("idUtilisateur", idUtilisateur).getResultList();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * @param idEvenement
     * @return
     */
    public List findListByEvenement(Long idEvenement) {
        try {
            return JPA.em().createQuery("select commentaire From Commentaire commentaire where commentaire.evenement.id = :idEvenement").setParameter("idEvenement", idEvenement).getResultList();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * @param commentaire
     * @return
     */
    public String create(Commentaire commentaire) {
        Utilisateur utilisateur = new Utilisateur().findByTelephone(commentaire.getTelephone());

        if (utilisateur == null) {
            return "aucun enregistrement correspondant";
        } else {

            Evenement evenement = new Evenement().findById(commentaire.getEvenement().getId());
            if (evenement == null) {
                return "aucun enregistrement correspondant";
            } else {
                commentaire.setUtilisateur(utilisateur);
                commentaire.setJour(new Date());
                String result = null;
                try {
                    JPA.em().persist(commentaire);
                } catch (Exception e) {
                    System.out.println(e.toString());
                    result = e.toString();
                }
                return result;
            }
        }
    }

    /**
     * @param commentaire
     * @return
     */
    public String update(Commentaire commentaire) {
        Commentaire commentaireNew = findById(commentaire.getId());

        if (commentaireNew == null) {
            return "aucun enregistrement correspondant";
        } else {
            String result = null;
            commentaireNew.setJour(commentaire.getJour());
            commentaireNew.setCommentaire(commentaire.getCommentaire());
            try {
                JPA.em().persist(commentaireNew);
            } catch (Exception e) {
                System.out.println(e.toString());
                result = e.toString();
            }
            return result;
        }
    }

    /**
     * @param id
     * @return
     */
    public String delete(Long id) {
        Commentaire commentaire = new Commentaire().findById(id);
        if (commentaire == null) {
            return "aucun enregistrement correspondant";
        } else {
            String result = null;
            try {
                JPA.em().remove(commentaire);
            } catch (Exception e) {
                System.out.println(e.toString());
                result = e.toString();
            }
            return result;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getJour() {
        return jour;
    }

    public void setJour(Date jour) {
        this.jour = jour;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
