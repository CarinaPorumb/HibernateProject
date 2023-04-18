package project.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class HealthCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String type;

    private Integer price;

    @ToString.Exclude
    @OneToOne(mappedBy = "healthCertificate", cascade = CascadeType.ALL)
    private Animal animal;
}