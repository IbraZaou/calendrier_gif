package fr.hb.asl.calendrier_gif.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.hb.asl.calendrier_gif.business.Jour;
import fr.hb.asl.calendrier_gif.business.Utilisateur;

@Repository
public interface JourDao extends JpaRepository<Jour, LocalDate> {

	@Query("FROM Jour WHERE month(date)=month(current_date()) AND year(date)=year(current_date())")
	List<Jour> findDaysOfCurrentMonth();

	// La méthode renvoie un objet de type Integer, Spring va arrondir
	// automatiquement
	@Query("SELECT avg(nbPoints) FROM Jour WHERE month(date) = 9 AND year(date) = 2022")
	Integer findAverageOfPointsInSeptember2022();

	@Query("""
			SELECT avg(nbPoints)
			FROM Jour
			WHERE month(date) = :leMois AND year(date) = :lAnnee
			""")
	Integer findAverageOfPoints(@Param("lAnnee") int annee, @Param("leMois") int mois);

	@Query("SELECT g.jour FROM Gif g WHERE g.legende LIKE %:legende%")
	List<Jour> findJourByGifLegende(@Param(value = "legende") String legende);

	// Méthode qui renvoie tous les jours sur lesquels l'utilisateur donné en
	// paramètre
	// à placer un gif
	List<Jour> findByGifUtilisateur(Utilisateur utilisateur);

	List<Jour> findByGifIsNull();

	List<Jour> findByNbPoints(int nbPoints);

	List<Jour> findByGifIsNullAndNbPointsGreaterThanEqual(int min);

	List<Jour> findByGifIsNullAndNbPointsGreaterThanEqualAndDateBefore(int min, LocalDate date);

	@Query("""
			SELECT max(date)
			FROM Jour
			""")
	LocalDate findLastDate();

	Jour findFirstByOrderByDateDesc();

	Page<Jour> findByDateBetween(LocalDate dateDebut, LocalDate dateFin, Pageable pageable);
}
