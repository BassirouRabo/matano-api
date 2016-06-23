package pojo;


import models.Actualite;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class ActualitePojo {
    private Long id;
    private String jour;
    private String actualite;

    public ActualitePojo(Long id, String jour, String actualite) {
        this.id = id;
        this.jour = jour;
        this.actualite = actualite;
    }

    public ActualitePojo() {
    }

    public ActualitePojo transformation(Actualite actualite) {
        return new ActualitePojo(actualite.getId(), new SimpleDateFormat("dd/MM/yyyy").format(actualite.getJour()), actualite.getActualite());
    }

    public List<ActualitePojo> transformationListe(List<Actualite> actualites) {
        return actualites.stream().map(actualite -> new ActualitePojo().transformation(actualite)).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public String getActualite() {
        return actualite;
    }

    public void setActualite(String actualite) {
        this.actualite = actualite;
    }
}
