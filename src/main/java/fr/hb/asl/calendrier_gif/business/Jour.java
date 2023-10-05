package fr.hb.asl.calendrier_gif.business;

import java.time.LocalDate;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Entity
@Getter
@Setter
public class Jour {

	private static Random random = new Random();

	@Id
	private LocalDate date;

	// @Min(value=20, message="Le nombre de points doit être supérieur ou égale à
	// 20")
	// @Max(value=50, message="Le nombre de points doit être inférieur ou égale à
	// 50")
	@Range(min = 20, max = 50, message = "Le nombre de points doit être compris entre {min} et {max}")
	private int nbPoints;

	@ToString.Exclude
	@OneToOne(mappedBy = "jour")
	private Gif gif;

	public String toString() {
		return date.getDayOfMonth() + "/" + date.getMonthValue();
	}

	public Jour() {
		nbPoints = 20 + random.nextInt(31);
	}

	public Jour(LocalDate date) {
		this();
		this.date = date;
	}
	
	public Jour(LocalDate date, int nbPoints) {
		this(date);
		this.nbPoints = nbPoints;
	}

}