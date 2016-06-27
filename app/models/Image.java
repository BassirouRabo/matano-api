package models;

import play.db.jpa.JPA;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "image")
public class Image {
    @ManyToOne
    protected Evenement evenement;
    @ManyToOne
    protected Utilisateur utilisateur;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "jour")
    private Date jour;
    @Column(name = "imagegalerie")
    private String imagegalerie;
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
            return JPA.em().createQuery("select image From Image image").getResultList();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * @param id
     * @return
     */
    public Image findById(Long id) {
        try {
            return (Image) JPA.em().createQuery("select image From Image image WHERE image.id = :id").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * @param image
     * @return
     */
    public Image transformation(Image image) {
        Utilisateur utilisateur = new Utilisateur().findById(image.getUtilisateur().getId());
        if (utilisateur == null) {
            return null;
        } else {
            image.setNom(utilisateur.getNom());
            image.setPrenom(utilisateur.getPrenom());
            image.setTelephone(utilisateur.getTelephone());
            image.setImage(utilisateur.getImage());
            return image;
        }
    }

    /**
     * @param idEvenement
     * @return
     */
    public List findListByEvenementAndUtilisateur(Long idEvenement, Long idUtilisateur) {
        try {
            return JPA.em().createQuery("select image From Image image where image.evenement.id = :idEvenement and image.utilisateur.id = :idUtilisateur").setParameter("idEvenement", idEvenement).setParameter("idUtilisateur", idUtilisateur).getResultList();
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
            return JPA.em().createQuery("select image From Image image where image.evenement.id = :idEvenement").setParameter("idEvenement", idEvenement).getResultList();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * @param idPartenaire
     * @return
     */
    public List findListByPartenaire(Long idPartenaire) {
        try {
            return JPA.em().createQuery("select image From Image image where image.evenement.partenaire.id = :idPartenaire").setParameter("idPartenaire", idPartenaire).getResultList();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * @param idUtilisteur
     * @return
     */
    public List findListByUtilisateur(Long idUtilisteur) {
        try {
            return JPA.em().createQuery("select image From Image image where image.utilisateur.id = :idUtilisteur").setParameter("idUtilisteur", idUtilisteur).getResultList();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * @param image
     * @return
     */
    public String create(Image image) {
        Utilisateur utilisateur = new Utilisateur().findById(image.getUtilisateur().getId());

        if (utilisateur == null) {
            return "aucun enregistrement correspondant";
        } else {

            Evenement evenement = new Evenement().findById(image.getEvenement().getId());
            if (evenement == null) {
                return "aucun enregistrement correspondant";
            } else {
                image.setUtilisateur(utilisateur);
                String result = null;
                try {
                    JPA.em().persist(image);
                } catch (Exception e) {
                    System.out.println(e.toString());
                    result = e.toString();
                }
                return result;
            }
        }
    }

    /**
     * @param id
     * @return
     */
    public String delete(Long id) {
        Image image = new Image().findById(id);
        if (image == null) {
            return "aucun enregistrement correspondant";
        } else {
            String result = null;
            try {
                JPA.em().remove(image);
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

    public String getImagegalerie() {
        return imagegalerie;
    }

    public void setImagegalerie(String imagegalerie) {
        this.imagegalerie = imagegalerie;
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
