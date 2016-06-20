package controllers;

import com.google.inject.Inject;
import models.Utilisateur;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;


public class UtilisateursController extends Controller {
    @Inject
    FormFactory formFactory;

    public Result inscription(){
        Form<Utilisateur> form = formFactory.form(Utilisateur.class).bindFromRequest();
        if (form.hasErrors()) {
            System.out.println("form erreur ");
            return ok("erreur");
        }else{
            System.out.println("form ok ");
            Utilisateur utilisateur = form.get();
            String result = utilisateur.create(utilisateur);
            if(result == null){
                System.out.println("result == null ");
                return ok("1");
            }else{
                System.out.println("result != null ");
                return ok("0");
            }
        }
    }

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
}

