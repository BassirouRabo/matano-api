package controllers;

import models.Partenaire;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import utils.Secured;
import views.html.partenaire;
import views.html.partenaires;

import javax.inject.Inject;

public class Partenaires extends Controller {
    @Inject
    FormFactory formFactory;

    @Security.Authenticated(Secured.class)
    @Transactional
    public Result reads() {
        return ok(partenaires.render(new Partenaire().findList()));
    }

    @Security.Authenticated(Secured.class)
    @Transactional
    public Result read(Long idPartenaire) {
        Partenaire partenaireExite = new Partenaire().findById(idPartenaire);

        if (partenaireExite == null) {
            return ok(partenaire.render(new Partenaire()));
        } else {
            return ok(partenaire.render(partenaireExite));
        }
    }


    @Transactional
    public Result connexion() {
        Form<Partenaire> form = formFactory.form(Partenaire.class).bindFromRequest();
        if (form.hasErrors()) {
            return redirect("/");
        } else {
            Partenaire partenaire = form.get();
            Partenaire partenaireExiste = partenaire.findByTelephoneAndPassword(partenaire.getTelephone(), partenaire.getPassword());

            if (partenaireExiste == null) {
                return redirect("/");
            } else {
                session("id", partenaireExiste.getId().toString());
                session("email", partenaireExiste.getEmail());
                return redirect("/evenements/partenaires/" + partenaireExiste.getId());
            }
        }
    }
}
