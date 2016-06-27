package models;

import play.db.jpa.JPA;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "utilisateur")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "nom")
    private String nom;
    @Column(name = "prenom")
    private String prenom;
    @Column(name = "telephone", length = 8, unique = true, nullable = false)
    private String telephone;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "presentation")      // text
    private String presentation;
    @Column(name = "image")
    private String image;

    public Utilisateur(String nom, String prenom, String telephone, String password, String email, String presentation, String image) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.password = password;
        this.email = email;
        this.presentation = presentation;
        this.image = image;
    }

    public Utilisateur(String telephone, String password) {
        this.telephone = telephone;
        this.password = password;
    }

    public Utilisateur() {
    }

    /**
     * @return
     */
    public List findList() {
        try {
            return JPA.em().createQuery("select utilisateur From Utilisateur utilisateur").getResultList();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * @param id
     * @return
     */
    public Utilisateur findById(Long id) {
        try {
            return (Utilisateur) JPA.em().createQuery("select utilisateur From Utilisateur utilisateur WHERE utilisateur.id = :id").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * @param telephone
     * @return
     */
    public Utilisateur findByTelephone(String telephone) {
        try {
            return (Utilisateur) JPA.em().createQuery("select utilisateur From Utilisateur utilisateur WHERE utilisateur.telephone = :telephone").setParameter("telephone", telephone).getSingleResult();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * @param telephone
     * @param password
     * @return
     */
    public Utilisateur findByTelephoneAndPassword(String telephone, String password) {
        try {
            return (Utilisateur) JPA.em().createQuery("select utilisateur From Utilisateur utilisateur WHERE utilisateur.telephone = :telephone AND utilisateur.password = :password ").setParameter("telephone", telephone).setParameter("password", password).getSingleResult();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * @param utilisateur
     * @return
     */
    public String create(Utilisateur utilisateur) {
        Utilisateur utilisateurOld = findByTelephone(utilisateur.getTelephone());

        if (utilisateurOld != null) {
            return "existe";
        } else {
            String result = null;
            try {
                JPA.em().persist(utilisateur);
            } catch (Exception e) {
                System.out.println(e.toString());
                result = e.toString();
            }
            return result;
        }
    }

    /**
     * @param utilisateur
     * @return
     */
    public String update(Utilisateur utilisateur) {
        Utilisateur utilisateurNew = findById(utilisateur.getId());

        if (utilisateurNew == null) {
            return "aucun enregistrement correspondant";
        } else {
            String result = null;

            utilisateurNew.setNom(utilisateur.getNom());
            utilisateurNew.setPrenom(utilisateur.getPrenom());
            utilisateurNew.setTelephone(utilisateur.getTelephone());
            utilisateurNew.setPassword(utilisateur.getPassword());
            utilisateurNew.setEmail(utilisateur.getEmail());
            utilisateurNew.setPresentation(utilisateur.getPresentation());
            utilisateurNew.setImage(utilisateur.getImage());

            try {
                JPA.em().persist(utilisateurNew);
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
        Utilisateur utilisateur = findById(id);
        if (utilisateur == null) {
            return "aucun enregistrement correspondant";
        } else {
            String result = null;
            try {
                JPA.em().remove(utilisateur);
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
    public String deleteAccount(Long id) {
        Utilisateur utilisateur = findById(id);
        if (utilisateur == null) {
            return "aucun enregistrement correspondant";
        } else {

            String resultParticipant;
            List<Participant> participants = new Participant().findListByUtilisateur(id);
            for (Participant participant : participants) {
                resultParticipant = new Participant().delete(participant.getId());
                if (resultParticipant != null) {
                    return "erreur de suppression du participant " + participant.getId();
                }
            }

            String resultCommentaire;
            List<Commentaire> commentaires = new Commentaire().findListByUtilisateur(id);
            for (Commentaire commentaire : commentaires) {
                resultCommentaire = new Commentaire().delete(commentaire.getId());
                if (resultCommentaire != null) {
                    return "erreur de suppression du commentaire " + commentaire.getId();
                }
            }

            String resultImage;
            List<Image> images = new Image().findListByUtilisateur(id);
            for (Image image : images) {
                resultImage = new Image().delete(image.getId());
                if (resultImage != null) {
                    return "erreur de suppression du image " + image.getId();
                }
            }

            return delete(id);

        }
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

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
