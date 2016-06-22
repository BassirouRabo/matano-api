package controllers;

import models.Participant;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import pojo.ParticipantPojo;

import javax.inject.Inject;

public class Participants extends Controller {
    @Inject
    FormFactory formFactory;

    @Transactional
    public Result reads() {
        return ok(Json.toJson(new ParticipantPojo().transformationListe(new Participant().findList())));
    }

    @Transactional
    public Result read(Long id) {
        Participant participant = new Participant().findById(id);
        if (participant == null) {
            return ok("0");
        } else {
            return ok(Json.toJson(new ParticipantPojo().transformation(participant)));
        }
    }

    @Transactional
    public Result readsByEvenement(Long idEvenement) {
        return ok(Json.toJson(new ParticipantPojo().transformationListe(new Participant().findListByEvenement(idEvenement))));
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
    public Result deleteByEvenementAndUtilisateur(Long idEvenement, Long idUtilisateur){
        String result = new Participant().deleteByEvenementAndUtilisateur(idEvenement, idUtilisateur);
        if (result != null) {
            return ok("0");
        } else {
            return ok("1");
        }
    }
}
