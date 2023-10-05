package fr.hb.asl.calendrier_gif.initialisation;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import fr.hb.asl.calendrier_gif.business.Emotion;
import fr.hb.asl.calendrier_gif.business.Gif;
import fr.hb.asl.calendrier_gif.business.GifDistant;
import fr.hb.asl.calendrier_gif.business.Jour;
import fr.hb.asl.calendrier_gif.business.Reaction;
import fr.hb.asl.calendrier_gif.business.Theme;
import fr.hb.asl.calendrier_gif.business.Utilisateur;
import fr.hb.asl.calendrier_gif.dao.EmotionDao;
import fr.hb.asl.calendrier_gif.dao.GifDao;
import fr.hb.asl.calendrier_gif.dao.JourDao;
import fr.hb.asl.calendrier_gif.dao.ReactionDao;
import fr.hb.asl.calendrier_gif.dao.ThemeDao;
import fr.hb.asl.calendrier_gif.dao.UtilisateurDao;
import fr.hb.asl.calendrier_gif.util.NbGifs;
import fr.hb.asl.calendrier_gif.util.NbInscrits;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AjoutDonneesInitiales implements CommandLineRunner {

	private final EmotionDao emotionDao;
	private final ThemeDao themeDao;
	private final UtilisateurDao utilisateurDao;
	private final JourDao jourDao;
	private final GifDao gifDao;
	private final ReactionDao reactionDao;
	private PasswordEncoder passwordEncoder;

	private static Random random = new Random();
	private static FakeValuesService fakeValuesService = new FakeValuesService(new Locale("fr-FR"),
			new RandomService());
	private static Faker faker = new Faker(new Locale("fr-FR"));

	@Override

	public void run(String... args) throws Exception {
		Date dateHeureDebut = new Date();
		ajouterEmotions();
		ajouterThemes();
		ajouterJours();
		ajouterUtilisateurs();
		ajouterGifs();
		ajouterReactions();
		Date dateHeureFin = new Date();
		System.out.println("Données initiales ajoutées en "
				+ String.valueOf(dateHeureFin.getTime() - dateHeureDebut.getTime()) + " ms");

		jourDao.findDaysOfCurrentMonth().forEach(System.out::println);

		jourDao.findJourByGifLegende("cat").forEach(System.out::println);

		utilisateurDao.findNbInscrits().forEach(System.out::println);

		afficherStatistiques();

		// emotionDao.deleteByNomStartingWith("M");
		System.out.println("gifs:");
		gifDao.findByReactionsNotEmptyAndReactions_DateHeureBetween(LocalDateTime.of(2022, 12, 1, 0, 0),
				LocalDateTime.of(2023, 12, 31, 0, 0)).forEach(System.out::println);

	}

	private void afficherStatistiques() {

		List<Utilisateur> utilisateurs = utilisateurDao.findAllUsersHavingFirstNameValentin();
		for (Utilisateur utilisateur : utilisateurs) {
			System.out.println(utilisateur);
		}

		List<Utilisateur> utilisateurs2 = utilisateurDao.findAllUsersHavingLowPoints();
		for (Utilisateur utilisateur : utilisateurs2) {
			System.out.println(utilisateur);
		}

		utilisateurDao.findAllUsersWhoRegisteredInOctober2022().forEach(System.out::println);

		System.out.println("Nombre moyen de points=" + jourDao.findAverageOfPointsInSeptember2022());

		List<NbGifs> nbGifs = gifDao.findNbGifsByYearAndMonth();
		for (NbGifs nbGif : nbGifs) {
			System.out.println(nbGif);
		}

		int nbTotalInscrits = 0;
		List<NbInscrits> nbInscrits = utilisateurDao.findNbInscrits();
		for (NbInscrits nbInscrit : nbInscrits) {
			System.out.println(nbInscrit);
			nbTotalInscrits += nbInscrit.getNbInscrits();
		}
		System.out.println("Nombre total d'inscrits=" + nbTotalInscrits);

		for (Object[] o : utilisateurDao.findCreatedUserPerMonth()) {
			int mois = (int) o[0];
			int annee = (int) o[1];
			Long total = (Long) o[2];
			System.out.println(mois + "/" + annee + " - " + total);
		}

		System.out.println("dernier jour = " + jourDao.findFirstByOrderByDateDesc());

		utilisateurDao.findAllUsers(8, 2022).forEach(System.out::println);

		themeDao.findByTheme().forEach(System.out::println);

		// System.out.println(reactionDao.findLast5ReactionsByGifJourOrderByDateHeure(jourDao.findById(LocalDate.now()).orElse(null)));
		LocalDate dateDebut = LocalDate.of(2022, 6, 1);
		LocalDate dateFin = LocalDate.of(2022, 10, 30);

		// Pageable pageable = PageRequest.of(0, 5, Direction.DESC, "nbPoints")
		Sort sort = Sort.by(Direction.DESC, "nbPoints").and(Sort.by(Direction.ASC, "date"));
		PageRequest pageable = PageRequest.of(0, 7, sort);
		Page<Jour> pageDeJours = jourDao.findByDateBetween(dateDebut, dateFin, pageable);
		Iterator<Order> iterator = pageDeJours.getSort().iterator();
		while (iterator.hasNext()) {
			Order order = iterator.next();
			System.out.println(order.getProperty() + " direction : " + order.getDirection());
		}
		pageDeJours.getContent().forEach(System.out::println);
		System.out.println("Il y au total " + pageDeJours.getTotalPages() + " pages");

		int curseurArretDebut = 0;
		int curseurArretFin = 0;

		if (pageDeJours.getTotalElements() > 0) {
			curseurArretDebut = pageDeJours.getSize() * pageDeJours.getNumber() + 1;
		}
		curseurArretFin = pageDeJours.getNumberOfElements() + pageDeJours.getSize() * pageDeJours.getNumber();

		System.out.println(
				"Jour " + curseurArretDebut + " à " + curseurArretFin + " sur " + pageDeJours.getTotalElements());

		Pageable pageablebis = pageDeJours.nextPageable();
		System.out.println(jourDao.findByDateBetween(dateDebut, dateFin, pageablebis).getContent());

		String emailLike = "wa";
		Pageable pageable2 = PageRequest.of(0, 7);

		System.out.println("Affichage d'une page d'utilisateurs");
		Page<Utilisateur> page = utilisateurDao.findByEmailContaining(emailLike, pageable2);
		System.out.println("Il y au total " + page.getTotalPages() + " pages ");
		System.out.println(utilisateurDao.findByEmailContaining(emailLike, pageable2).getContent());

		// On passe à la page suivante
		Pageable pageSuivante = pageable2.next();
		System.out.println(pageSuivante.getPageNumber());
		System.out.println(utilisateurDao.findByEmailContaining(emailLike, pageSuivante));
		System.out.println(utilisateurDao.findByEmailContaining(emailLike, pageSuivante).getContent());

		System.out.println("émotions débutant par S : " + emotionDao.findEmotionHavingNameStartingWithS());

	}

	private void ajouterGifs() {
		if (gifDao.count() == 0) {
			List<Jour> jours = jourDao.findAll();
			List<Utilisateur> utilisateurs = utilisateurDao.findAll();
			Calendar calendar = Calendar.getInstance();

			for (int i = 0; i < 10; i++) {
				Utilisateur utilisateur = utilisateurs.remove(0);
				Jour jour = jours.remove(0);
				calendar.set(2020, 1, 1);
				Date dateDebut = calendar.getTime();
				calendar = Calendar.getInstance();
				Date dateFin = calendar.getTime();
				Date dateAleatoire = faker.date().between(dateDebut, dateFin);
				calendar.setTime(dateAleatoire);
				LocalDateTime dateHeureAjout = dateAleatoire.toInstant().atZone(ZoneId.systemDefault())
						.toLocalDateTime();
				Gif gif = new GifDistant(utilisateur, jour, "Wow !",
						"https://c.tenor.com/Qo_pth9wapcAAAAC/wow-eddy-wally.gif", dateHeureAjout);
				gifDao.save(gif);
				// On met à jour le solde de l'utilisateur
				utilisateur.setNbPoints(utilisateur.getNbPoints() - jour.getNbPoints());
				utilisateurDao.save(utilisateur);
			}
		}
	}

	private void ajouterReactions() {
		if (reactionDao.count() == 0) {

			List<Emotion> emotions = emotionDao.findAll();
			List<Gif> gifs = gifDao.findAll();
			List<Utilisateur> utilisateurs = utilisateurDao.findAll();
			for (int i = 0; i < 100; i++) {
				reactionDao.save(new Reaction(utilisateurs.get(random.nextInt(utilisateurs.size())),
						emotions.get(random.nextInt(emotions.size())), gifs.get(random.nextInt(gifs.size()))));
			}
		}
	}

	private void ajouterEmotions() {
		if (emotionDao.count() == 0) {
			emotionDao.save(new Emotion("Souriant", "😀"));
			emotionDao.save(new Emotion("Monocle", "🧐"));
			emotionDao.save(new Emotion("Bisous", "😙"));
			emotionDao.save(new Emotion("Coeur", "❤"));
			emotionDao.save(new Emotion("PTDR", "🤣"));
		}
	}

	private void ajouterThemes() {
		if (themeDao.count() == 0) {
			themeDao.save(new Theme("Bachata"));
			themeDao.save(new Theme("Dark"));
		}
	}

	private void ajouterJours() {
		if (jourDao.count() == 0) {
			int anneeEnCours = LocalDate.now().getYear();
			int moisEnCours = LocalDate.now().getMonthValue();
			LocalDate date = LocalDate.of(anneeEnCours, moisEnCours, 1);
			int nbJoursDuMoisEnCours = date.lengthOfMonth();
			for (int i = 1; i <= nbJoursDuMoisEnCours; i++) {
				jourDao.save(new Jour(date));
				date = date.plusDays(1);
			}
		}
	}

	public void ajouterUtilisateurs() {
		if (utilisateurDao.count() == 0) {
			// log.info("Ajout de 500 utilisateurs");
			// Partie déclarative
			List<Theme> themes = themeDao.findAll();
			Map<String, Utilisateur> map = new HashMap<>();
			Calendar calendar = Calendar.getInstance();
			int compteur = 0;

			// Partie traitement
			// On boucle tant que la taille de la map n'est pas égale à 50
			while (map.size() != 50) {
				compteur++;
				// System.out.println(compteur);
				// On déclare un objet de type Utilisateur
				// que l'on instancie dans la foulée
				Utilisateur utilisateur = new Utilisateur();
				// On fait appel au faker pour définir le nom de l'utilisateur
				utilisateur.setNom(faker.name().lastName());
				utilisateur.setPrenom(faker.name().firstName());
				utilisateur.setEmail(fakeValuesService.letterify("?????@hb.com"));
				utilisateur.setMotDePasse(passwordEncoder.encode("12345678"));

				// Grâce à l'objet calendar et le faker on obtient une date comprise
				// entre le 1 janvier 2021 et aujourd'hui (inclus)
				calendar.set(2021, 1, 1);
				Date dateDebut = calendar.getTime();
				calendar = Calendar.getInstance();
				Date dateFin = calendar.getTime();

				Date dateAleatoire = faker.date().between(dateDebut, dateFin);
				calendar.setTime(dateAleatoire);
				// La date choisie par le faker est utilisée pour définir la date et heure
				// d'inscription du nouvel utilisateur
				utilisateur.setDateHeureInscription(
						dateAleatoire.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
//				// On choisit un thème aléatoirement parmi la liste des thèmes
				utilisateur.setTheme(themes.get(random.nextInt(themes.size())));
				// System.out.println(utilisateur);

				utilisateur.setDateDeNaissance(LocalDate.of(1972, 9, 28));
				// On ajoute l'objet utilisateur dans la map
				map.put(utilisateur.getEmail(), utilisateur);
			}

			Utilisateur fx = new Utilisateur();
			fx.setNom("COTE"); 
			fx.setPrenom("Fx");
			fx.setEmail(fakeValuesService.letterify("fx@hb.com"));
			fx.setMotDePasse(passwordEncoder.encode("123"));
			fx.setDateDeNaissance(LocalDate.of(1974, 12, 2));
			fx.setTheme(themes.get(1));
			map.put("fx@hb.com", fx);
			// J'invoque la méthode saveAll sur la dao utilisateurDao
			// pour demander à Spring Data de sauvegarder tous les utilisateurs présents
			// dans la map

			utilisateurDao.saveAll(map.values());

			System.out.println("Nombre d'itérations=" + compteur);
		}
	}
}