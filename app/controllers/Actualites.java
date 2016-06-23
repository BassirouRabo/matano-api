package controllers;


import models.Actualite;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import pojo.ActualitePojo;

import javax.inject.Inject;

public class Actualites extends Controller {
    @Inject
    FormFactory formFactory;

    @Transactional
    public Result reads() {
        return ok(Json.toJson(new ActualitePojo().transformationListe(new Actualite().findList())));
    }

    @Transactional
    public Result read(Long id) {
        Actualite actualite = new Actualite().findById(id);
        if (actualite == null) {
            return ok("0");
        } else {
            return ok(Json.toJson(new ActualitePojo().transformation(actualite)));
        }
    }

    @Transactional
    public Result readsByEvenement(Long idEvenement) {
        return ok(Json.toJson(new ActualitePojo().transformationListe(new Actualite().findListByEvenement(idEvenement))));
    }

    @Transactional
    public Result create() {
        Form<Actualite> form = formFactory.form(Actualite.class).bindFromRequest();
        if (form.hasErrors()) {
            return ok("0");
        } else {
            Actualite actualite = form.get();
            String result = actualite.create(actualite);
            if (result == null) {
                return ok("1");
            } else {
                return ok("0");
            }
        }
    }

    @Transactional
    public Result update() {
        Form<Actualite> form = formFactory.form(Actualite.class).bindFromRequest();
        if (form.hasErrors()) {
            return ok("0");
        } else {
            Actualite actualite = form.get();
            String result = actualite.update(actualite);
            if (result == null) {
                return ok("1");
            } else {
                return ok("0");
            }
        }
    }

    @Transactional
    public Result delete(Long id) {
        String result = new Actualite().delete(id);
        if (result != null) {
            return ok("0");
        } else {
            return ok("1");
        }
    }

}
