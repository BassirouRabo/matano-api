package controllers;

import models.Feedback;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;


public class Feedbacks extends Controller {
    @Inject
    FormFactory formFactory;

    @Transactional
    public Result reads() {
        return ok(Json.toJson(new Feedback().findList()));
    }

    @Transactional
    public Result create() {
        Form<Feedback> form = formFactory.form(Feedback.class).bindFromRequest();
        if (form.hasErrors()) {
            return ok("0");
        } else {
            Feedback feedback = form.get();
            String result = feedback.create(feedback);
            if (result == null) {
                return ok("1");
            } else {
                return ok("0");
            }
        }
    }

}
