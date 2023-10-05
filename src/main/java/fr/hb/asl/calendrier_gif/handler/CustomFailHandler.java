package fr.hb.asl.calendrier_gif.handler;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import fr.hb.asl.calendrier_gif.business.Utilisateur;
import fr.hb.asl.calendrier_gif.dao.UtilisateurDao;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
public class CustomFailHandler implements AuthenticationFailureHandler {

	private UtilisateurDao utilisateurDao;

	@Override
	public void onAuthenticationFailure(javax.servlet.http.HttpServletRequest request,
			javax.servlet.http.HttpServletResponse response, AuthenticationException exception)
			throws IOException, javax.servlet.ServletException {
		
        String username = request.getParameter("username");
        Utilisateur utilisateur = utilisateurDao.findUtilisateurByEmail(username);

        if (utilisateur != null) {
            utilisateur.setNbErreur(utilisateur.getNbErreur() + 1);

            if (utilisateur.getNbErreur() >= 3) {
                utilisateur.setAccountNonLocked(false);
            }

            utilisateurDao.save(utilisateur);
        }
        
		log.info("Authentication failed: " + exception.getMessage() + request.getParameter("username"));
        response.sendRedirect("index?notification=Email%20ou%20mot%20de%20passe%20incorrect");
        //TODO creer une jsp pour compte bloquer
//        if (utilisateur != null && !utilisateur.isAccountNonLocked()) {
//            response.sendRedirect("compteBloque");
//        }
    }
		
}
