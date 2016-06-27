package controllers;

import models.Image;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import pojo.ImagePojo;
import utils.Secured;
import views.html.image;
import views.html.images;

import javax.inject.Inject;


public class Images extends Controller {
    @Inject
    FormFactory formFactory;

    @Transactional
    public Result reads() {
        return ok(Json.toJson(new ImagePojo().transformationListe(new Image().findList())));
    }

    @Security.Authenticated(Secured.class)
    @Transactional
    public Result read(Long id) {
        Image imageExiste = new Image().findById(id);
        if (imageExiste == null) {
            return ok(image.render(new Image()));
        } else {
            return ok(image.render(imageExiste));
        }
    }

    @Transactional
    public Result readsByEvenement(Long idEvenement) {
        return ok(Json.toJson(new ImagePojo().transformationListe(new Image().findListByEvenement(idEvenement))));
    }

    @Security.Authenticated(Secured.class)
    @Transactional
    public Result readsByPartenaire(Long idPartenaire) {
        return ok(images.render(new Image().findListByPartenaire(idPartenaire)));
    }

    @Transactional
    public Result create() {
        Form<Image> form = formFactory.form(Image.class).bindFromRequest();
        if (form.hasErrors()) {
            return ok("0");
        } else {
            Image image = form.get();
            String result = image.create(image);
            if (result == null) {
                return ok("1");
            } else {
                return ok("0");
            }
        }
    }

    @Transactional
    public Result delete(Long id) {
        String result = new Image().delete(id);
        if (result != null) {
            return ok("0");
        } else {
            return ok("1");
        }
    }
}
