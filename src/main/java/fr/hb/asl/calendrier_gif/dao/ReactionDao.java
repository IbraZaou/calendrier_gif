package fr.hb.asl.calendrier_gif.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.hb.asl.calendrier_gif.business.Emotion;
import fr.hb.asl.calendrier_gif.business.Gif;
import fr.hb.asl.calendrier_gif.business.Jour;
import fr.hb.asl.calendrier_gif.business.Reaction;
import fr.hb.asl.calendrier_gif.business.Utilisateur;

@Repository
public interface ReactionDao extends JpaRepository<Reaction, Long> {

	//
	// Méthode qui renvoie la liste des toutes les réactions du Gif donné en
	// paramètre
	// @param gif
	// @return
	//

	List<Reaction> findByGif(Gif gif);

	Reaction findLastByGifAndUtilisateurAndEmotion(Gif gif, Utilisateur utilisateur, Emotion emotion);

	// Spring Data va interpréter le nom de la méthode et le traduire en HQL
	List<Reaction> findByDateHeureBetween(LocalDateTime dateDebut, LocalDateTime dateFin);

	@Query("""
			SELECT r
			FROM Reaction r
			WHERE r.id = :id and r.gif.jour.date >= :dateDebut and r.gif.jour.date < :dateFin and r.utilisateur.email like :email
			""")
	List<Reaction> findFirstByIdAndDateAndUtilisateurEmailLike(@Param("id") Long idReaction,
			@Param("dateDebut") LocalDateTime dateDebut, @Param("dateFin") LocalDateTime dateFin,
			@Param("email") String email);

	List<Reaction> findLast5ByGif(Gif gif);

	List<Reaction> findByGifJour(Jour jour);
}
