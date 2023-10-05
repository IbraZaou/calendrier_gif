package fr.hb.asl.calendrier_gif.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.hb.asl.calendrier_gif.business.Emotion;
import fr.hb.asl.calendrier_gif.business.Gif;
import fr.hb.asl.calendrier_gif.business.Reaction;
import fr.hb.asl.calendrier_gif.business.Utilisateur;
import fr.hb.asl.calendrier_gif.dao.EmotionDao;
import fr.hb.asl.calendrier_gif.dao.ReactionDao;
import fr.hb.asl.calendrier_gif.service.GifService;
import fr.hb.asl.calendrier_gif.service.ReactionService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReactionServiceImpl implements ReactionService {
 
	private ReactionDao reactionDao;
	private EmotionDao emotionDao;
	private GifService gifService;

	@Override
	public Reaction ajouterReaction(Long idGif, Long idEmotion, Utilisateur utilisateur) {
		Emotion emotion = emotionDao.findById(idEmotion).orElse(null);
		Gif gif = gifService.recupererGif(idGif);
		Reaction reaction = new Reaction();
		reaction.setEmotion(emotion);
		reaction.setGif(gif);
		reaction.setUtilisateur(utilisateur);
		reactionDao.save(reaction);
		return reaction;
	}

	@Override
	public List<Reaction> recupererReactions(Gif gif) {
		return reactionDao.findByGif(gif);
	}

	@Override
	public boolean supprimerReaction(Long id) {
		if (reactionDao.existsById(id)) {
			reactionDao.deleteById(id);
			return true;
		}
		return false;
	}

}
