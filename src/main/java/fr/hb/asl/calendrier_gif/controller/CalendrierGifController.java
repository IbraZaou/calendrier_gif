package fr.hb.asl.calendrier_gif.controller;

import java.io.IOException;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import fr.hb.asl.calendrier_gif.business.GifDistant;
import fr.hb.asl.calendrier_gif.business.GifTeleverse;
import fr.hb.asl.calendrier_gif.business.Jour;
import fr.hb.asl.calendrier_gif.business.Theme;
import fr.hb.asl.calendrier_gif.business.Utilisateur;
import fr.hb.asl.calendrier_gif.service.EmotionService;
import fr.hb.asl.calendrier_gif.service.GifService;
import fr.hb.asl.calendrier_gif.service.JourService;
import fr.hb.asl.calendrier_gif.service.ReactionService;
import fr.hb.asl.calendrier_gif.service.ThemeService;
import fr.hb.asl.calendrier_gif.service.UtilisateurService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class CalendrierGifController {
	

	private static final int NB_JOURS_PAR_PAGE = 12;

	protected static final String DOSSIER_IMAGES = "src/main/webapp/images/";

	private final JourService jourService;
	private final EmotionService emotionService;
	private final UtilisateurService utilisateurService;
	private final ThemeService themeService;
	private final GifService gifService;
	private final ReactionService reactionService;

	private final HttpSession httpSession;
	
	@RequestMapping(value = { "/index", "/" }) 
	public ModelAndView accueil() {
		// System.out.println(new Date() + " dans accueil");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		mav.addObject("utilisateurs", utilisateurService.recupererUtilisateursAyantReagiAuMoinsCinqFois());

		// deux écritures équivalentes :
		// mav.getModel().put("nbInscrits", utilisateurService.recupererNbInscrits());
		mav.addObject("nbInscrits", utilisateurService.recupererNbInscrits());
		mav.addObject("themes", themeService.recupererThemes());
		mav.addObject("nbTotalInscrits", utilisateurService.recupererUtilisateurs().size());
		System.out.println( themeService.recupererThemes());
		return mav; 
	}

	@GetMapping("/calendrier")
	public ModelAndView calendrier(@PageableDefault(size = NB_JOURS_PAR_PAGE, sort = "date") Pageable pageable) {
		ModelAndView mav = new ModelAndView("calendrier");
		mav.addObject("pageDeJours", jourService.recupererJours(pageable));
		// Met en session la page choisie
		if (pageable != null) {
			httpSession.setAttribute("numeroDePage", pageable.getPageNumber());
		}
		return mav;
	}

	@GetMapping("calendrier/placerGifDistant")
	public ModelAndView placerGifDistantGet(
			@RequestParam("ID_JOUR") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate idJour,
			@ModelAttribute GifDistant gifDistant) {
		ModelAndView mav = new ModelAndView("placerGifDistant");
		Jour jour = jourService.recupererJour(idJour);
		gifDistant.setJour(jour);
		mav.addObject("gifDistant", gifDistant);
		return mav;
	} 
	
	
	
	@GetMapping("calendrier/televerserGif")
	public ModelAndView televerserGifDistantGet(
			@RequestParam("ID_JOUR") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate idJour) {
		ModelAndView mav = new ModelAndView("televerserGif");
		mav.addObject("idJour", idJour);
		return mav;
	}
  
//	@PostMapping("calendrier/placerGifDistant")
//	public ModelAndView placerGifDistantPost(@Valid @ModelAttribute GifDistant gifDistant, BindingResult result) {
//		if (result.hasErrors()) {
//			ModelAndView mav = placerGifDistantGet(gifDistant.getJour().getDate(), gifDistant);
//			mav.addObject("gifDistant", gifDistant);
//			return mav;
//		} else {
//			Jour jour = gifDistant.getJour();
//			Jour jourChoisi = jourService.recupererJour(jour.getDate());
//			// On vérifie qu'il n'y a pas déja un gif sur ce jour
//			if (jourChoisi.getGif() == null) {
//				gifService.ajouterGifDistant(gifDistant, (Utilisateur) httpSession.getAttribute("utilisateur"));
//			}
//			if (httpSession.getAttribute("numeroDePage") != null) {
//				return new ModelAndView("redirect:/calendrier?page=" + httpSession.getAttribute("numeroDePage"));
//			} else {
//				return new ModelAndView("redirect:/calendrier");
//			} 
//		}
//	}
 
	@PostMapping("calendrier/placerGifDistant")
	public ModelAndView placerGifDistantPost(@Valid @ModelAttribute GifDistant gifDistant, BindingResult result) {
		if (result.hasErrors()) {
			ModelAndView mav = placerGifDistantGet(gifDistant.getJour().getDate(), gifDistant);
			mav.addObject("gifDistant", gifDistant);
			return mav;
		} else {
			Jour jour = gifDistant.getJour();
			Jour jourChoisi = jourService.recupererJour(jour.getDate());
			// On vérifie qu'il n'y a pas déja un gif sur ce jour
			if (jourChoisi.getGif() == null) {
				gifService.ajouterGifDistant(gifDistant, (Utilisateur) httpSession.getAttribute("utilisateur"));
			}
			if (httpSession.getAttribute("numeroDePage") != null) {
				return new ModelAndView("redirect:/calendrier?page=" + httpSession.getAttribute("numeroDePage"));
			} else {
				return new ModelAndView("redirect:/calendrier");
			} 
		}
	}
 

	@PostMapping("calendrier/televerserGif")
	public ModelAndView televerserGifDistantGet(
			@RequestParam("ID_JOUR") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate idJour,
			@RequestParam("FICHIER") MultipartFile multipartFile) {
		GifTeleverse gifTeleverse = gifService.ajouterGifTeleverse(idJour, null,
				(Utilisateur) httpSession.getAttribute("utilisateur"));
		try {
			enregisterFichier(gifTeleverse.getId() + ".gif", multipartFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (httpSession.getAttribute("numeroDePage") != null) {
			return new ModelAndView("redirect:/calendrier?page=" + httpSession.getAttribute("numeroDePage"));
		} else {
			return new ModelAndView("redirect:/calendrier");
		}
	}
 
	@GetMapping("calendrier/reagir")
	public ModelAndView reagirGet(@RequestParam("ID_GIF") Long idGif) {
		ModelAndView mav = new ModelAndView("reagir");
		mav.addObject("gif", gifService.recupererGif(idGif));
		mav.addObject("emotions", emotionService.recupererEmotions());
		return mav;
	}

	@PostMapping("calendrier/reagir")
	public ModelAndView reagirPost(@RequestParam("ID_GIF") Long idGif, @RequestParam("ID_EMOTION") Long idEmotion) {
		reactionService.ajouterReaction(idGif, idEmotion, (Utilisateur) httpSession.getAttribute("utilisateur"));
		if (httpSession.getAttribute("numeroDePage") != null) {
			return new ModelAndView("redirect:/calendrier?page=" + httpSession.getAttribute("numeroDePage"));
		} else {
			return new ModelAndView("redirect:/calendrier");
		}
	}

	@GetMapping("calendrier/deconnexion")
	public ModelAndView deconnexion() {
		httpSession.invalidate();
		ModelAndView mav = new ModelAndView("redirect:/index");
		mav.addObject("notification", "Au revoir");
		return mav;
	}

	@GetMapping("/inscription")
	public ModelAndView inscriptionGet(@ModelAttribute Utilisateur utilisateur) {
		ModelAndView mav = new ModelAndView("inscription");
		mav.addObject("themes", themeService.recupererThemes());
		return mav;
	}

	/**
	 * Cette méthode traite une requête HTTP dont la méthode est POST
	 * 
	 * @param utilisateur
	 * @param result
	 * @return
	 */
	@PostMapping({ "/inscription", "/signup" })
	public ModelAndView inscriptionPost(@Valid @ModelAttribute Utilisateur utilisateur, BindingResult result) {
		// Est-ce que Spring a trouvé des erreurs en validant l'objet utilisateur ?
		if (result.hasErrors()) {
			return inscriptionGet(utilisateur);
		} else {
			System.out.println("Ajout utilisateur");
			try {
				utilisateurService.ajouterUtilisateur(utilisateur.getNom(),utilisateur.getPrenom(),utilisateur.getEmail(),utilisateur.getMotDePasse(),utilisateur.getTheme());
				ModelAndView mav = new ModelAndView("redirect:index");
				mav.addObject("notification", "Utilisateur ajouté");
				// Pour que l'utilisateur soit connecté automatiquement et redirigé vers le
				// calendrier
				 httpSession.setAttribute("utilisateur", utilisateur);
				// ModelAndView mav = new ModelAndView("redirect:calendrier");
				return mav;
			} catch (Exception e) {
				System.out.println(e);
				ModelAndView mav = new ModelAndView("redirect:index");
				mav.addObject("notification", "Adresse email déjà en base");
				return mav;
			}

		}
	}

	protected static void enregisterFichier(String nom, MultipartFile multipartFile) throws IOException {
		Path chemin = Paths.get(DOSSIER_IMAGES);

		if (!Files.exists(chemin)) {
			Files.createDirectories(chemin);
		}

		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path cheminFichier = chemin.resolve(nom);
			Files.copy(inputStream, cheminFichier, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			throw new IOException("Erreur d'écriture : " + nom, ioe);
		}
	}
	
	
//	   @GetMapping("/calendrierExport")
//	    public ModelAndView getCalendrierExportPDF(@RequestParam(name = "jours") int nbPage) {
//	        Page<Jour> jours = jourService.recupererJours(PageRequest.of(nbPage, NB_JOURS_PAR_PAGE));
//	        ModelAndView mav = new ModelAndView(new CalendrierExportPDF(gifService));
//	        mav.addObject("jours", jours.getContent());
//	        return mav;
//	    }

	
}
