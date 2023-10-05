package fr.hb.asl.calendrier_gif.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.hb.asl.calendrier_gif.business.Emotion;

@Repository
public interface EmotionDao extends JpaRepository<Emotion, Long> {

	// Requêtes par dérivation ( "query method" ): pas besoin de rajouter de @Query
	// Spring DATA essaye de construire une requête HQL en analysant le nom de la
	// méthode en Java

	Emotion findByNom(String nom);

	Emotion findByCode(String code);

	List<Emotion> findFirst2ByNomContainingIgnoreCase(String nom);

	List<Emotion> findByNomStartingWith(String debutDuNomRecherche);

	@Query(value = "FROM Emotion WHERE lower(nom) LIKE 's%'")
	List<Emotion> findEmotionHavingNameStartingWithS();

	@Query(value = "UPDATE Emotion SET nom = upper(nom)")
	void UpdateNom();

	long deleteByNomStartingWith(String debut);

	long countByNomStartingWith(String debut);

	boolean existsByNom(String nom);
}
