package controllers;

import models.Commentaire;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import pojo.CommentairePojo;
import utils.Secured;
import views.html.commentaire;
import views.html.commentaires;
import views.html.commentairess;

import javax.inject.Inject;


public class Commentaires extends Controller {
    @Inject
    FormFactory formFactory;

    @Transactional
    public Result reads() {
        return ok(Json.toJson(new CommentairePojo().tranformationListe(new Commentaire().findList())));
    }

    @Security.Authenticated(Secured.class)
    @Transactional
    public Result read(Long id) {
        Commentaire commentaireExiste = new Commentaire().findById(id);
        if (commentaireExiste == null) {
            return ok(commentaire.render(new Commentaire()));
        } else {
            return ok(commentaire.render(commentaireExiste));
        }
    }

    @Transactional
    public Result readsByEvenement(Long idEvenement) {
        return ok(Json.toJson(new CommentairePojo().tranformationListe(new Commentaire().findListByEvenement(idEvenement))));
    }

    @Security.Authenticated(Secured.class)
    @Transactional
    public Result readsByPartenaire(Long idPartenaire) {
        return ok(commentaires.render(new Commentaire().findListByPartenaire(idPartenaire)));
    }

    @Security.Authenticated(Secured.class)
    @Transactional
    public Result readsByEvenementAndPartenaire(Long idEvenement, Long idPartenaire) {
        return ok(commentairess.render(new Commentaire().findListByEvenementAndPartenaire(idEvenement, idPartenaire)));
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
