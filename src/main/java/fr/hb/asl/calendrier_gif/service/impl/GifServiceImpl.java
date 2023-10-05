package fr.hb.asl.calendrier_gif.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import fr.hb.asl.calendrier_gif.business.Gif;
import fr.hb.asl.calendrier_gif.business.GifDistant;
import fr.hb.asl.calendrier_gif.business.GifTeleverse;
import fr.hb.asl.calendrier_gif.business.Jour;
import fr.hb.asl.calendrier_gif.business.Utilisateur;
import fr.hb.asl.calendrier_gif.dao.GifDao;
import fr.hb.asl.calendrier_gif.dao.GifDistantDao;
import fr.hb.asl.calendrier_gif.dao.GifTeleverseDao;
import fr.hb.asl.calendrier_gif.dao.UtilisateurDao;
import fr.hb.asl.calendrier_gif.service.GifService;
import fr.hb.asl.calendrier_gif.service.JourService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GifServiceImpl implements GifService {
	// @Autowired
	private JourService jourService;

	// @Autowired
	private GifDao gifDao;

	// @Autowired
	private GifDistantDao gifDistantDao;

	// @Autowired
	private GifTeleverseDao gifTeleverseDao;

	// @Autowired
	private UtilisateurDao utilisateurDao;

	@Override
	public GifDistant ajouterGifDistant(LocalDate idJour, String url, Utilisateur utilisateur) {
		GifDistant gifDistant = new GifDistant();
		Jour jour = jourService.recupererJour(idJour);
		gifDistant.setUrl(url);
		gifDistant.setUtilisateur(utilisateur);
		gifDistant.setJour(jour);
		return ajouterGifDistant(gifDistant, utilisateur);
	}

	@Override
	public GifDistant ajouterGifDistant(GifDistant gifDistant, Utilisateur utilisateur) {
		// on envoit en base le gifdistant construit et on le recuperer dans Gifdistant
		// afin d'etre sure de travailler avec les même données
		gifDistant.setUtilisateur(utilisateur);
		gifDistant = gifDistantDao.save(gifDistant);
		// si on a prit le parti de ne PAS mettre @OneToOne(mappedBy = "jour") dans Gif
		// Jour jour = jourService.recupererJour(gifDistant.getJour().getDate());
		// jour.setGif(gifDistant);
		// jourService.enregistrerJour(jour);

		// met à jour le solde de l'utilisateur
		utilisateur.setNbPoints(utilisateur.getNbPoints() - gifDistant.getJour().getNbPoints());
		utilisateurDao.save(utilisateur);

		return gifDistant;
	}

	@Override
	public GifTeleverse ajouterGifTeleverse(LocalDate localDate, String nomFichierOriginal, Utilisateur utilisateur) {
		GifTeleverse gifTeleverse = new GifTeleverse();
		gifTeleverse.setJour(jourService.recupererJour(localDate));
		gifTeleverse = gifTeleverseDao.save(gifTeleverse);
		gifTeleverse.setUtilisateur(utilisateur);
		utilisateur.setNbPoints(utilisateur.getNbPoints() - gifTeleverse.getJour().getNbPoints());
		utilisateurDao.save(utilisateur);
		return gifTeleverse;
	}

	@Override
	public Gif enregistrerGif(Gif gif) {
		return gifDao.save(gif);
	}

	@Override
	public Gif recupererGif(Long idGif) {
		return gifDao.findById(idGif).orElse(null);
	}

	@Override
	public Gif mettreAJourLegende(Gif gif, String nouvelleLegende) {
		gif.setLegende(nouvelleLegende);
		gifDao.save(gif);
		return gif;
	}

	@Override
	public Gif recupererGifParJour(Jour jour) {
		return gifDao.findLast1ByJour(jour);
	}

	@Override
	public List<Gif> recupererGifsParLegende(String legende) {
		return gifDao.findByLegendeContaining(legende);
	}

	@Override
	public List<Gif> recupererGifsParNbReactions() {
		return gifDao.findTopByReactions();
	}
	
    @Override
    public GifDistant recupererGifDistant(Long id) {
        return gifDistantDao.findById(id).orElse(null);
    }
    
    @Override
    public GifTeleverse recupererGifTeleverse(Long id) {
        return gifTeleverseDao.findById(id).orElse(null);
    }

}
