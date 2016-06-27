package models;

import play.db.jpa.JPA;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "evenement")
public class Evenement {
    @ManyToOne
    protected Partenaire partenaire;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "categorie")     //Culture, Education, Sport
    private String categorie;
    @Column(name = "rubrique")
    private String rubrique;
    @Column(name = "titre")
    private String titre;
    @Column(name = "tarif")
    private String tarif;
    @Column(name = "lieu")
    private String lieu;
    @Column(name = "presentation")          // text
    private String presentation;
    @Column(name = "image")
    private String image;
    @Column(name = "horaire")
    private String horaire;
    @Column(name = "lien")
    private String lien;
    @Column(name = "jour")
    private Date jour;
    @Column(name = "video")
    private String video;
    @Column(name = "imagefull")
    private String imagefull;

    /**
     * @return
     */
    public List findList() {
        try {
            return JPA.em().createQuery("select evenement From Evenement evenement order by evenement.jour DESC").getResultList();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * @param id
     * @return
     */
    public Evenement findById(Long id) {
        try {
            return (Evenement) JPA.em().createQuery("select evenement From Evenement evenement WHERE evenement.id = :id").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * @param categorie
     * @return
     */
    public List findListByCategorie(String categorie) {
        try {
            return JPA.em().createQuery("select evenement From Evenement evenement where evenement.categorie = :categorie order by evenement.jour DESC").setParameter("categorie", categorie).getResultList();
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
            return JPA.em().createQuery("select evenement From Evenement evenement where evenement.partenaire.id = :idPartenaire order by evenement.jour DESC").setParameter("idPartenaire", idPartenaire).getResultList();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * @param idUtilisateur
     * @return
     */
    public List findListByUtilisateur(Long idUtilisateur) {
        List<Participant> participants = new Participant().findListByUtilisateur(idUtilisateur);
        return participants.stream().map(participant -> new Evenement().findById(participant.getEvenement().getId())).collect(Collectors.toList());
    }

    /**
     * @param evenement
     * @return
     */
    public String create(Evenement evenement) {
        Evenement evenementOld = findById(evenement.getId());

        if (evenementOld != null) {
            return "existe";
        } else {
            String result = null;
            try {
                JPA.em().persist(evenement);
            } catch (Exception e) {
                System.out.println(e.toString());
                result = e.toString();
            }
            return result;
        }
    }

    /**
     * @param evenement
     * @return
     */
    public String update(Evenement evenement) {
        Evenement newEvenement = new Evenement().findById(evenement.getId());
        if (newEvenement == null) {
            return "aucun enregistrement correspondant";
        } else {
            String result = null;
            newEvenement.setCategorie(evenement.getCategorie());
            newEvenement.setRubrique(evenement.getRubrique());
            newEvenement.setTitre(evenement.getTitre());
            newEvenement.setTarif(evenement.getTarif());
            newEvenement.setLieu(evenement.getLieu());
            newEvenement.setPresentation(evenement.getPresentation());
            newEvenement.setImage(evenement.getImage());
            newEvenement.setHoraire(evenement.getHoraire());
            newEvenement.setLien(evenement.getLien());
            newEvenement.setJour(evenement.getJour());
            newEvenement.setVideo(evenement.getVideo());
            newEvenement.setImagefull(evenement.getImagefull());
            try {
                JPA.em().persist(newEvenement);
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
        String result = null;
        Evenement evenement = new Evenement().findById(id);
        if (evenement == null) {
            return "aucun enregistrement correspondant";
        } else {
            try {
                JPA.em().remove(evenement);
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

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getRubrique() {
        return rubrique;
    }

    public void setRubrique(String rubrique) {
        this.rubrique = rubrique;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTarif() {
        return tarif;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
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

    public String getHoraire() {
        return horaire;
    }

    public void setHoraire(String horaire) {
        this.horaire = horaire;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public Date getJour() {
        return jour;
    }

    public void setJour(Date jour) {
        this.jour = jour;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getImagefull() {
        return imagefull;
    }

    public void setImagefull(String imagefull) {
        this.imagefull = imagefull;
    }
}
