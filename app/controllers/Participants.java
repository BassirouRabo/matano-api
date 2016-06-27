package controllers;

import models.Participant;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import pojo.ParticipantPojo;
import utils.Secured;
import views.html.participant;
import views.html.participants;

import javax.inject.Inject;

public class Participants extends Controller {
    @Inject
    FormFactory formFactory;

    @Transactional
    public Result reads() {
        return ok(Json.toJson(new ParticipantPojo().transformationListe(new Participant().findList())));
    }

    @Security.Authenticated(Secured.class)
    @Transactional
    public Result read(Long id) {
        Participant participantExiste = new Participant().findById(id);
        if (participantExiste == null) {
            return ok(participant.render(new Participant()));
        } else {
            return ok(participant.render(participantExiste));
        }
    }

    @Transactional
    public Result readsByEvenement(Long idEvenement) {
        return ok(Json.toJson(new ParticipantPojo().transformationListe(new Participant().findListByEvenement(idEvenement))));
    }

    @Security.Authenticated(Secured.class)
    @Transactional
    public Result readsByPartenaire(Long idPartenaire) {
        return ok(participants.render(new Participant().findListByPartenaire(idPartenaire)));
    }


    @Transactional
    public Result create() {
        Form<Participant> form = formFactory.form(Participant.class).bindFromRequest();
        if (form.hasErrors()) {
            return ok("0");
        } else {
            Participant participant = form.get();
            String result = participant.create(participant);
            if (result == null) {
                return ok("1");
            } else {
                return ok("0");
            }
        }
    }


    @Transactional
    public Result delete(Long id) {
        String result = new Participant().delete(id);
        if (result != null) {
            return ok("0");
        } else {
            return ok("1");
        }
    }

    @Transactional
    public Result deleteByEvenementAndUtilisateur(Long idEvenement, Long idUtilisateur) {
        String result = new Participant().deleteByEvenementAndUtilisateur(idEvenement, idUtilisateur);
        if (result != null) {
            return ok("0");
        } else {
            return ok("1");
        }
    }
}
