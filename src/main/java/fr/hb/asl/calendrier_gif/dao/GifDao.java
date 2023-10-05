package fr.hb.asl.calendrier_gif.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.hb.asl.calendrier_gif.business.Gif;
import fr.hb.asl.calendrier_gif.business.Jour;
import fr.hb.asl.calendrier_gif.business.Utilisateur;
import fr.hb.asl.calendrier_gif.util.NbGifs;

@Repository
public interface GifDao extends JpaRepository<Gif, Long> {

	//
	// Cette méthode renvoie le nombre de Gifs mis en ligne par l'utilisateur donné
	// en paramètre
	// @param utilisateur
	// @return
	//

	int countByUtilisateur(Utilisateur utilisateur);

	@Query("FROM Gif WHERE dateHeureAjout between '2022-09-14' AND '2022-09-21'")
	List<Gif> findGifsAddedBetween14thAnd21stSeptember2022();

	@Query("""
			FROM Gif
			WHERE dateHeureAjout BETWEEN ?1 AND ?2""")
	List<Gif> findGifsAddedBetweenDates(LocalDateTime dateHeureDebut, LocalDateTime dateHeureFin);

	@Query("""
			SELECT new fr.hb.asl.calendrier_gif.util.NbGifs(year(g.dateHeureAjout), month(g.dateHeureAjout), COUNT(*) as nbGifs)
			FROM Gif g
			GROUP by year(g.dateHeureAjout), month(g.dateHeureAjout)
			""")
	List<NbGifs> findNbGifsByYearAndMonth();

	@Query("""
			FROM Gif
			WHERE month(current_date) = month(dateHeureAjout) AND year(current_date) = year(dateHeureAjout)
			""")
	List<Gif> findGifsAddedThisMonth(LocalDateTime dateHeureAjout);

	@Query("SELECT g as gif, size(g.reactions) as nb FROM Gif g order by size(g.reactions) DESC")
	List<Map<Gif, Integer>> getGifsOrderByReactionsDesc();

	@Query(value = "FROM Gif g ORDER BY size(g.reactions) DESC")
	List<Gif> findTopByReactions();

	// Cette méthode renvoie tous les gifs postés par l'utilisateur donné en
	// paramètre
	// @param utilisateur
	// @return

	List<Gif> findByUtilisateur(Utilisateur utilisateur);

	List<Gif> findByUtilisateurId(Long id);

	List<Gif> findByUtilisateurNom(String nom);

	Gif findLast1ByJour(Jour jour);

	List<Gif> findByLegendeContaining(String legende);

	List<Gif> findByReactionsDateHeureBetween(LocalDateTime dateDebut, LocalDateTime dateFin);

	List<Gif> findByReactionsNotEmptyAndReactions_DateHeureBetween(LocalDateTime dateHeureDebut,
			LocalDateTime dateHeureFin);

}
