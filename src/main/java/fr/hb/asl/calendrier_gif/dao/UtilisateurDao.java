package fr.hb.asl.calendrier_gif.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.hb.asl.calendrier_gif.business.Theme;
import fr.hb.asl.calendrier_gif.business.Utilisateur;
import fr.hb.asl.calendrier_gif.util.NbInscrits;

@Repository
public interface UtilisateurDao extends JpaRepository<Utilisateur, Long> {
	// Il est possible de déclarer des méthodes spécifiques
	@Query(
	// Nouveautés de Java 15 : les text blocks
	"""
			FROM Utilisateur
			WHERE prenom='Valentin'
			""")
	List<Utilisateur> findAllUsersHavingFirstNameValentin();

	@Query("""
			FROM Utilisateur
			WHERE nbPoints<400
			""")
	List<Utilisateur> findAllUsersHavingLowPoints();

	@Query(value = """
			FROM Utilisateur
			WHERE theme.nom = 'Dark'
			""")
	List<Utilisateur> findUsersHavingChosenDarkTheme();

	@Query(value = """
			SELECT nom || ' ' || prenom as NomEtPrenom
			      FROM Utilisateur
			  WHERE month(dateHeureInscription) = 10
			  		AND year(dateHeureInscription) = 2022
			  		AND email LIKE '%hb.com'
			  """)
	List<String> findAllUsersWhoRegisteredInOctober2022();

	// Query method (feature de Spring Data)
	// Spring Data va interpréter le nom de la méthode qui n'est pas annotée @Query
	// (requête par dérivation)
	Utilisateur findByEmailAndMotDePasse(String email, String motDePasse);

	/**
	 * Cette méthode renvoie tous les utilisateurs ayant choisi le thème donné en
	 * paramètre
	 * 
	 * @param theme
	 * @return
	 */
	// Requête par dérivation
	List<Utilisateur> findByTheme(Theme theme);

	// Méthode qui renvoie tous les utilisateurs ayant choisi le thème dont le nom
	// est donné en paramètre
	List<Utilisateur> findByThemeNom(String nom);

	/**
	 * Méthode qui renvoie la liste des utilisateurs dont le solde est inférieur au
	 * paramètre
	 */
	List<Utilisateur> findByNbPointsLessThan(int seuil);

	/**
	 * Méthode qui renvoie la liste des utilisateurs dont le solde est inférieur au
	 * paramètre
	 */
	List<Utilisateur> findByNbPointsLessThanAndNbPointsGreaterThan(int seuilMax, int seuilMin);

	/**
	 * Méthode qui renvoie les utilisateurs qui se sont inscrits pendant le mois et
	 * l'annee donnés en paramètre
	 * 
	 * @param mois
	 * @param annee
	 * @return
	 */
	@Query("""
			SELECT prenom
			FROM Utilisateur
					WHERE month(dateHeureInscription)=:mois
					  	  AND year(dateHeureInscription)=:annee
					ORDER BY prenom
				""")
	List<String> findAllUsers(@Param("mois") int mois, @Param("annee") int annee);

	/**
	 * Cette méthode renvoie les utilisateurs ayant choisi le thème donné en
	 * paramètre et s'étant inscrits entre les deux date et heure données en
	 * paramètre
	 * 
	 * @param theme
	 * @param dateHeureDebut
	 * @param dateHeureFin
	 * @return une liste d'utilisateurs
	 */
	List<Utilisateur> findByThemeAndDateHeureInscriptionBetween(Theme theme, LocalDateTime dateHeureDebut,
			LocalDateTime dateHeureFin);

	@Query(value = """
			FROM Utilisateur
			WHERE id NOT IN
				(SELECT DISTINCT utilisateur.id
				 FROM Gif)""")
	List<Utilisateur> findNonContribuitingUsers();

	@Query(value = "FROM Utilisateur ORDER BY prenom")
	List<Utilisateur> findAllUsersSortedByPrenom();

	Utilisateur findByEmail(String email);

	@Query("SELECT MONTH(u.dateHeureInscription), YEAR(u.dateHeureInscription), COUNT(u.id) FROM Utilisateur u GROUP BY MONTH(u.dateHeureInscription), YEAR(u.dateHeureInscription) ORDER BY YEAR(u.dateHeureInscription) DESC, MONTH(u.dateHeureInscription) DESC")
	List<Object[]> findCreatedUserPerMonth();

	/**
	 * Cette méthode renvoie une page d'utilisateur Elle a en paramètre un objet de
	 * type Pageable correspondant à une demande de page
	 * 
	 * @param filtreEmail
	 * @param pageable
	 * @return
	 */
	Page<Utilisateur> findByEmailContaining(String filtreEmail, Pageable pageable);

	Utilisateur findByIdAndEmail(Long id, String email);

	// ajout d'une méthode annotée @Query
	@Query("""
			SELECT u
			FROM Utilisateur u, Reaction r
			WHERE r.utilisateur=u
			GROUP BY r.utilisateur
			HAVING COUNT(r.utilisateur)>=5
			""")
	// @Query("FROM Utilisateur u WHERE (select count(*) from Reaction r where
	// r.utilisateur = u) >= 5")
	List<Utilisateur> findUtilisateursHavingAtLeastFiveReactions();

	@Query("""
			FROM Utilisateur
			WHERE theme.nom = 'Dark'
			""")
	List<Utilisateur> findUtilisateursUsingDarkTheme();

	/**
	 * Cette méthode renvoie le nombre d'inscrits par année et par mois Elle utilise
	 * la classe util NbInscrits
	 */

	// Projection caractérisée par la syntaxe : "SELECT new"
	@Query(value = "SELECT new fr.hb.asl.calendrier_gif.util.NbInscrits(year(u.dateHeureInscription), month(u.dateHeureInscription), COUNT(*) as nbutilisateurs) "
			+ "FROM Utilisateur u GROUP BY year(u.dateHeureInscription), month(u.dateHeureInscription) "
			+ "ORDER BY year(u.dateHeureInscription), month(u.dateHeureInscription)")
	List<NbInscrits> findNbInscrits();

	/**
	 * Methode qui renvoie une page d'utilisateurs s'étant inscrits entre les deux
	 * dates données en paramètre
	 * 
	 * @param dateHeureDebut
	 * @param dateHeureFin
	 * @param pageable
	 * @return
	 */
	Page<Utilisateur> findByDateHeureInscriptionBetween(LocalDateTime dateHeureDebut, Pageable pageable,
			LocalDateTime dateHeureFin);

	boolean existsByEmail(String email);

	Utilisateur findLastByEmailAndMotDePasse(String email, String motDePasse);

	// Méthode qui renvoie la liste des utilisateurs qui n'ont pas encore ajouté
	// d'image
	List<Utilisateur> findByGifsIsNotNullAndGifsIsEmpty();

	Utilisateur findUtilisateurByEmail(String username);
}
