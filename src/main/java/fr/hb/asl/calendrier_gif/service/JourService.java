package fr.hb.asl.calendrier_gif.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.hb.asl.calendrier_gif.business.Jour;
import fr.hb.asl.calendrier_gif.dto.JourDto;

public interface JourService {
	Jour ajouterJour(LocalDate date, int nbPoints);

	Jour enregistrerJour(Jour jour);

	Jour enregistrerJour(JourDto jourDto);

	Page<Jour> recupererJours(Pageable pageable);

	List<Jour> recupererJours();

	Jour recupererJour(LocalDate idJour);

	List<Jour> recupererJoursDuMoisEnCours();

	Jour mettreAJourJour(LocalDate date, int nouveauNbPoints);

	boolean supprimerJour(LocalDate date);

	long compterJours();
}
