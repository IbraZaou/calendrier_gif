package fr.hb.asl.calendrier_gif.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.hb.asl.calendrier_gif.business.Theme;
import fr.hb.asl.calendrier_gif.dao.ThemeDao;
import fr.hb.asl.calendrier_gif.dto.ThemeDto;
import fr.hb.asl.calendrier_gif.mapper.ThemeMapper;
import fr.hb.asl.calendrier_gif.service.ThemeService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ThemeServiceImpl implements ThemeService {

	private ThemeDao themeDao;

	@Override
	public Theme ajouterTheme(String nom) {
		return themeDao.save(new Theme(nom));
	}

	@Override
	public Theme enregistrerTheme(ThemeDto themeDto) {
		Theme theme = ThemeMapper.INSTANCE.toEntity(themeDto);
		return enregistrerTheme(theme);
	}

	@Override
	public Theme enregistrerTheme(Theme theme) {
		return themeDao.save(theme);
	}

	@Override
	public List<Theme> recupererThemes() {
		return themeDao.findAll();
	}

	@Override
	public Theme recupererTheme(Long id) {
		return themeDao.findById(id).orElse(null);
	}

	@Override
	public Theme recupererTheme(String nom) {
		return themeDao.findByNom(nom);
	}

}
