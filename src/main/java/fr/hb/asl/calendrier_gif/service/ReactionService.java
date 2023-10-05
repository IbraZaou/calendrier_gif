package fr.hb.asl.calendrier_gif.service;

import java.util.List;

import fr.hb.asl.calendrier_gif.business.Gif;
import fr.hb.asl.calendrier_gif.business.Reaction;
import fr.hb.asl.calendrier_gif.business.Utilisateur;

public interface ReactionService {

	Reaction ajouterReaction(Long idGif, Long idEmotion, Utilisateur utilisateur);

	List<Reaction> recupererReactions(Gif gif);

	boolean supprimerReaction(Long id);
}
