package models;

import play.db.jpa.JPA;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "message")
    private String message;
    @Column(name = "jour")
    private Date jour;


    public Feedback(String telephone, String message, Date jour) {
        this.telephone = telephone;
        this.message = message;
        this.jour = jour;
    }

    public Feedback(String telephone, String message) {
        this.telephone = telephone;
        this.message = message;
    }

    public Feedback() {
    }

    /**
     * @return
     */
    public List findList() {
        try {
            return JPA.em().createQuery("select feedback From Feedback feedback").getResultList();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * @param id
     * @return
     */
    public Feedback findById(Long id) {
        try {
            return (Feedback) JPA.em().createQuery("select feedback From Feedback feedback WHERE feedback.id = :id").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * @param feedback
     * @return
     */
    public String create(Feedback feedback) {
        feedback.setJour(new Date());
        String result = null;
        try {
            JPA.em().persist(feedback);
        } catch (Exception e) {
            System.out.println(e.toString());
            result = e.toString();
        }
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getJour() {
        return jour;
    }

    public void setJour(Date jour) {
        this.jour = jour;
    }
}
