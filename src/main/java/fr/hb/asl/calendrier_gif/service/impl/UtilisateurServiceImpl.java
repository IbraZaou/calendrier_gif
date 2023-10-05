package fr.hb.asl.calendrier_gif.service.impl;

import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.hb.asl.calendrier_gif.business.Theme;
import fr.hb.asl.calendrier_gif.business.Utilisateur;
import fr.hb.asl.calendrier_gif.dao.UtilisateurDao;
import fr.hb.asl.calendrier_gif.dto.UtilisateurDto;
import fr.hb.asl.calendrier_gif.mapper.UtilisateurMapper;
import fr.hb.asl.calendrier_gif.service.UtilisateurService;
import fr.hb.asl.calendrier_gif.util.NbInscrits;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService,UserDetailsService {

	private final UtilisateurDao utilisateurDao;
	private  final PasswordEncoder passwordEncoder;
	// private final ThemeService themeService;

	@Override
	public Utilisateur enregistrerUtilisateur(Utilisateur utilisateur) {
		return utilisateurDao.save(utilisateur);
	}

	@Override
	public Utilisateur enregistrerUtilisateur(UtilisateurDto utilisateurDto) {
		Utilisateur utilisateur = UtilisateurMapper.INSTANCE.toEntity(utilisateurDto);
		return enregistrerUtilisateur(utilisateur);
	}

	@Override
	public Utilisateur ajouterUtilisateur(String nom, String prenom, String email, String motDePasse,
			Theme recupererTheme) {
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setNom(nom);
		utilisateur.setPrenom(prenom);
		utilisateur.setEmail(email);
		utilisateur.setMotDePasse(passwordEncoder.encode(motDePasse));
		utilisateur.setTheme(recupererTheme);
		return utilisateurDao.save(utilisateur);
	}
	
	
	@Override
	public Utilisateur ajouterUtilisateur(String nom, String prenom, String email, String motDePasse) {
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setNom(nom);
		utilisateur.setPrenom(prenom);
		utilisateur.setEmail(email);
		utilisateur.setMotDePasse(passwordEncoder.encode(motDePasse));

		return utilisateurDao.save(utilisateur);
	}

	@Override
	public Utilisateur recupererUtilisateur(String email, String motDePasse) {
		return utilisateurDao.findLastByEmailAndMotDePasse(email, motDePasse);
	}

	@Override
	public List<Utilisateur> recupererUtilisateursAyantReagiAuMoinsCinqFois() {
		return utilisateurDao.findUtilisateursHavingAtLeastFiveReactions();
	}

	@Override
	public Utilisateur recupererUtilisateur(Long id) {
		return utilisateurDao.findById(id).orElse(null);
	}

	@Override
	public Utilisateur ajouterUtilisateurAleatoire() {
		return null;
	}

	@Override
	public List<NbInscrits> recupererNbInscrits() {
		return utilisateurDao.findNbInscrits();
	}

	@Override
	public List<Utilisateur> recupererUtilisateurs() {
		return utilisateurDao.findAll();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    final Utilisateur utilisateur = utilisateurDao.findUtilisateurByEmail(username);

	    if (utilisateur == null) {
	        throw new UsernameNotFoundException("User not found");
	    }

	    String role = "USER"; 
	    UserDetails user = User.builder()
	            .username(utilisateur.getEmail())
	            .password(utilisateur.getMotDePasse())
	            .roles(role)
	            .accountLocked(utilisateur.isAccountNonLocked())
	            .build();

	    return user;
	}


	@Override
	public Utilisateur recupererUtilisateur(String email) {
		return utilisateurDao.findByEmail(email);
	}
	
	

	
	
}
