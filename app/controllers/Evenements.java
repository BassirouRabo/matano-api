package controllers;

import com.google.inject.Inject;
import models.Evenement;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;


public class Evenements extends Controller {
    @Inject
    FormFactory formFactory;

    @Transactional
    public Result reads() {
        return ok(Json.toJson(new Evenement().findList()));
    }

    @Transactional
    public Result readsByCategorie(String categorie) {
        return ok(Json.toJson(new Evenement().findListByCategorie(categorie)));
    }

    @Transactional
    public Result read(Long id) {
        Evenement evenement = new Evenement().findById(id);
        if (evenement == null) {
            return ok("0");
        } else {
            return ok(Json.toJson(evenement));
        }

    }

    @Transactional
    public Result create() {
        Form<Evenement> form = formFactory.form(Evenement.class).bindFromRequest();
        if (form.hasErrors()) {
            return ok("0");
        } else {
            Evenement evenement = form.get();
            String result = evenement.create(evenement);
            if (result == null) {
                return ok("1");
            } else {
                return ok("0");
            }
        }
    }

    @Transactional
    public Result update() {
        Form<Evenement> form = formFactory.form(Evenement.class).bindFromRequest();
        if (form.hasErrors()) {
            return ok("0");
        } else {
            Evenement evenement = form.get();
            String result = evenement.update(evenement);
            if (result == null) {
                return ok("1");
            } else {
                return ok("0");
            }
        }
    }

    @Transactional
    public Result delete(Long id) {
        String result = new Evenement().delete(id);
        if (result != null) {
            return ok("0");
        } else {
            return ok("1");
        }
    }
}
