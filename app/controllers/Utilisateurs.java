package controllers;

import models.Utilisateur;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;


public class Utilisateurs extends Controller {
    @Inject
    FormFactory formFactory;

    @Transactional
    public Result reads(){
        return ok(Json.toJson(new Utilisateur().findList()));
    }

    @Transactional
    public Result read(Long id){
        Utilisateur utilisateur = new Utilisateur().findById(id);
        if(utilisateur == null){
            return ok("0");
        }else{
            return ok(Json.toJson(utilisateur));
        }
    }

    @Transactional
    public Result readByTelephone(String telephone){
        Utilisateur utilisateur = new Utilisateur().findByTelephone(telephone);
        if(utilisateur == null){
            return ok("0");
        }else{
            return ok(Json.toJson(utilisateur));
        }
    }

    @Transactional
    public Result inscription(){
        Form<Utilisateur> form = formFactory.form(Utilisateur.class).bindFromRequest();
        if (form.hasErrors()) {
            return ok("0");
        }else{
            Utilisateur utilisateur = form.get();
            String result = utilisateur.create(utilisateur);
            if(result == null){
                return ok("1");
            }else{
                return ok("0");
            }
        }
    }

    @Transactional
    public Result connexion(){
        Form<Utilisateur> form = formFactory.form(Utilisateur.class).bindFromRequest();
        if (form.hasErrors()) {
            return ok("0");
        }else{
            Utilisateur utilisateur = form.get();
            Utilisateur  utilisateurExiste =  utilisateur.findByTelephoneAndPassword(utilisateur.getTelephone(), utilisateur.getPassword());

            if(utilisateurExiste == null){
                return ok("0");
            }else{
                return ok("1");
            }
        }
    }

    @Transactional
    public Result update(){
        Form<Utilisateur> form = formFactory.form(Utilisateur.class).bindFromRequest();
        if (form.hasErrors()) {
            return ok("0");
        }else{
            Utilisateur utilisateur = form.get();
            String result = utilisateur.update(utilisateur);
            if (result == null) {
                return ok("1");
            } else {
                return ok("0");
            }
        }
    }

    @Transactional
    public Result delete(Long id) {
        String result = new Utilisateur().delete(id);
        if (result != null) {
            return ok("0");
        } else {
            return ok("1");
        }
    }
}

