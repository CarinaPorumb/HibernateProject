package packages.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Toy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String toyName;

}
