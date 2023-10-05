package fr.hb.asl.calendrier_gif.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.hb.asl.calendrier_gif.business.Emotion;
import fr.hb.asl.calendrier_gif.dao.EmotionDao;
import fr.hb.asl.calendrier_gif.service.EmotionService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmotionServiceImpl implements EmotionService {

	private EmotionDao emotionDao;

	@Override
	public Emotion ajouterEmotion(String nom, String code) {
		return emotionDao.save(new Emotion(nom, code));
	}

	@Override
	public Emotion ajouterEmotion(Emotion emotion) {
		return emotionDao.save(emotion);
	}

	@Override
	public List<Emotion> recupererEmotions() {
		return emotionDao.findAll();
	}

	@Override
	public Emotion recupererEmotion(Long id) {
		return emotionDao.findById(id).orElse(null);
	}

	@Override
	public Emotion recupererEmotion(String nom) {
		return emotionDao.findByNom(nom);
	}

	@Override
	public boolean supprimerEmotion(Long id) {
		Emotion emotion = recupererEmotion(id);
		if (emotion == null) {
			return false;
		} else {
			emotionDao.delete(emotion);
			return true;
		}
	}

}
