package models;

import play.db.jpa.JPA;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

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
     *
     * @param idUtilisateur
     * @return
     */
    public List findListByUtilisateur(Long idUtilisateur) {
        try {
            return JPA.em().createQuery("select participant From Participant participant where participant.utilisateur.id = :idUtilisateur").setParameter("idUtilisateur", idUtilisateur).getResultList();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     *
     * @param participant
     * @return
     */
    public Participant transformation(Participant participant) {
        Utilisateur utilisateur = new Utilisateur().findById(participant.getUtilisateur().getId());
        if (utilisateur == null) {
            return null;
        } else {
            participant.setNom(utilisateur.getNom());
            participant.setPrenom(utilisateur.getPrenom());
            participant.setTelephone(utilisateur.getTelephone());
            participant.setImage(utilisateur.getImage());
            return participant;
        }
    }

    /**
     * @param participants
     * @return
     */
    public List<Participant> transformationListe(List<Participant> participants) {
        System.out.print("" + participants.size());

        return participants.stream().map(this::transformation).collect(Collectors.toList());
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
     * @param telephone
     * @return
     */
    public Participant findByEvenementAndUtilisateur(Long idEvenement, String telephone) {
        String result = existeEvenenementAndUtilisateur(idEvenement, telephone);

        if (result != null) {
            return null;
        } else {
            Long idUtilisateur = new Utilisateur().findByTelephone(telephone).getId();
            try {
                return (Participant) JPA.em().createQuery("select participant From Participant participant WHERE participant.evenement.id= :idEvenement and participant.utilisateur.id = :idUtilisateur").setParameter("idEvenement", idEvenement).setParameter("idUtilisateur", idUtilisateur).getSingleResult();
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        }
    }

    /**
     * @param idEvenement
     * @param telephone
     * @return
     */
    public String existeEvenenementAndUtilisateur(Long idEvenement, String telephone) {
        Utilisateur utilisateur = new Utilisateur().findByTelephone(telephone);

        if (utilisateur == null) {
            return "aucun enregistrement correspondant";
        } else {
            Evenement evenement = new Evenement().findById(idEvenement);
            if (evenement == null) {
                return "aucun enregistrement correspondant";
            } else {
                return null;
            }
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

                Participant participantOld = findByEvenementAndUtilisateur(evenement.getId(), utilisateur.getTelephone());
                if (participantOld != null) {
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


    /**
     * @param idEvenement
     * @param idUtilisateur
     * @return
     */
    public String deleteByEvenementAndUtilisateur(Long idEvenement, Long idUtilisateur) {
        Utilisateur utilisateur = new Utilisateur().findById(idUtilisateur);

        if (utilisateur == null) {
            return "aucun enregistrement correspondant";
        } else {
            Participant participant = findByEvenementAndUtilisateur(idEvenement, utilisateur.getTelephone());

            if (participant == null) {
                return "aucun enregistrement correspondant";
            } else {
                return delete(participant.getId());
            }
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
