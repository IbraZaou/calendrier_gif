package fr.hb.asl.calendrier_gif.service;

import java.util.List;

import fr.hb.asl.calendrier_gif.business.Theme;
import fr.hb.asl.calendrier_gif.business.Utilisateur;
import fr.hb.asl.calendrier_gif.dto.UtilisateurDto;
import fr.hb.asl.calendrier_gif.util.NbInscrits;

public interface UtilisateurService {
 
	Utilisateur enregistrerUtilisateur(Utilisateur utilisateur);

	Utilisateur enregistrerUtilisateur(UtilisateurDto utilisateurDto);

	Utilisateur ajouterUtilisateur(String nom, String prenom, String email, String motDePasse, Theme nomTheme);
	Utilisateur ajouterUtilisateur(String nom, String prenom, String email, String motDePasse);


	Utilisateur recupererUtilisateur(String email, String motDePasse);

	List<Utilisateur> recupererUtilisateursAyantReagiAuMoinsCinqFois();

	Utilisateur recupererUtilisateur(Long id);

	Utilisateur ajouterUtilisateurAleatoire();

	List<NbInscrits> recupererNbInscrits();

	List<Utilisateur> recupererUtilisateurs();
	
	Utilisateur recupererUtilisateur(String email);

}
