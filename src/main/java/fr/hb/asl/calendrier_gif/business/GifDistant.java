package fr.hb.asl.calendrier_gif.business;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GifDistant extends Gif {

	@NotBlank(message = "Merci de remplir le champs")
	@URL(message = "Merci de saisir une URL valide, elle doit se terminer par .gif, .Gif ou .GIF", regexp = "^https?://(?:[a-z0-9\\-]+\\.)+[a-z]{2,6}(?:/[^/#?]+)+\\.(?:Gif|gif|GIF)$")
	private String url;  

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public GifDistant(Utilisateur utilisateur, Jour jour, String legende, String url, LocalDateTime dateHeureAjout) {
		super();
		this.utilisateur = utilisateur;
		this.jour = jour;
		this.legende = legende;
		this.url = url;
		this.dateHeureAjout = dateHeureAjout;
	}

}
