package fr.hb.asl.calendrier_gif.business;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@ToString
public class Reaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Gif gif;

	@ManyToOne
	private Utilisateur utilisateur;

	@ManyToOne
	private Emotion emotion;

	private LocalDateTime dateHeure;

	public Reaction() {
		dateHeure = LocalDateTime.now();
	}

	public Reaction(Utilisateur utilisateur, Emotion emotion, Gif gif) {
		super();
		this.gif = gif;
		this.utilisateur = utilisateur;
		this.emotion = emotion;
	}

}
