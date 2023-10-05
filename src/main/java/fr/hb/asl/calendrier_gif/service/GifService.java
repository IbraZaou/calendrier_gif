package fr.hb.asl.calendrier_gif.service;

import java.time.LocalDate;
import java.util.List;

import fr.hb.asl.calendrier_gif.business.Gif;
import fr.hb.asl.calendrier_gif.business.GifDistant;
import fr.hb.asl.calendrier_gif.business.GifTeleverse;
import fr.hb.asl.calendrier_gif.business.Jour;
import fr.hb.asl.calendrier_gif.business.Utilisateur;

public interface GifService {

	GifDistant ajouterGifDistant(LocalDate idJour, String url, Utilisateur utilisateur);

	GifDistant ajouterGifDistant(GifDistant gifDistant, Utilisateur utilisateur);

	GifTeleverse ajouterGifTeleverse(LocalDate localDate, String nomFichierOriginal, Utilisateur utilisateur);

	Gif enregistrerGif(Gif gif);

	Gif recupererGif(Long idGif);

	Gif mettreAJourLegende(Gif gif, String nouvelleLegende);

	Gif recupererGifParJour(Jour jour);

	List<Gif> recupererGifsParLegende(String legende);

	List<Gif> recupererGifsParNbReactions();

	GifDistant recupererGifDistant(Long id);

    GifTeleverse recupererGifTeleverse(Long id);

}
