package pojo;

import models.Participant;

import java.util.List;
import java.util.stream.Collectors;

public class ParticipantPojo {
    private Long id;
    private String nom;
    private String prenom;
    private String telephone;
    private String image;

    public ParticipantPojo(Long id, String nom, String prenom, String telephone, String image) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.image = image;
    }

    public ParticipantPojo() {
    }

    /**
     * @param participant
     * @return
     */
    public ParticipantPojo transformation(Participant participant) {
        return new ParticipantPojo(participant.getId(), participant.getUtilisateur().getNom(), participant.getUtilisateur().getPrenom(), participant.getUtilisateur().getTelephone(), participant.getUtilisateur().getImage());
    }

    /**
     * @param participants
     * @return
     */
    public List<ParticipantPojo> transformationListe(List<Participant> participants) {

        return participants.stream().map(participant -> new ParticipantPojo().transformation(participant)).collect(Collectors.toList());
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
