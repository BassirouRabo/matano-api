package models;

import play.db.jpa.JPA;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "actualite")
public class Actualite {
    @ManyToOne
    protected Evenement evenement;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "jour")
    private Date jour;
    @Column(name = "actualite")
    private String actualite;

    /**
     * @return
     */
    public List findList() {
        try {
            return JPA.em().createQuery("select actualite From Actualite actualite").getResultList();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * @param id
     * @return
     */
    public Actualite findById(Long id) {
        try {
            return (Actualite) JPA.em().createQuery("select actualite From Actualite actualite WHERE actualite.id = :id").setParameter("id", id).getSingleResult();
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
            return JPA.em().createQuery("select actualite From Actualite actualite where actualite.evenement.id = :idEvenement").setParameter("idEvenement", idEvenement).getResultList();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * @param actualite
     * @return
     */
    public String create(Actualite actualite) {
        Evenement evenement = new Evenement().findById(actualite.getId());
        if (evenement == null) {
            return "aucun enregistrement correspondant";
        } else {
            String result = null;
            try {
                JPA.em().persist(actualite);
            } catch (Exception e) {
                System.out.println(e.toString());
                result = e.toString();
            }
            return result;
        }

    }

    /**
     * @param actualite
     * @return
     */
    public String update(Actualite actualite) {
        Actualite actualiteNew = findById(actualite.getId());

        if (actualiteNew == null) {
            return "aucun enregistrement correspondant";
        } else {
            String result = null;
            actualiteNew.setJour(actualite.getJour());
            actualiteNew.setActualite(actualite.getActualite());
            try {
                JPA.em().persist(actualiteNew);
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
        Actualite actualite = new Actualite().findById(id);
        if (actualite == null) {
            return "aucun enregistrement correspondant";
        } else {
            String result = null;
            try {
                JPA.em().remove(actualite);
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

    public String getActualite() {
        return actualite;
    }

    public void setActualite(String actualite) {
        this.actualite = actualite;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }
}
