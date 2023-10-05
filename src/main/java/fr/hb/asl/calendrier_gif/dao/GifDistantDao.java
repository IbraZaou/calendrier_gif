package fr.hb.asl.calendrier_gif.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.hb.asl.calendrier_gif.business.GifDistant;

@Repository
public interface GifDistantDao extends JpaRepository<GifDistant, Long> {

	long deleteByUrlLike(String source);
}
