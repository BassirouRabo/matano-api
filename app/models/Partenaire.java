package models;

import play.db.jpa.JPA;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "partenaire")
public class Partenaire {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "nom")
    private String nom;
    @Column(name = "telephone", length = 8, unique = true, nullable = false)
    private String telephone;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "adresse")
    private String adresse;

    public Partenaire(String nom, String telephone, String password, String email, String adresse) {
        this.nom = nom;
        this.telephone = telephone;
        this.password = password;
        this.email = email;
        this.adresse = adresse;
    }

    public Partenaire() {
    }

    /**
     * @return
     */
    public List findList() {
        try {
            return JPA.em().createQuery("select partenaire From Partenaire partenaire").getResultList();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * @param id
     * @return
     */
    public Partenaire findById(Long id) {
        try {
            return (Partenaire) JPA.em().createQuery("select partenaire From Partenaire partenaire WHERE partenaire.id = :id").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * @param telephone
     * @return
     */
    public Partenaire findByTelephone(String telephone) {
        try {
            return (Partenaire) JPA.em().createQuery("select partenaire From Partenaire partenaire WHERE partenaire.telephone = :telephone").setParameter("telephone", telephone).getSingleResult();
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
    public Partenaire findByTelephoneAndPassword(String telephone, String password) {
        try {
            return (Partenaire) JPA.em().createQuery("select partenaire From Partenaire partenaire WHERE partenaire.telephone = :telephone AND partenaire.password = :password ").setParameter("telephone", telephone).setParameter("password", password).getSingleResult();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * @param partenaire
     * @return
     */
    public String create(Partenaire partenaire) {
        Partenaire partenaireOld = findByTelephone(partenaire.getTelephone());

        if (partenaireOld != null) {
            return "existe";
        } else {
            String result = null;
            try {
                JPA.em().persist(partenaire);
            } catch (Exception e) {
                System.out.println(e.toString());
                result = e.toString();
            }
            return result;
        }
    }

    /**
     * @param partenaire
     * @return
     */
    public String update(Partenaire partenaire) {
        Partenaire partenaireNew = findById(partenaire.getId());

        if (partenaireNew == null) {
            return "aucun enregistrement correspondant";
        } else {
            String result = null;

            partenaireNew.setNom(partenaire.getNom());
            partenaireNew.setTelephone(partenaire.getTelephone());
            partenaireNew.setPassword(partenaire.getPassword());
            partenaireNew.setEmail(partenaire.getEmail());
            partenaireNew.setAdresse(partenaire.getAdresse());

            try {
                JPA.em().persist(partenaireNew);
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
        Partenaire partenaire = findById(id);
        if (partenaire == null) {
            return "aucun enregistrement correspondant";
        } else {
            String result = null;
            try {
                JPA.em().remove(partenaire);
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}
