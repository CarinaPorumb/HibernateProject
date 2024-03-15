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
@Entity
@ToString
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String name;

    private Integer age;

    private Integer weight;

    @OneToOne(cascade = CascadeType.ALL)
    private HealthCertificate healthCertificate;

    @Builder.Default
    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Toy> toys = new HashSet<>();

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "animal_owner",
            joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "owner_id"))
    private Set<Owner> owners = new HashSet<>();

    public void addToyToAnimal(Toy toy) {
        toys.add(toy);
        toy.setAnimal(this);
    }

    public void addOwnerToAnimal(Owner owner) {
        owners.add(owner);
        owner.addAnimalToOwner(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(id, animal.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}