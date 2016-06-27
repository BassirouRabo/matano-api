package controllers;


import models.Actualite;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import pojo.ActualitePojo;
import utils.Secured;
import views.html.actualite;
import views.html.actualites;

import javax.inject.Inject;

public class Actualites extends Controller {
    @Inject
    FormFactory formFactory;

    @Transactional
    public Result reads() {
        return ok(Json.toJson(new ActualitePojo().transformationListe(new Actualite().findList())));
    }

    @Security.Authenticated(Secured.class)
    @Transactional
    public Result read(Long id) {
        Actualite actualiteExiste = new Actualite().findById(id);
        if (actualiteExiste == null) {
            return ok(actualite.render(new Actualite()));
        } else {
            return ok(actualite.render(actualiteExiste));
        }
    }

    @Transactional
    public Result readsByEvenement(Long idEvenement) {
        return ok(Json.toJson(new ActualitePojo().transformationListe(new Actualite().findListByEvenement(idEvenement))));
    }

    @Security.Authenticated(Secured.class)
    @Transactional
    public Result readsByPartenaire(Long idPartenaire) {
        return ok(actualites.render((new Actualite().findListByPartenaire(idPartenaire))));
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
