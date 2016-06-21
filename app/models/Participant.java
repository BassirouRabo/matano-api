package models;

import play.db.jpa.JPA;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "participant")
public class Participant {
    @ManyToOne
    protected Evenement evenement;
    @ManyToOne
    protected Utilisateur utilisateur;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
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
            return JPA.em().createQuery("select participant From Participant participant").getResultList();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * @param id
     * @return
     */
    public Participant findById(Long id) {
        try {
            return (Participant) JPA.em().createQuery("select participant From Participant participant WHERE participant.id = :id").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * @param idEvenement
     * @return
     */
    public List findListByEvenementAndUtilisateur(Long idEvenement, Long idUtilisateur) {
        try {
            return JPA.em().createQuery("select participant From Participant participant where participant.evenement.id = :idEvenement and participant.utilisateur.id = :idUtilisateur").setParameter("idEvenement", idEvenement).setParameter("idUtilisateur", idUtilisateur).getResultList();
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
            return JPA.em().createQuery("select participant From Participant participant where participant.evenement.id = :idEvenement").setParameter("idEvenement", idEvenement).getResultList();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * @param participant
     * @return
     */
    public String create(Participant participant) {
        Utilisateur utilisateur = new Utilisateur().findByTelephone(participant.getTelephone());

        if (utilisateur == null) {
            return "aucun enregistrement correspondant";
        } else {
            Evenement evenement = new Evenement().findById(participant.getEvenement().getId());
            if (evenement == null) {
                return "aucun enregistrement correspondant";
            } else {
                participant.setUtilisateur(utilisateur);
                String result = null;
                try {
                    JPA.em().persist(participant);
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
        Participant participant = new Participant().findById(id);
        if (participant == null) {
            return "aucun enregistrement correspondant";
        } else {
            String result = null;
            try {
                JPA.em().remove(participant);
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