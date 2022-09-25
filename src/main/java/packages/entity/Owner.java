package packages.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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

    private String name;

    @ManyToMany(mappedBy = "owners", fetch = FetchType.EAGER)
    private Set<Animal> animals = new HashSet<>();

    public void addAnimalToOwner(Animal animal) {
        animals.add(animal);
    }
}
