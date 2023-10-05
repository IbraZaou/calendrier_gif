package fr.hb.asl.calendrier_gif.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import fr.hb.asl.calendrier_gif.business.Utilisateur;
import fr.hb.asl.calendrier_gif.dao.UtilisateurDao;
import lombok.AllArgsConstructor;
@AllArgsConstructor
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
 
	private UtilisateurDao utilisateurDao;
	private final HttpSession httpSession;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {		   
		   
		   String username = authentication.getName();
	       Utilisateur utilisateur = utilisateurDao.findUtilisateurByEmail(username);

	        // Réinitialise le nombre d'essais infructueux à zéro après une connexion réussie
	        if (utilisateur != null) {
	            utilisateur.setNbErreur(0);
	            utilisateurDao.save(utilisateur);
	        }

	        // Redirigez l'utilisateur vers la page d'accueil
	        httpSession.setAttribute("utilisateur", utilisateur);
	        response.sendRedirect("/calendrier");
	    }
		
	}
