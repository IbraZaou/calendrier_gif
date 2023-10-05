package fr.hb.asl.calendrier_gif.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.hb.asl.calendrier_gif.business.Theme;

@Repository
public interface ThemeDao extends JpaRepository<Theme, Long> {

	Theme findByNom(String nom);

	@Query("FROM Theme WHERE nom = :nom")
	Theme findByNomHQL(@Param("nom") String nom);

	/**
	 * 
	 * @param nom
	 * @return
	 */
	@Query(value = "SELECT * FROM theme WHERE nom LIKE '%nom%'", nativeQuery = true)
	Theme findByNomSQL(@Param("nom") String nom);

	@Query(value = "SELECT DISTINCT theme FROM Utilisateur")
	List<Theme> findByTheme();
}
