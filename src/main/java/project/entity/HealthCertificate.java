package project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
@Builder
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
    @OneToOne(mappedBy = "healthCertificate")
    private Animal animal;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HealthCertificate that = (HealthCertificate) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}