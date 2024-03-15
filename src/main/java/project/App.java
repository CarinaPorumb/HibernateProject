package project;

import net.datafaker.Faker;
import project.dao.AnimalDAOImpl;
import project.dao.EntityDAO;
import project.dao.OwnerDAOImpl;
import project.entity.Animal;
import project.entity.HealthCertificate;
import project.entity.Owner;
import project.entity.Toy;

import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {
        EntityDAO<Animal, Integer> animal = new AnimalDAOImpl();
        animal.createAll(createAnimal());

    }

    public static List<Animal> createAnimal() {

        List<Animal> animals = new ArrayList<>();
        Faker faker = new Faker();
        EntityDAO<Owner, Integer> owner = new OwnerDAOImpl();

        Animal rocco = Animal.builder()
                .name("Rocco")
                .age(2)
                .weight(4)
                .healthCertificate(HealthCertificate.builder()
                        .type("Travel Health Certificate")
                        .price(faker.number().randomDigitNotZero())
                        .build())
                .build();

        Animal lady = Animal.builder()
                .name("Lady")
                .age(2)
                .weight(4)
                .healthCertificate(HealthCertificate.builder()
                        .type("Domestic Health Certificate")
                        .price(faker.number().randomDigitNotZero())
                        .build())
                .build();

        animals.add(rocco);
        animals.add(lady);


        animals.forEach(elem -> {
            Toy toy1 = Toy.builder()
                    .toyName(faker.superhero().name())
                    .animal(elem)
                    .build();

            Toy toy2 = Toy.builder()
                    .toyName(faker.superhero().name())
                    .animal(elem)
                    .build();

            elem.addToyToAnimal(toy1);
            elem.addToyToAnimal(toy2);
        });


        Owner owner1 = owner.createAndReturnIt(Owner.builder().ownerName(faker.funnyName().name()).build());
        Owner owner2 = owner.createAndReturnIt(Owner.builder().ownerName(faker.funnyName().name()).build());
        Owner owner3 = owner.createAndReturnIt(Owner.builder().ownerName(faker.funnyName().name()).build());
        Owner owner4 = owner.createAndReturnIt(Owner.builder().ownerName(faker.funnyName().name()).build());

        rocco.addOwnerToAnimal(owner1);
        rocco.addOwnerToAnimal(owner2);
        owner1.getAnimals().add(rocco);
        owner2.getAnimals().add(rocco);

        lady.addOwnerToAnimal(owner3);
        lady.addOwnerToAnimal(owner4);
        owner3.getAnimals().add(lady);
        owner4.getAnimals().add(lady);

        return animals;
    }

}