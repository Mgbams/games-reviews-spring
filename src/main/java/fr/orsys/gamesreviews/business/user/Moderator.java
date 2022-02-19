package fr.orsys.gamesreviews.business.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import java.util.Objects;

@Getter
@Setter
@ToString(callSuper = true)

@Entity
public class Moderator extends User {

    private String phoneNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Moderator moderator = (Moderator) o;
        return Objects.equals(phoneNumber, moderator.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), phoneNumber);
    }

}
