package fr.hb.asl.calendrier_gif.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fr.hb.asl.calendrier_gif.business.Jour;
import fr.hb.asl.calendrier_gif.dao.JourDao;
import fr.hb.asl.calendrier_gif.dto.JourDto;
import fr.hb.asl.calendrier_gif.mapper.JourMapper;
import fr.hb.asl.calendrier_gif.service.JourService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JourServiceImpl implements JourService {

	private JourDao jourDao;

	@Override
	public Jour ajouterJour(LocalDate date, int nbPoints) {
		Jour jour = new Jour();
		jour.setDate(date);
		jour.setNbPoints(nbPoints);
		return jourDao.save(jour);
	}

	@Override
	public Jour enregistrerJour(Jour jour) {
		return jourDao.save(jour);
	}

	@Override
	public Jour enregistrerJour(JourDto jourDto) {
		Jour jour = JourMapper.INSTANCE.toEntity(jourDto);
		return enregistrerJour(jour);
	}

	@Override
	public Page<Jour> recupererJours(Pageable pageable) {
		return jourDao.findAll(pageable);
	}

	@Override
	public List<Jour> recupererJours() {
		return jourDao.findAll();
	}

	@Override
	public Jour recupererJour(LocalDate idJour) {
		return jourDao.findById(idJour).orElse(null);
	}

	@Override
	public List<Jour> recupererJoursDuMoisEnCours() {
		return jourDao.findDaysOfCurrentMonth();
	}

	@Override
	public Jour mettreAJourJour(LocalDate date, int nouveauNbPoints) {
		Jour jour = recupererJour(date);
		jour.setNbPoints(nouveauNbPoints);
		return jourDao.save(jour);
	}

	@Override
	public boolean supprimerJour(LocalDate date) {
		Jour jour = recupererJour(date);
		if (jour == null) {
			return false;
		}
		jourDao.delete(jour);
		return true;
	}

	@Override
	public long compterJours() {
		return jourDao.count();
	}

}
