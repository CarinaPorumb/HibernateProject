package project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String ownerName;

    public Owner(String ownerName) {
        this.ownerName = ownerName;
    }

    @Builder.Default
    @ToString.Exclude
    @ManyToMany(mappedBy = "owners", fetch = FetchType.EAGER)
    private Set<Animal> animals = new HashSet<>();

    public void addAnimalToOwner(Animal animal) {
        animals.add(animal);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Owner owner = (Owner) o;

        return Objects.equals(id, owner.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}