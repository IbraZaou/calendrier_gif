package fr.hb.asl.calendrier_gif.business;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter     
@ToString
public class Emotion {
  
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;

	@NonNull 
	@NotBlank(message = "merci de donner un nom à l\' emotion")
	private String nom;

	@NonNull 
	private String code;

	@ToString.Exclude
	@OneToMany(mappedBy = "emotion", cascade = CascadeType.REMOVE)
	private List<Reaction> reactions;

	public Emotion(String nom, String code) {
		this.nom = nom;
		this.code = code;
	}

}
