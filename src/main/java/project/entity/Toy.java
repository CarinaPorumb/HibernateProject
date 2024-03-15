package project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Toy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String toyName;

    public Toy(String toyName) {
        this.toyName = toyName;
    }

    @ManyToOne
    @JoinTable(
            name = "animal_toy",
            joinColumns = @JoinColumn(name = "toy_id"),
            inverseJoinColumns = @JoinColumn(name = "animal_id"))
    private Animal animal;

    @Override
    public String toString() {
        return "Toy{" +
                "id=" + id +
                ", toyName='" + toyName + '\'' +
                ", animal=" + animal.getName() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Toy toy = (Toy) o;

        return Objects.equals(id, toy.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}