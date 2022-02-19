package fr.orsys.gamesreviews.business.user;

import fr.orsys.gamesreviews.business.Review;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString(callSuper = true)

@Entity
public class Player extends User {

    private LocalDate birthDate;

    @ToString.Exclude
    @OneToMany(mappedBy = "player")
    private List<Review> reviews;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Player player = (Player) o;
        return Objects.equals(birthDate, player.birthDate) && Objects.equals(reviews, player.reviews);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), birthDate, reviews);
    }

}
