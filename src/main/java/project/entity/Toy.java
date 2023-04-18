package project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}