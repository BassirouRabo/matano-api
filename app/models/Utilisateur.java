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
    @Column(name = "presentation")
    private String presentation;

    public Utilisateur(String nom, String prenom, String telephone, String password, String email, String presentation) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.password = password;
        this.email = email;
        this.presentation = presentation;
    }

    public Utilisateur(String telephone, String password) {
        this.telephone = telephone;
        this.password = password;
    }

    public List<Utilisateur> findList() {
        try {
            return JPA.em().createQuery("select utilisateur From Utilisateur utilisateur").getResultList();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    public Utilisateur() {
    }

    public Utilisateur findByTelephone(String telephone) {
        try {
            return (Utilisateur)JPA.em().createQuery("select utilisateur From Utilisateur utilisateur WHERE utilisateur.telephone = :telephone").setParameter("telephone", telephone).getSingleResult();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    public Utilisateur findByTelephoneAndPassword(String telephone, String password) {
        try {
            return (Utilisateur) JPA.em().createQuery("select utilisateur From Utilisateur utilisateur WHERE utilisateur.telephone = :telephone AND utilisateur.password = :password ").setParameter("telephone", telephone).setParameter("password", password).getSingleResult();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    public String create(Utilisateur utilisateur) {
        Utilisateur utilisateurOld = findByTelephone(utilisateur.getTelephone());

        if(utilisateurOld != null){
            return "existe";
        }else{
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
}