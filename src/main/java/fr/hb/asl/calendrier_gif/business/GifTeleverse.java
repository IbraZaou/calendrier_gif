package fr.hb.asl.calendrier_gif.business;



import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class GifTeleverse extends Gif {

	private String nomFichierOriginal;
}
