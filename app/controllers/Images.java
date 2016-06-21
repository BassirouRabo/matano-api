package controllers;

import models.Image;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;


public class Images extends Controller {
    @Inject
    FormFactory formFactory;

    @Transactional
    public Result reads() {
        return ok(Json.toJson(new Image().findList()));
    }

    @Transactional
    public Result read(Long id) {
        Image image = new Image().findById(id);
        if (image == null) {
            return ok("0");
        } else {
            return ok(Json.toJson(image));
        }
    }

    @Transactional
    public Result readsByEvenement(Long idEvenement) {
        return ok(Json.toJson(new Image().findListByEvenement(idEvenement)));
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
