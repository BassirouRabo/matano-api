package controllers;

import models.Commentaire;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;


public class Commentaires extends Controller {
    @Inject
    FormFactory formFactory;

    @Transactional
    public Result reads() {
        return ok(Json.toJson(new Commentaire().findList()));
    }

    @Transactional
    public Result read(Long id) {
        Commentaire commentaire = new Commentaire().findById(id);
        if (commentaire == null) {
            return ok("0");
        } else {
            return ok(Json.toJson(commentaire));
        }
    }

    @Transactional
    public Result readsByEvenement(Long idEvenement) {
        return ok(Json.toJson(new Commentaire().findListByEvenement(idEvenement)));
    }

    @Transactional
    public Result create() {
        Form<Commentaire> form = formFactory.form(Commentaire.class).bindFromRequest();
        if (form.hasErrors()) {
            return ok("0");
        } else {
            Commentaire commentaire = form.get();
            String result = commentaire.create(commentaire);
            if (result == null) {
                return ok("1");
            } else {
                return ok("0");
            }
        }
    }

    @Transactional
    public Result update() {
        Form<Commentaire> form = formFactory.form(Commentaire.class).bindFromRequest();
        if (form.hasErrors()) {
            return ok("0");
        } else {
            Commentaire commentaire = form.get();
            String result = commentaire.update(commentaire);
            if (result == null) {
                return ok("1");
            } else {
                return ok("0");
            }
        }
    }


    @Transactional
    public Result delete(Long id) {
        String result = new Commentaire().delete(id);
        if (result != null) {
            return ok("0");
        } else {
            return ok("1");
        }
    }
}
