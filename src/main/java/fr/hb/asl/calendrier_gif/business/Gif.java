package fr.hb.asl.calendrier_gif.business;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@FieldDefaults(level = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@ToString
public abstract class Gif {
  
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	LocalDateTime dateHeureAjout;

	@Column(length = 255)
	String legende;

	@OneToMany(mappedBy = "gif", fetch = FetchType.EAGER)
	List<Reaction> reactions;

	@OneToOne
	Jour jour;

	@ManyToOne
	Utilisateur utilisateur;

	public Gif() {
		dateHeureAjout = LocalDateTime.now();
	}

}
